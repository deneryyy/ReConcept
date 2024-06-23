package moe.denery.reconcept.mixin.vanillaremove;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.class)
public interface BlockBehaviourAccessor {
    @Accessor
    @Mutable
    void setRequiredFeatures(FeatureFlagSet features);
}
