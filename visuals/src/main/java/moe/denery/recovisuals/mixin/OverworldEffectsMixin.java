package moe.denery.recovisuals.mixin;

import moe.denery.recovisuals.ReCoVisuals;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionSpecialEffects.OverworldEffects.class)
public class OverworldEffectsMixin {
    @Inject(
            method = "getBrightnessDependentFogColor",
            at = @At("RETURN"),
            cancellable = true
    )
    private void changeNightFogColor(Vec3 vec3, float dayCycleValue, CallbackInfoReturnable<Vec3> cir) {
        ReCoVisuals.updateDayCycleValue(dayCycleValue);
        float nightFogFade = ReCoVisuals.getNightFogFade();
        Vec3 originalReturn = cir.getReturnValue();
        cir.setReturnValue(new Vec3(
                Mth.lerp(nightFogFade, originalReturn.x, 0.0F),
                Mth.lerp(nightFogFade, originalReturn.y, 0.0F),
                Mth.lerp(nightFogFade, originalReturn.z, 0.0F)
        ));
    }


}
