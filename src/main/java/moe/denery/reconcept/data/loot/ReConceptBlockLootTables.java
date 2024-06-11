package moe.denery.reconcept.data;

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
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

public class ReConceptBlockLootTables extends FabricBlockLootTableProvider {
    protected ReConceptBlockLootTables(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        add(Blocks.SHORT_GRASS, this::createReConceptGrassDrops);
        add(Blocks.FERN, this::createReConceptGrassDrops);
    }

    public LootTable.Builder createReConceptGrassDrops(Block block) {
        return BlockLootSubProvider.createShearsDispatchTable(
                block,
                this.applyExplosionDecay(block, LootItem.lootTableItem(Items.WHEAT_SEEDS)
                        .when(LootItemRandomChanceCondition.randomChance(0.125F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.FORTUNE, 2))
        ).withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(HAS_NO_SHEARS_OR_SILK_TOUCH)
                .add(this.applyExplosionCondition(block, LootItem.lootTableItem(ReConceptItems.TUFT)).when(LootItemRandomChanceCondition.randomChance(0.25F)))
        );
    }
}
