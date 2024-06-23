package moe.denery.recovisuals.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public final class DarkeningConditionRegistry {
    private static final DarkeningConditionRegistry INSTANCE = new DarkeningConditionRegistry();

    private final List<DarkeningCondition> conditions = new ArrayList<>();

    private DarkeningConditionRegistry() {}

    public void registerCondition(DarkeningCondition darkeningCondition) {
        this.conditions.add(darkeningCondition);
    }

    public float getCommonDarkeningAmount() {
        return (float) this.conditions.stream()
                .mapToDouble(DarkeningCondition::getDarkeningAmount)
                .max()
                .orElse(0.0D);
    }

    public void tick() {
        for (DarkeningCondition condition : this.conditions) {
            condition.tick();
        }
    }

    public static DarkeningConditionRegistry get() {
        return DarkeningConditionRegistry.INSTANCE;
    }

    public static class DarkeningCondition {
        private final BooleanSupplier condition;
        private final float maximum;
        private final float tickChange;

        private float darkeningAmount = 0.0F;

        public DarkeningCondition(BooleanSupplier condition, float maximum, float tickChange) {
            this.condition = condition;
            this.maximum = maximum;
            this.tickChange = tickChange;
        }

        public static DarkeningCondition of(BooleanSupplier condition, float maximum, float tickChange) {
            return new DarkeningCondition(condition, maximum, tickChange);
        }

        public void tick() {
            if (condition.getAsBoolean()) {
                if (this.darkeningAmount < maximum) {
                    this.darkeningAmount += tickChange;
                }
            } else {
                if (this.darkeningAmount > 0.0F) {
                    this.darkeningAmount -= tickChange;
                }
            }
        }

        public float getDarkeningAmount() {
            return darkeningAmount;
        }
    }
}
