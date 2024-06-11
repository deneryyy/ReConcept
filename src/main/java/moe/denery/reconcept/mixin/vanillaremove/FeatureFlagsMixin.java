package moe.denery.reconcept.mixin.vanillaremove;

import com.llamalad7.mixinextras.sugar.Local;
import moe.denery.reconcept.ReConcept;
import moe.denery.reconcept.ReConceptFeatureFlags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagRegistry;
import net.minecraft.world.flag.FeatureFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FeatureFlags.class)
public class FeatureFlagsMixin {
    @Inject(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/flag/FeatureFlagRegistry$Builder;build()Lnet/minecraft/world/flag/FeatureFlagRegistry;"
            )
    )
    private static void reconceptFeatureFlags(CallbackInfo ci, @Local FeatureFlagRegistry.Builder builder) {
        // Makes vanilla remove feature flag for easier content removal
        final ResourceLocation vanillaTurnedOff = ReConcept.createReConcept("vanilla_remove");
        ReConceptFeatureFlags.setFlags(new ReConceptFeatureFlags(builder.create(vanillaTurnedOff)));
    }
}
