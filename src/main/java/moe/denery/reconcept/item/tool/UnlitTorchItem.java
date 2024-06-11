package moe.denery.reconcept.item.tool;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class UnlitTorchItem extends Item {
    public UnlitTorchItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        Player player = useOnContext.getPlayer();
        Level level = useOnContext.getLevel();
        if (player == null)
            return InteractionResult.PASS;

        BlockState blockState = level.getBlockState(useOnContext.getClickedPos());
        if (blockState.getBlock() instanceof CampfireBlock) {
            useOnContext.getItemInHand().shrink(1);
            player.addItem(new ItemStack(Items.TORCH));
            return InteractionResult.SUCCESS;
        }
        return super.useOn(useOnContext);
    }
}
