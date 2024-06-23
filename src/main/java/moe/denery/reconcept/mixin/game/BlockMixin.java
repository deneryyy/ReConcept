package moe.denery.reconcept.mixin.game;

import moe.denery.reconcept.item.ReConceptItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Block.class)
public class BlockMixin {

    @Unique
    private static final RandomSource RANDOM_SOURCE = RandomSource.create();

    @Inject(
            method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void sandLoot(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack itemStack, CallbackInfoReturnable<List<ItemStack>> cir) {
        // Kind of hacky way of making per biome drops, tho I doubt block loot tables have such a functionality in them
        if (blockState.is(Blocks.SAND) && serverLevel.getBiome(blockPos) == Biomes.RIVER && RANDOM_SOURCE.nextFloat() < 0.1F) {
            cir.setReturnValue(List.of(new ItemStack(ReConceptItems.STONE)));
        }
    }

    @Inject(
            method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;)Ljava/util/List;",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void sandLoot2(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, @Nullable BlockEntity blockEntity, CallbackInfoReturnable<List<ItemStack>> cir) {
        // Kind of hacky way of making per biome drops, tho I doubt block loot tables have such a functionality in them
        if (blockState.is(Blocks.SAND) && serverLevel.getBiome(blockPos) == Biomes.RIVER && RANDOM_SOURCE.nextFloat() < 0.1F) {
            cir.setReturnValue(List.of(new ItemStack(ReConceptItems.STONE)));
        }
    }
}
