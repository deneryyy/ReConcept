package moe.denery.reconcept.data.models;

import moe.denery.reconcept.block.ReConceptBlocks;
import moe.denery.reconcept.item.ReConceptItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;

public class ReConceptModels extends FabricModelProvider {
    public ReConceptModels(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createAxisAlignedPillarBlock(ReConceptBlocks.TUFT_BLOCK, TexturedModel.COLUMN);
        blockStateModelGenerator.createCrossBlockWithDefaultItem(ReConceptBlocks.JVNE_FLOWER, BlockModelGenerators.TintState.NOT_TINTED);
        blockStateModelGenerator.createCrossBlockWithDefaultItem(ReConceptBlocks.AGONY_FLOWER, BlockModelGenerators.TintState.NOT_TINTED);
        blockStateModelGenerator.createCrossBlockWithDefaultItem(ReConceptBlocks.HIGANBANA, BlockModelGenerators.TintState.NOT_TINTED);

        // Flat Ofuda model
        blockStateModelGenerator.createNonTemplateHorizontalBlock(ReConceptBlocks.OFUDA_OF_RESURRECTION);
        blockStateModelGenerator.createSimpleFlatItemModel(ReConceptBlocks.OFUDA_OF_RESURRECTION);


    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ReConceptItems.TUFT, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ReConceptItems.UTILITY_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(ReConceptItems.BARK, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ReConceptItems.STONE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ReConceptItems.WOODEN_MATERIAL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ReConceptItems.UNLIT_TORCH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ReConceptItems.CACTUS_ON_A_STICK, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

}
