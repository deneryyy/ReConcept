package moe.denery.recodynlights.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.function.Function;

public final class EntityLuminanceRegistry {
    private static final BiMap<EntityType<? extends Entity>, LuminanceRegistry<?>> ENTITY_TYPE_REGISTRY = HashBiMap.create();

    public static <T extends Entity> void register(EntityType<T> entityType, Function<T, Boolean> condition, int luminanceAmount) {
        EntityLuminanceRegistry.ENTITY_TYPE_REGISTRY.computeIfAbsent(entityType, type -> {
            return new LuminanceRegistry<>((EntityType<Entity>) type);
        });
        ((LuminanceRegistry<T>) ENTITY_TYPE_REGISTRY.get(entityType)).register(condition, luminanceAmount);
    }

    @ApiStatus.Internal
    public static <T extends Entity> int getLuminance(EntityType<T> entityType, T entity) {
        LuminanceRegistry<T> luminanceRegistry = (LuminanceRegistry<T>) EntityLuminanceRegistry.ENTITY_TYPE_REGISTRY.get(entityType);
        if (luminanceRegistry == null)
            return 0;
        List<Integer> applied = new ArrayList<>();
        for (Function<T, Boolean>  condition : luminanceRegistry.conditions()) {
            if (condition.apply(entity))
                applied.add(luminanceRegistry.get(condition));
        }
        try {
            return Collections.max(applied);
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    private static class LuminanceRegistry<T extends Entity> {
        private final Map<Function<T, Boolean>, Integer> luminanceRegistry = new HashMap<>();
        private final EntityType<T> entityType;

        private LuminanceRegistry(EntityType<T> entityType) {
            this.entityType = entityType;
        }

        public void register(Function<T, Boolean> condition, int luminance) {
            this.luminanceRegistry.put(condition, luminance);
        }

        public int get(Function<T, Boolean> condition) {
            Integer luminance = this.luminanceRegistry.get(condition);
            return luminance == null ? 0 : luminance;
        }

        public Set<Function<T, Boolean>> conditions() {
            return this.luminanceRegistry.keySet();
        }

        public EntityType<T> getEntityType() {
            return entityType;
        }
    }
}
