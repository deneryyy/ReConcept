/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package dev.lambdaurora.lambdynlights.mixin;

import dev.lambdaurora.lambdynlights.DynamicLightSource;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import moe.denery.recodynlights.ReCoDynLights;
import moe.denery.recodynlights.api.EntityLuminanceRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements DynamicLightSource {
    @Shadow public abstract Level level();

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Shadow public abstract double getEyeY();

    @Shadow public abstract ChunkPos chunkPosition();

    @Shadow public abstract BlockPos getOnPos();

    @Shadow public abstract boolean isRemoved();

    @Shadow private Level level;

    @Shadow public abstract EntityType<?> getType();

    @Unique
    private int luminance = 0;

    @Unique
    private int lastLuminance = 0;
    @Unique
    private double prevX;
    @Unique
    private double prevY;
    @Unique
    private double prevZ;
    @Unique
    private LongOpenHashSet trackedLitChunkPos = new LongOpenHashSet();

    @Inject(method = "tick", at = @At("TAIL"))
    public void onTick(CallbackInfo ci) {
        if (!this.level.isClientSide())
            return;

        if (this.isRemoved()) {
            this.setDynamicLightEnabled(false);
        } else {
            this.dynamicLightTick();
            ReCoDynLights.updateTracking(this);
        }
    }

    @Override
    public void dynamicLightTick() {
        this.luminance = EntityLuminanceRegistry.getLuminance((EntityType<Entity>) this.getType(), (Entity) (Object) this);
    }

    @Override
    public boolean updateDynamicLight(@NotNull LevelRenderer renderer) {
        double deltaX = this.getX() - this.prevX;
        double deltaY = this.getY() - this.prevY;
        double deltaZ = this.getZ() - this.prevZ;

        int luminance = this.getLuminance();

        if (Math.abs(deltaX) > 0.1D || Math.abs(deltaY) > 0.1D || Math.abs(deltaZ) > 0.1D || luminance != this.lastLuminance) {
            this.prevX = this.getX();
            this.prevY = this.getY();
            this.prevZ = this.getZ();
            this.lastLuminance = luminance;

            var newPos = new LongOpenHashSet();

            if (luminance > 0) {
                var entityChunkPos = this.chunkPosition();
                var chunkPos = new BlockPos.MutableBlockPos(entityChunkPos.x, SectionPos.posToSectionCoord(this.getEyeY()), entityChunkPos.z);

                ReCoDynLights.scheduleChunkRebuild(renderer, chunkPos);
                ReCoDynLights.updateTrackedChunks(chunkPos, this.trackedLitChunkPos, newPos);

                var directionX = (this.getOnPos().getX() & 15) >= 8 ? Direction.EAST : Direction.WEST;
                var directionY = (Mth.floor(this.getEyeY()) & 15) >= 8 ? Direction.UP : Direction.DOWN;
                var directionZ = (this.getOnPos().getZ() & 15) >= 8 ? Direction.SOUTH : Direction.NORTH;

                for (int i = 0; i < 7; i++) {
                    if (i % 4 == 0) {
                        chunkPos.move(directionX); // X
                    } else if (i % 4 == 1) {
                        chunkPos.move(directionZ); // XZ
                    } else if (i % 4 == 2) {
                        chunkPos.move(directionX.getOpposite()); // Z
                    } else {
                        chunkPos.move(directionZ.getOpposite()); // origin
                        chunkPos.move(directionY); // Y
                    }
                    ReCoDynLights.scheduleChunkRebuild(renderer, chunkPos);
                    ReCoDynLights.updateTrackedChunks(chunkPos, this.trackedLitChunkPos, newPos);
                }
            }

            // Schedules the rebuild of removed chunks.
            this.scheduleTrackedChunksRebuild(renderer);
            // Update tracked lit chunks.
            this.trackedLitChunkPos = newPos;
            return true;
        }
        return false;
    }

    @Override
    public void scheduleTrackedChunksRebuild(@NotNull LevelRenderer renderer) {
        if (Minecraft.getInstance().level == this.level)
            for (long pos : this.trackedLitChunkPos) {
                ReCoDynLights.scheduleChunkRebuild(renderer, pos);
            }
    }

    @Override
    public void resetDynamicLight() {
        this.lastLuminance = 0;
    }

    @Override
    public @NotNull Level getDynamicLightLevel() {
        return this.level();
    }

    @Override
    public double getDynamicLightX() {
        return this.getX();
    }

    @Override
    public double getDynamicLightY() {
        return this.getEyeY();
    }

    @Override
    public double getDynamicLightZ() {
        return this.getZ();
    }

    @Override
    public int getLuminance() {
        return this.luminance;
    }

    @Override
    public void setLuminance(int luminance) {
        this.luminance = luminance;
    }
}
