package moe.denery.reconcept.block.ofuda;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class OfudaOfResurrectionBlock extends OfudaBlock {
    public OfudaOfResurrectionBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        ServerPlayer serverPlayer;
        if (!(level.isClientSide || (serverPlayer = (ServerPlayer) player).getRespawnDimension() == level.dimension() && blockPos.equals(serverPlayer.getRespawnPosition()))) {
            serverPlayer.setRespawnPosition(level.dimension(), blockPos, 0.0f, false, true);
            level.playSound(null, (double) blockPos.getX() + 0.5, (double) blockPos.getY() + 0.5, (double) blockPos.getZ() + 0.5, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
