package moe.denery.reconcept.mixin.vanillaremove;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public interface ItemAccessor {
    @Accessor
    @Mutable
    void setRequiredFeatures(FeatureFlagSet requiredFeatures);
}
