package moe.denery.reconcept.mixin.game;

import moe.denery.reconcept.item.throwing.ReConceptTorch;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Items.class)
public class ItemsMixin {

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/item/StandingAndWallBlockItem"
            ),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Items;OBSIDIAN:Lnet/minecraft/world/item/Item;"),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Items;END_ROD:Lnet/minecraft/world/item/Item;")
            )
    )
    private static StandingAndWallBlockItem changeTorch(Block block, Block block2, Item.Properties properties, Direction direction) {
        return new ReConceptTorch(Blocks.TORCH, Blocks.WALL_TORCH, new Item.Properties(), Direction.DOWN);
    }
}
