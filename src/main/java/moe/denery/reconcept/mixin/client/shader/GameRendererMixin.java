package moe.denery.reconcept.mixin.client.shader;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    // @Shadow private static ShaderInstance rendertypeTranslucentShader;

    /**
     * For Future. Shader use generally breaks compatibility with sodium.
     */
    @Inject(
            method = "reloadShaders",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;loadBlurEffect(Lnet/minecraft/server/packs/resources/ResourceProvider;)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void vanillaShaderReplacement(ResourceProvider resourceProvider, CallbackInfo ci) {
        // rendertypeTranslucentShader = ReConceptShaders.reconceptTranslucentShader();
    }

/*
    // The more red way of darkening nights

    @Shadow private float darkenWorldAmount;

    @Shadow private float darkenWorldAmountO;

    @Inject(method = "getDarkenWorldAmount", at = @At("HEAD"), cancellable = true)
    private void darkerNights(float f, CallbackInfoReturnable<Float> cir) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null)
            return;

        float dayTime = level.getTimeOfDay(f);
        float dayCycleValue = Mth.cos(dayTime * ((float)Math.PI * 2)) - 0.0F;
        if (dayCycleValue <= -0.3F) {
            float vanillaDarkening = Mth.lerp(f, this.darkenWorldAmountO, this.darkenWorldAmount);
            cir.setReturnValue(2.0F);
        }
    }
 */
}
