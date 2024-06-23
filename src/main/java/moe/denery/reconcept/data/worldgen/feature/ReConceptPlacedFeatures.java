package moe.denery.reconcept.data.worldgen.feature;

import moe.denery.reconcept.ReConcept;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class ReConceptPlacedFeatures {
    public static final ResourceKey<PlacedFeature> FLOWER_JUNGLE_RECONCEPT = ReConceptPlacedFeatures.createKey("flower_jungle_reconcept");

    public static void bootstrap(BootstrapContext<PlacedFeature> registryBootstrap) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureHolderGetter = registryBootstrap.lookup(Registries.CONFIGURED_FEATURE);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerJungleReconceptConfiguredFeature = configuredFeatureHolderGetter.getOrThrow(ReConceptConfiguredFeatures.FLOWER_JUNGLE_RECONCEPT);
        PlacementUtils.register(
                registryBootstrap,
                ReConceptPlacedFeatures.FLOWER_JUNGLE_RECONCEPT,
                flowerJungleReconceptConfiguredFeature,
                RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        );
    }

    public static void generate(HolderLookup.Provider registries, FabricDynamicRegistryProvider.Entries entries) {
        entries.add(registries.lookupOrThrow(Registries.PLACED_FEATURE), ReConceptPlacedFeatures.FLOWER_JUNGLE_RECONCEPT);
    }

    public static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ReConcept.createReConcept(name));
    }
}
