package moe.denery.reconcept.mixin;

import moe.denery.reconcept.ReConceptVanillaRemove;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(MappedRegistry.class)
public class MappedRegistryMixin<T> {

    @Shadow @Final
    private Map<ResourceKey<T>, Holder.Reference<T>> byKey;

    @Shadow @Final
    ResourceKey<? extends Registry<T>> key;

    @Inject(
            method = "freeze",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V"
            )
    )
    private void vanillaRemoveFreezingFix(CallbackInfoReturnable<Registry<T>> cir) {
        if (((ResourceKey<? extends Registry<?>>) this.key) == Registries.CONFIGURED_FEATURE) {
            for (ResourceKey<ConfiguredFeature<?, ?>> removedVanillaConfiguredFeature : ReConceptVanillaRemove.REMOVED_VANILLA_CONFIGURED_FEATURES) {
                this.byKey.remove(removedVanillaConfiguredFeature);
            }
        } else if (((ResourceKey<? extends Registry<?>>) this.key) == Registries.PLACED_FEATURE) {
            for (ResourceKey<PlacedFeature> removedVanillaConfiguredFeature : ReConceptVanillaRemove.REMOVED_VANILLA_PLACED_FEATURES) {
                this.byKey.remove(removedVanillaConfiguredFeature);
            }
        }
    }
}
