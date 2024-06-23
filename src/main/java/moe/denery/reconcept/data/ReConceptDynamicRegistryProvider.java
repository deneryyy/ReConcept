package moe.denery.reconcept.data;

import moe.denery.reconcept.data.worldgen.biome.ReConceptBiomes;
import moe.denery.reconcept.data.worldgen.feature.ReConceptConfiguredFeatures;
import moe.denery.reconcept.data.worldgen.feature.ReConceptPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ReConceptDynamicRegistryProvider extends FabricDynamicRegistryProvider {
    public ReConceptDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        ReConceptConfiguredFeatures.generate(registries, entries);
        ReConceptPlacedFeatures.generate(registries, entries);
        ReConceptBiomes.generate(registries, entries);
    }

    @Override
    public String getName() {
        return "ReConcept";
    }
}
