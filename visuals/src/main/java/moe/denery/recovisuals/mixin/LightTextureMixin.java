package moe.denery.recovisuals.mixin;

import moe.denery.recovisuals.registry.DarkeningConditionRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightTexture.class)
public class LightTextureMixin {
    @Final @Shadow private Minecraft minecraft;

    @Unique private float darkeningAmount;
    @Unique private float dayCycleValue;


    @Inject(method = "<init>", at = @At("TAIL"))
    private void registerConditions(GameRenderer gameRenderer, Minecraft minecraft, CallbackInfo ci) {
        DarkeningConditionRegistry registry = DarkeningConditionRegistry.get();
        registry.registerCondition(DarkeningConditionRegistry.DarkeningCondition.of(() -> this.dayCycleValue <= -0.05F, 0.25F, 0.0025F));
        registry.registerCondition(DarkeningConditionRegistry.DarkeningCondition.of(() -> {
            ClientLevel level = minecraft.level;
            if (level == null) return false;
            LocalPlayer player = minecraft.player;
            if (player == null) return false;

            return player.getBlockStateOn().is(Blocks.CAVE_AIR);
        }, 0.25F, 0.0025F));
        registry.registerCondition(DarkeningConditionRegistry.DarkeningCondition.of(() -> {
            ClientLevel level = minecraft.level;
            if (level == null) return false;

            return level.isRaining();
        }, 0.10F, 0.00125F));
    }

    @Inject(method = "calculateDarknessScale", at = @At("RETURN"), cancellable = true)
    private void changeDarknessDuringNight(LivingEntity livingEntity, float f, float g, CallbackInfoReturnable<Float> cir) {
        ClientLevel level = minecraft.level;
        if (level == null)
            return;

        float dayTime = level.getTimeOfDay(f);
        this.dayCycleValue = Mth.cos(dayTime * ((float) Math.PI * 2));
        cir.setReturnValue(Mth.clamp(this.darkeningAmount + cir.getReturnValueF(), 0.0F, 2.0F));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickConditions(CallbackInfo ci) {
        DarkeningConditionRegistry.get().tick();
        this.darkeningAmount = DarkeningConditionRegistry.get().getCommonDarkeningAmount();
    }
}
