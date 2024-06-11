package moe.denery.reconcept.data.recipe;

import moe.denery.reconcept.item.ReConceptItems;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public final class FirstStageRecipes {
    public static void generateFirstStageReConceptRecipes(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.STRING, 4)
                .requires(ItemTags.WOOL)
                .unlockedBy("has_wool", FabricRecipeProvider.has(ItemTags.WOOL))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ReConceptItems.UTILITY_KNIFE)
                .requires(Items.STICK)
                .requires(ReConceptItems.TUFT)
                .requires(Items.FLINT)
                .unlockedBy("has_stick", FabricRecipeProvider.has(Items.STICK))
                .unlockedBy("has_tuft", FabricRecipeProvider.has(ReConceptItems.TUFT))
                .unlockedBy("has_flint", FabricRecipeProvider.has(Items.FLINT))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ReConceptItems.AGONY_FLOWER)
                .requires(ItemTags.COALS)
                .requires(ReConceptItems.JVNE_FLOWER)
                .unlockedBy("has_jvne_flower", FabricRecipeProvider.has(ReConceptItems.JVNE_FLOWER))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReConceptItems.TUFT_BLOCK)
                .define('T', ReConceptItems.TUFT)
                .pattern("TT")
                .pattern("TT")
                .unlockedBy("has_tuft", FabricRecipeProvider.has(ReConceptItems.TUFT))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ReConceptItems.TUFT, 4)
                .requires(ReConceptItems.TUFT_BLOCK)
                .unlockedBy("has_tuft", FabricRecipeProvider.has(ReConceptItems.TUFT))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ReConceptItems.WOODEN_MATERIAL)
                .requires(ReConceptItems.BARK)
                .requires(ReConceptItems.TUFT)
                .requires(Items.SUGAR_CANE)
                .requires(Items.FLINT)
                .unlockedBy("has_bark", FabricRecipeProvider.has(ReConceptItems.BARK))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ReConceptItems.UNLIT_TORCH)
                .requires(ReConceptItems.TUFT)
                .requires(Items.STICK)
                .unlockedBy("has_stick", FabricRecipeProvider.has(Items.STICK))
                .unlockedBy("has_tuft", FabricRecipeProvider.has(ReConceptItems.TUFT))
                .save(exporter);
    }

    public static void generateFirstStageVanillaOverrideRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.CAMPFIRE)
                .define('T', ReConceptItems.TUFT)
                .define('B', ReConceptItems.BARK)
                .define('S', Items.STICK)
                .pattern("BS")
                .pattern("TT")
                .unlockedBy("has_stick", FabricRecipeProvider.has(Items.STICK))
                .unlockedBy("has_tuft", FabricRecipeProvider.has(ReConceptItems.TUFT))
                .unlockedBy("has_bark", FabricRecipeProvider.has(ReConceptItems.BARK))
                .save(exporter);

        // TODO boats and beds
    }
}
