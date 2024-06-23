package moe.denery.recovisuals.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import moe.denery.recovisuals.registry.FogConditionRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
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
    private static void changeFog(Camera camera, FogRenderer.FogMode fogMode, float viewDistance, boolean bl, float g, CallbackInfo ci, @Local FogRenderer.FogData fogData) {
        FogConditionRegistry registry = FogConditionRegistry.get();
        // fogData.start = viewDistance * registry.getCommonFogStartFactor();
        // fogData.end = viewDistance * registry.getCommonFogEndFactor();

        /*
        if (camera.getFluidInCamera() == FogType.WATER) {
            fogData.start = 2.0F;
            fogData.end = 4.5F;
        } else if (camera.getFluidInCamera() == FogType.NONE) {
            fogData.end = viewDistance * 1.2F;
        }
         */
        /*
        if (level.isRaining()) {
            fogData.end = viewDistance * 1.9F;
            fogData.start = viewDistance * 1.4F;
        }

        if (level.getBiome(player.getOnPos()).is(BiomeTags.IS_OCEAN)) {
            fogData.end = viewDistance * 1.9F;
            fogData.start = viewDistance * 1.4F;
        }
        */
    }

}
