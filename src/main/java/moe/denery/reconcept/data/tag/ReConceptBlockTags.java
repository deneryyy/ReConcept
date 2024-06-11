package moe.denery.reconcept.data.tag;

import moe.denery.reconcept.ReConcept;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public final class ReConceptBlockTags extends FabricTagProvider.BlockTagProvider {
    public static final TagKey<Block> MINEABLE_WITH_HOE = TagKey.create(Registries.BLOCK, ReConcept.createReConcept("mineable/hoe"));

    public ReConceptBlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE).add(
                Blocks.NETHER_WART_BLOCK,
                Blocks.WARPED_WART_BLOCK,
                Blocks.HAY_BLOCK,
                Blocks.DRIED_KELP_BLOCK,
                Blocks.TARGET,
                Blocks.SHROOMLIGHT,
                Blocks.SPONGE,
                Blocks.WET_SPONGE,
                Blocks.SCULK_SENSOR,
                Blocks.CALIBRATED_SCULK_SENSOR,
                Blocks.MOSS_BLOCK,
                Blocks.MOSS_CARPET,
                Blocks.SCULK,
                Blocks.SCULK_CATALYST,
                Blocks.SCULK_VEIN,
                Blocks.SCULK_SHRIEKER,
                Blocks.PINK_PETALS
        );
    }
}
