package moe.denery.reconcept.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightTexture.class)
public class LightTextureMixin {
    @Final @Shadow private Minecraft minecraft;
/*
    // More red and nether like darkness, less visibility
    @Inject(method = "getDarknessGamma", at = @At("HEAD"), cancellable = true)
    private void changeDarkness(float f, CallbackInfoReturnable<Float> cir) {
        ClientLevel level = this.minecraft.level;
        if (level == null)
            return;

        float dayTime = level.getTimeOfDay(f);
        float i = Mth.cos(dayTime * ((float)Math.PI * 2)) - 0.0F;
        if (i <= -0.3f) {
            cir.setReturnValue(0.8F);
        }
    }

 */
    @Inject(method = "calculateDarknessScale", at = @At("HEAD"), cancellable = true)
    private void changeDarknessDuringNight(LivingEntity livingEntity, float f, float g, CallbackInfoReturnable<Float> cir) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null)
            return;

        float dayTime = level.getTimeOfDay(f);
        float dayCycleValue = Mth.cos(dayTime * ((float)Math.PI * 2)) - 0.0F;
        if (dayCycleValue <= -0.5F) {
            cir.setReturnValue(0.25F);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickAdditions(CallbackInfo ci) {

    }
}
