package moe.denery.reconcept.client;

import moe.denery.recodynlights.api.EntityLuminanceRegistry;
import moe.denery.recodynlights.api.PlayerLuminanceRegistry;
import moe.denery.reconcept.entity.ReConceptEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class DynamicLights {
    public static void initialize() {
        PlayerLuminanceRegistry.register(player -> {
            Item mainHandItem = player.getMainHandItem().getItem();
            Item offhandItem = player.getOffhandItem().getItem();
            return mainHandItem.equals(Items.TORCH) || offhandItem.equals(Items.TORCH);
        }, 15);

        EntityLuminanceRegistry.register(ReConceptEntities.THROWN_ITEM, thrownItemEntity -> {
            return thrownItemEntity.getItem().is(Items.TORCH);
        }, 15);

        EntityLuminanceRegistry.register(EntityType.ITEM, item -> {
            return item.getItem().is(Items.TORCH);
        }, 15);
    }
}
