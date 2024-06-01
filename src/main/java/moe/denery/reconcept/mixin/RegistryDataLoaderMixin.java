package moe.denery.reconcept.mixin;

import com.google.gson.JsonElement;
import com.mojang.serialization.Decoder;
import moe.denery.reconcept.ReConceptVanillaRemove;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RegistryDataLoader.class)
public class RegistryDataLoaderMixin {

    @Inject(
            method = "loadElementFromResource",
            at = @At("HEAD"),
            cancellable = true
    )
    private static <E> void datapackRegistryElementLoadingHook(WritableRegistry<E> writableRegistry, Decoder<E> decoder, RegistryOps<JsonElement> registryOps, ResourceKey<E> resourceKey, Resource resource, RegistrationInfo registrationInfo, CallbackInfo ci) {
        if (((ResourceKey<? extends Registry<?>>) writableRegistry.key()) == Registries.CONFIGURED_FEATURE) {
            if (ReConceptVanillaRemove.REMOVED_VANILLA_CONFIGURED_FEATURES.contains(resourceKey)) {
                ci.cancel();
            }
        } else if (((ResourceKey<? extends Registry<?>>) writableRegistry.key()) == Registries.PLACED_FEATURE) {
            if (ReConceptVanillaRemove.REMOVED_VANILLA_PLACED_FEATURES.contains(resourceKey)) {
                ci.cancel();
            }
        }
    }
}
