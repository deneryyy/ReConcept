package moe.denery.reconcept.mixin.vanillaremove;

import moe.denery.reconcept.ReConceptFeatureFlags;
import moe.denery.reconcept.ReConceptVanillaRemove;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Items.class)
public class ItemsMixin {
    @Inject(
            method = "registerItem(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/item/Item;)Lnet/minecraft/world/item/Item;",
            at = @At("HEAD")
    )
    private static void vanillaRemoveItems(ResourceKey<Item> resourceKey, Item item, CallbackInfoReturnable<Item> cir) {
        ItemAccessor itemAccessor = (ItemAccessor) item;
        if (ReConceptVanillaRemove.VANILLA_TURNED_OFF_ITEMS.contains(resourceKey.location().getPath())) {
            itemAccessor.setRequiredFeatures(FeatureFlagSet.of(ReConceptFeatureFlags.flags().vanillaTurnedOff()));
        }
    }
}
