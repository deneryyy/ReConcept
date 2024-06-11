package moe.denery.reconcept.mixin.game;

import moe.denery.reconcept.item.ReConceptItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ComposterBlock.class)
public class ComposterBlockMixin {
    @Unique
    private static final RandomSource RANDOM = RandomSource.create();

    @ModifyArg(
            method = "extractProduce",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V")
    )
    private static ItemLike a(ItemLike itemLike) {
        if (RANDOM.nextDouble() < 0.05F) {
            return ReConceptItems.JVNE_FLOWER;
        }
        return itemLike;
    }
}
