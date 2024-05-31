package moe.denery.reconcept.mixin;

import moe.denery.reconcept.ReConceptFeatureFlags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public class EntityTypeMixin {
    @Inject(method = "register", at = @At("HEAD"))
    private static <T extends Entity> void injected(String id, EntityType.Builder<T> builder, CallbackInfoReturnable<EntityType<T>> cir) {
        if (ReConceptFeatureFlags.VANILLA_TURNED_OFF_ENTITIES.contains(id)) {
            builder.requiredFeatures(ReConceptFeatureFlags.flags().vanillaTurnedOff());
        }
    }
}
