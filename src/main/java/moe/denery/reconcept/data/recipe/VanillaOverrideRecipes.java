package moe.denery.reconcept.data.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class VanillaOverrideRecipes extends FabricRecipeProvider {
    public VanillaOverrideRecipes(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        FirstStageRecipes.generateFirstStageVanillaOverrideRecipes(exporter);
    }

    @Override
    protected ResourceLocation getRecipeIdentifier(ResourceLocation identifier) {
        return new ResourceLocation("minecraft", identifier.getPath());
    }

    @Override
    public String getName() {
        return "VanillaRecipes";
    }
}
