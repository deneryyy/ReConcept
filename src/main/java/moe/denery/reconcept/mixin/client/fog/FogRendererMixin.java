package moe.denery.reconcept.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Inject(
            method = "setupFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V"
            )
    )
    private static void changeFog(Camera camera, FogRenderer.FogMode fogMode, float f, boolean bl, float g, CallbackInfo ci, @Local FogRenderer.FogData fogData) {
        if (camera.getFluidInCamera() == FogType.WATER) {
            fogData.start = 2.0F;
            fogData.end = 4.5F;
        } else if (camera.getFluidInCamera() == FogType.NONE) {
            // TODO cooler fog
            // fogData.end = fogData.end - 220F;
        }
    }
}
