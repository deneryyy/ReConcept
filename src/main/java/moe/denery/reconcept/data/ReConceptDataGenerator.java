package moe.denery.reconcept.data;

import moe.denery.reconcept.data.loot.ReConceptBlockLootTables;
import moe.denery.reconcept.data.models.ReConceptModels;
import moe.denery.reconcept.data.recipe.ReConceptRecipes;
import moe.denery.reconcept.data.recipe.VanillaOverrideRecipes;
import moe.denery.reconcept.data.tag.ReConceptBlockTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ReConceptDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(ReConceptBlockTags::new);
        pack.addProvider(ReConceptBlockLootTables::new);
        pack.addProvider(VanillaOverrideRecipes::new);
        pack.addProvider(ReConceptRecipes::new);
        pack.addProvider(ReConceptModels::new);
    }
}
