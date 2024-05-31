package moe.denery.reconcept.mixin;

import moe.denery.reconcept.ReConcept;
import moe.denery.reconcept.ReConceptFeatureFlags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagRegistry;
import net.minecraft.world.flag.FeatureFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FeatureFlags.class)
public class FeatureFlagsMixin {
    @Inject(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/flag/FeatureFlagRegistry$Builder;build()Lnet/minecraft/world/flag/FeatureFlagRegistry;"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void reconceptFeatureFlags(CallbackInfo ci, FeatureFlagRegistry.Builder builder) {
        final ResourceLocation vanillaTurnedOff = ReConcept.createReConcept("vanilla_turned_off");
        ReConceptFeatureFlags.setFlags(new ReConceptFeatureFlags(builder.create(vanillaTurnedOff)));
    }
}
