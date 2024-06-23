package moe.denery.reconcept.data.recipe;

import moe.denery.reconcept.item.ReConceptItems;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.CLAY_BALL), RecipeCategory.MISC, Items.BRICK, 0.35F, 400)
                .unlockedBy("has_clay_ball", FabricRecipeProvider.has(Items.CLAY_BALL))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReConceptItems.CACTUS_ON_A_STICK)
                .define('C', Items.CACTUS)
                .define('S', Items.STICK)
                .pattern("C")
                .pattern("S")
                .unlockedBy("has_cactus", FabricRecipeProvider.has(Items.CACTUS))
                .unlockedBy("has_stick", FabricRecipeProvider.has(Items.STICK))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.PAPER, 3)
                .define('S', Items.SUGAR_CANE)
                .pattern("SS")
                .pattern("S ")
                .unlockedBy("has_sugar_cane", FabricRecipeProvider.has(Items.SUGAR_CANE))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReConceptItems.OFUDA_OF_RESURRECTION)
                .define('H', ReConceptItems.HIGANBANA)
                .define('P', Items.PAPER)
                .pattern("PH")
                .pattern("P ")
                .unlockedBy("has_paper", FabricRecipeProvider.has(Items.PAPER))
                .unlockedBy("has_higanbana", FabricRecipeProvider.has(ReConceptItems.HIGANBANA))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReConceptItems.CONSTRUCTION_TABLE)
                .define('W', ReConceptItems.WOODEN_MATERIAL)
                .define('B', Items.BRICK)
                .pattern("BB")
                .pattern("WW")
                .unlockedBy("has_wooden_material", FabricRecipeProvider.has(ReConceptItems.WOODEN_MATERIAL))
                .unlockedBy("has_brick", FabricRecipeProvider.has(Items.BRICK))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReConceptItems.POLISHING_TABLE)
                .define('S', ReConceptItems.STONE)
                .define('B', Items.BRICK)
                .pattern("SS")
                .pattern("BB")
                .unlockedBy("has_wooden_material", FabricRecipeProvider.has(ReConceptItems.WOODEN_MATERIAL))
                .unlockedBy("has_brick", FabricRecipeProvider.has(Items.BRICK))
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.CRAFTING_TABLE)
                .define('W', ReConceptItems.WOODEN_MATERIAL)
                .pattern("WW")
                .pattern("WW")
                .unlockedBy("has_wooden_material", FabricRecipeProvider.has(ReConceptItems.WOODEN_MATERIAL))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BOWL)
                .define('B', ReConceptItems.BARK)
                .pattern("BB")
                .unlockedBy("has_wooden_material", FabricRecipeProvider.has(ReConceptItems.WOODEN_MATERIAL))
                .save(exporter);
    }
}
