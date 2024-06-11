package moe.denery.reconcept.mixin.client.darkernight;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
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

    @Unique private float nightDarkenAmount;
    @Unique private float dayCycleValue;

    @Inject(method = "calculateDarknessScale", at = @At("RETURN"), cancellable = true)
    private void changeDarknessDuringNight(LivingEntity livingEntity, float f, float g, CallbackInfoReturnable<Float> cir) {
        ClientLevel level = minecraft.level;
        if (level == null)
            return;

        float dayTime = level.getTimeOfDay(f);
        this.dayCycleValue = Mth.cos(dayTime * ((float)Math.PI * 2));
        cir.setReturnValue(Mth.clamp(this.nightDarkenAmount + cir.getReturnValueF(), 0.0F, 2.0F));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickAdditions(CallbackInfo ci) {
        // Night darkening
        float dayTime = this.dayCycleValue;
        this.darkenTick(dayTime <= -0.05F, 0.25F, 0.0025F);

        // Raining darkening
        ClientLevel level = minecraft.level;
        if (level == null)
            return;
        this.darkenTick(level.isRaining(), 0.10F, 0.00125F);
    }

    @Unique
    private void darkenTick(boolean condition, float max, float amount) {
        if (condition) {
            if (this.nightDarkenAmount < max)
                this.nightDarkenAmount += amount;
        } else {
            if (this.nightDarkenAmount > 0.0F)
                this.nightDarkenAmount -= amount;
        }
    }
}
