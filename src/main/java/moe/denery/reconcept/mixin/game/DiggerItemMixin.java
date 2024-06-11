package moe.denery.reconcept.mixin.game;

import com.llamalad7.mixinextras.sugar.Local;
import moe.denery.reconcept.data.tag.ReConceptBlockTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Tool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(DiggerItem.class)
public class DiggerItemMixin {


    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;component(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Lnet/minecraft/world/item/Item$Properties;"
            ),
            index = 1
    )
    private static <T> T changeHoe(T tool, @Local(argsOnly = true) TagKey<T> tagKey, @Local(argsOnly = true) Tier tier) {
        // Changes hoe's mineable blocks
        if (tagKey == BlockTags.MINEABLE_WITH_HOE) {
            return (T) DiggerItemMixin.createHoeProperties(tier);
        }
        return tool;
    }

    @Unique
    private static Tool createHoeProperties(Tier tier) {
        return new Tool(List.of(Tool.Rule.deniesDrops(tier.getIncorrectBlocksForDrops()), Tool.Rule.minesAndDrops(ReConceptBlockTags.MINEABLE_WITH_HOE, tier.getSpeed())), 1.0F, 1);
    }
}
