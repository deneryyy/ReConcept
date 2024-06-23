package moe.denery.reconcept.data;

import moe.denery.reconcept.data.loot.ReConceptBlockLootTables;
import moe.denery.reconcept.data.models.ReConceptModels;
import moe.denery.reconcept.data.recipe.ReConceptRecipes;
import moe.denery.reconcept.data.recipe.VanillaOverrideRecipes;
import moe.denery.reconcept.data.tag.ReConceptBlockTags;
import moe.denery.reconcept.data.worldgen.biome.ReConceptBiomes;
import moe.denery.reconcept.data.worldgen.feature.ReConceptConfiguredFeatures;
import moe.denery.reconcept.data.worldgen.feature.ReConceptPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class ReConceptDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(ReConceptBlockTags::new);
        pack.addProvider(ReConceptBlockLootTables::new);
        pack.addProvider(VanillaOverrideRecipes::new);
        pack.addProvider(ReConceptRecipes::new);
        pack.addProvider(ReConceptModels::new);
        pack.addProvider(ReConceptDynamicRegistryProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registrySetBuilder) {
        registrySetBuilder.add(Registries.CONFIGURED_FEATURE, ReConceptConfiguredFeatures::bootstrap);
        registrySetBuilder.add(Registries.PLACED_FEATURE, ReConceptPlacedFeatures::bootstrap);
        registrySetBuilder.add(Registries.BIOME, ReConceptBiomes::bootstrap);
    }

}
