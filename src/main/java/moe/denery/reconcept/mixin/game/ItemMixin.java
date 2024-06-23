package moe.denery.reconcept.mixin.game;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(
            method = "isCorrectToolForDrops",
            at = @At("HEAD"),
            cancellable = true
    )
    private void leavesException(ItemStack itemStack, BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
        // Makes leave blocks drop sticks and other stuff no matter what instrument is used
        if (blockState.getBlock() instanceof LeavesBlock) {
            cir.setReturnValue(true);
        }
    }
}
