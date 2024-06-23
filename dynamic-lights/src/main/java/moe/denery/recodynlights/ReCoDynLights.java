/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package moe.denery.recodynlights;

import dev.lambdaurora.lambdynlights.DynamicLightSource;
import dev.lambdaurora.lambdynlights.LambDynLightsAlgorithms;
import dev.lambdaurora.lambdynlights.mixin.LevelRendererInvoker;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReCoDynLights implements ClientModInitializer {
    public static ReCoDynLights INSTANCE;

    private final Set<DynamicLightSource> dynamicLightSources = new HashSet<>();

    private final ReentrantReadWriteLock lightSourcesLock = new ReentrantReadWriteLock();
    private long lastUpdate = System.currentTimeMillis();
    private int lastUpdateCount = 0; // May be used in F3

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        WorldRenderEvents.START.register(context -> {
            this.updateAll(context.worldRenderer());
        });
    }

    /**
     * Updates all light sources.
     *
     * @param renderer the renderer
     */
    public void updateAll(@NotNull LevelRenderer renderer) {
        long now = System.currentTimeMillis();
        if (now >= this.lastUpdate + 50) {
            this.lastUpdate = now;
            this.lastUpdateCount = 0;

            this.lightSourcesLock.readLock().lock();
            for (var lightSource : this.dynamicLightSources) {
                if (lightSource.updateDynamicLight(renderer)) this.lastUpdateCount++;
            }
            this.lightSourcesLock.readLock().unlock();
        }
    }

    public void addDynamicLightSource(DynamicLightSource dynamicLightSource) {
        if (!dynamicLightSource.getDynamicLightLevel().isClientSide())
            return;
        if (this.containsDynamicLightSource(dynamicLightSource))
            return;
        this.lightSourcesLock.writeLock().lock();
        this.dynamicLightSources.add(dynamicLightSource);
        this.lightSourcesLock.writeLock().unlock();
    }

    /**
     * Removes the light source from the tracked light sources.
     *
     * @param lightSource the light source to remove
     */
    public void removeDynamicLightSource(@NotNull DynamicLightSource lightSource) {
        this.lightSourcesLock.writeLock().lock();

        var dynamicLightSources = this.dynamicLightSources.iterator();
        DynamicLightSource it;
        while (dynamicLightSources.hasNext()) {
            it = dynamicLightSources.next();
            if (it.equals(lightSource)) {
                dynamicLightSources.remove();
                if (Minecraft.getInstance().levelRenderer != null)
                    lightSource.scheduleTrackedChunksRebuild(Minecraft.getInstance().levelRenderer);
                break;
            }
        }

        this.lightSourcesLock.writeLock().unlock();
    }

    /**
     * Clears light sources.
     */
    public void clearLightSources() {
        this.lightSourcesLock.writeLock().lock();

        var dynamicLightSources = this.dynamicLightSources.iterator();
        DynamicLightSource it;
        while (dynamicLightSources.hasNext()) {
            it = dynamicLightSources.next();
            dynamicLightSources.remove();
            if (Minecraft.getInstance().levelRenderer != null) {
                if (it.getLuminance() > 0)
                    it.resetDynamicLight();
                it.scheduleTrackedChunksRebuild(Minecraft.getInstance().levelRenderer);
            }
        }

        this.lightSourcesLock.writeLock().unlock();
    }

    public boolean containsDynamicLightSource(DynamicLightSource dynamicLightSource) {
        this.lightSourcesLock.readLock().lock();
        boolean result = this.dynamicLightSources.contains(dynamicLightSource);
        this.lightSourcesLock.readLock().unlock();
        return result;
    }

    public int getLightMapFor(BlockPos blockPos, int lightmap) {
        return LambDynLightsAlgorithms.getLightmapWithDynamicLight(this.getDynamicLightLevel(blockPos), lightmap);
    }

    /**
     * Updates the dynamic lights tracking.
     *
     * @param lightSource the light source
     */
    public static void updateTracking(@NotNull DynamicLightSource lightSource) {
        boolean enabled = lightSource.isDynamicLightEnabled();
        int luminance = lightSource.getLuminance();

        if (!enabled && luminance > 0) {
            lightSource.setDynamicLightEnabled(true);
        } else if (enabled && luminance < 1) {
            lightSource.setDynamicLightEnabled(false);
        }
    }

    /**
     * Returns the dynamic light level at the specified position.
     *
     * @param pos the position
     * @return the dynamic light level at the specified position
     */
    public double getDynamicLightLevel(@NotNull BlockPos pos) {
        double result = 0;
        this.lightSourcesLock.readLock().lock();
        for (var lightSource : this.dynamicLightSources) {
            result = LambDynLightsAlgorithms.maxDynamicLightLevel(pos, lightSource, result);
        }
        this.lightSourcesLock.readLock().unlock();

        return Mth.clamp(result, 0, 15);
    }

    /**
     * Schedules a chunk rebuild at the specified chunk position.
     *
     * @param renderer the renderer
     * @param chunkPos the chunk position
     */
    public static void scheduleChunkRebuild(@NotNull LevelRenderer renderer, @NotNull BlockPos chunkPos) {
        scheduleChunkRebuild(renderer, chunkPos.getX(), chunkPos.getY(), chunkPos.getZ());
    }

    /**
     * Schedules a chunk rebuild at the specified chunk position.
     *
     * @param renderer the renderer
     * @param chunkPos the packed chunk position
     */
    public static void scheduleChunkRebuild(@NotNull LevelRenderer renderer, long chunkPos) {
        scheduleChunkRebuild(renderer, BlockPos.getX(chunkPos), BlockPos.getY(chunkPos), BlockPos.getZ(chunkPos));
    }

    public static void scheduleChunkRebuild(@NotNull LevelRenderer renderer, int x, int y, int z) {
        if (Minecraft.getInstance().level != null)
            ((LevelRendererInvoker) renderer).scheduleChunkRebuild(x, y, z, false);
    }

    /**
     * Updates the tracked chunk sets.
     *
     * @param chunkPos the packed chunk position
     * @param old the set of old chunk coordinates to remove this chunk from it
     * @param newPos the set of new chunk coordinates to add this chunk to it
     */
    public static void updateTrackedChunks(@NotNull BlockPos chunkPos, @Nullable LongOpenHashSet old, @Nullable LongOpenHashSet newPos) {
        if (old != null || newPos != null) {
            long pos = chunkPos.asLong();
            if (old != null)
                old.remove(pos);
            if (newPos != null)
                newPos.add(pos);
        }
    }
}
