package moe.denery.reconcept.data.loot;

import moe.denery.reconcept.block.ReConceptBlocks;
import moe.denery.reconcept.item.ReConceptItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

public class ReConceptBlockLootTables extends FabricBlockLootTableProvider {
    public static final float[] RECONCEPT_LEAVES_STICK_CHANCES = new float[]{0.25F, 0.29F, 0.32F, 0.35F, 0.37F};
    private static final float[] JUNGLE_LEAVES_SAPLING_CHANGES = new float[]{0.025f, 0.027777778f, 0.03125f, 0.041666668f, 0.1f};

    public ReConceptBlockLootTables(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.dropSelf(ReConceptBlocks.JVNE_FLOWER);
        this.dropSelf(ReConceptBlocks.AGONY_FLOWER);
        this.dropSelf(ReConceptBlocks.TUFT_BLOCK);
        this.dropSelf(ReConceptBlocks.HIGANBANA);
        this.dropSelf(ReConceptBlocks.OFUDA_OF_RESURRECTION);
        this.dropSelf(ReConceptBlocks.CONSTRUCTION_TABLE);
        this.dropSelf(ReConceptBlocks.POLISHING_TABLE);

        // Makes tuft drop
        add(Blocks.SHORT_GRASS, this::createReConceptGrassDrops);
        add(Blocks.FERN, this::createReConceptGrassDrops);

        // Changes stick drop chances
        this.add(Blocks.OAK_LEAVES, (Block block) -> this.createReConceptOakLeavesDrops(block, Blocks.OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.SPRUCE_LEAVES, (Block block) -> this.createReConceptLeavesDrops(block, Blocks.SPRUCE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.BIRCH_LEAVES, (Block block) -> this.createReConceptLeavesDrops(block, Blocks.BIRCH_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.JUNGLE_LEAVES, (Block block) -> this.createReConceptLeavesDrops(block, Blocks.JUNGLE_SAPLING, JUNGLE_LEAVES_SAPLING_CHANGES));
        this.add(Blocks.ACACIA_LEAVES, (Block block) -> this.createReConceptLeavesDrops(block, Blocks.ACACIA_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.DARK_OAK_LEAVES, (Block block) -> this.createReConceptOakLeavesDrops(block, Blocks.DARK_OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.CHERRY_LEAVES, (Block block) -> this.createReConceptLeavesDrops(block, Blocks.CHERRY_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.AZALEA_LEAVES, (Block block) -> this.createReConceptLeavesDrops(block, Blocks.AZALEA, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.FLOWERING_AZALEA_LEAVES, (Block block) -> this.createReConceptLeavesDrops(block, Blocks.FLOWERING_AZALEA, NORMAL_LEAVES_SAPLING_CHANCES));
    }

    public LootTable.Builder createReConceptGrassDrops(Block grass) {
        return BlockLootSubProvider.createShearsDispatchTable(
                grass,
                this.applyExplosionDecay(grass, LootItem.lootTableItem(Items.WHEAT_SEEDS)
                        .when(LootItemRandomChanceCondition.randomChance(0.125F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.FORTUNE, 2))
        ).withPool(
                LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .when(HAS_NO_SHEARS_OR_SILK_TOUCH)
                    .add(this.applyExplosionCondition(grass, LootItem.lootTableItem(ReConceptItems.TUFT)).when(LootItemRandomChanceCondition.randomChance(0.25F)))
        );
    }

    public LootTable.Builder createReConceptLeavesDrops(Block leaves, Block sapling, float... saplingChances) {
        return BlockLootSubProvider.createSilkTouchOrShearsDispatchTable(
                leaves,
                this.applyExplosionCondition(
                        leaves,
                        LootItem.lootTableItem(sapling)
                ).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, saplingChances))
        ).withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(HAS_NO_SHEARS_OR_SILK_TOUCH)
                        .add(this.applyExplosionDecay(leaves, LootItem.lootTableItem(Items.STICK)/*.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))*/)
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, RECONCEPT_LEAVES_STICK_CHANCES)))
        );
    }

    public LootTable.Builder createReConceptOakLeavesDrops(Block leaves, Block sapling, float... saplingChances) {
        return this.createReConceptLeavesDrops(leaves, sapling, saplingChances)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(this.applyExplosionCondition(leaves, LootItem.lootTableItem(Items.APPLE)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, 0.005f, 0.0055555557f, 0.00625f, 0.008333334f, 0.025f))));
    }
}
