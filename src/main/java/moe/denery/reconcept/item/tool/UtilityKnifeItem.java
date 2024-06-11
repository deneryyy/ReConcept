package moe.denery.reconcept.item.tool;

import moe.denery.reconcept.block.BlockUtil;
import moe.denery.reconcept.item.ReConceptItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class UtilityKnifeItem extends Item {

    public UtilityKnifeItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos clickedPos = useOnContext.getClickedPos();
        BlockState clickedBlock = level.getBlockState(clickedPos);
        Block block = clickedBlock.getBlock();
        Player player = useOnContext.getPlayer();
        if (player == null)
            return InteractionResult.PASS;

        if (BlockUtil.isNotStrippedLog(block)) {
            return BlockUtil.getStripped(clickedBlock).map(stripped -> {
                // Log strip
                level.playSound(useOnContext.getPlayer(), clickedPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlockAndUpdate(clickedPos, stripped);
                // Durability change
                ItemStack utilityKnife = useOnContext.getItemInHand();
                utilityKnife.hurtAndBreak(1, useOnContext.getPlayer(), EquipmentSlot.MAINHAND);
                // Bark drop
                Vec3 spawnVector = Vec3.atLowerCornerWithOffset(clickedPos, 0.5, 0.5, 0.5).offsetRandom(level.random, 0.7f);
                ItemStack bark = new ItemStack(ReConceptItems.BARK);
                ItemEntity barkEntity = new ItemEntity(level, spawnVector.x(), spawnVector.y(), spawnVector.z(), bark);
                barkEntity.setDefaultPickUpDelay();
                level.addFreshEntity(barkEntity);
                return InteractionResult.SUCCESS;
            }).orElse(InteractionResult.PASS);
        }
        return super.useOn(useOnContext);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        itemStack.hurtAndBreak(2, livingEntity2, EquipmentSlot.MAINHAND);
        return true;
    }
}
