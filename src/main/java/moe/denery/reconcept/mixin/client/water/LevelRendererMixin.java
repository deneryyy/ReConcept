package moe.denery.reconcept.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    // water rendering experiment

    @Inject(
            method = "renderSectionLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setupShaderLights(Lnet/minecraft/client/renderer/ShaderInstance;)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void waterFogStart(
            RenderType renderType,
            double d,
            double e,
            double f,
            Matrix4f matrix4f,
            Matrix4f matrix4f2,
            CallbackInfo ci,
            @Local ShaderInstance shaderInstance
    ) {
        if (renderType == RenderType.translucent()) {
            //shaderInstance.FOG_START.set(0.0F);
            //shaderInstance.FOG_END.set(4.0F);
            float[] colors = new float[4];
            colors[0] = 0.0f;
            colors[1] = 0.0f;
            colors[2] = 1.0f;
            colors[3] = 1.0f;
            //shaderInstance.FOG_COLOR.set(colors);
            shaderInstance.COLOR_MODULATOR.set(colors);
        }
    }
}
