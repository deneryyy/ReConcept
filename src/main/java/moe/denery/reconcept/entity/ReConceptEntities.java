package moe.denery.reconcept.entity;

import moe.denery.reconcept.ReConcept;
import moe.denery.reconcept.entity.thrown.ThrownItemEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ReConceptEntities {
    public static final EntityType<ThrownItemEntity> THROWN_ITEM = register(
            "thrown_item",
            EntityType.Builder.<ThrownItemEntity>of(ThrownItemEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build()
    );

    public static void registerEntities() {
        // FabricDefaultAttributeRegistry.register(THROWN_ITEM, AbstractArrow);
    }

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
        return Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                ReConcept.createReConcept(name),
                entity
        );
    }
}
