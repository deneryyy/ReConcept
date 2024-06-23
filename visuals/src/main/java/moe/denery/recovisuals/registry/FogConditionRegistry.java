package moe.denery.recovisuals.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public final class FogConditionRegistry {
    private static final FogConditionRegistry INSTANCE = new FogConditionRegistry();

    private final List<FogCondition> conditions = new ArrayList<>();

    private FogConditionRegistry() {}

    public void registerCondition(FogCondition condition) {
        this.conditions.add(condition);
    }

    public void tick() {
        for (FogCondition condition : conditions) {
            condition.tick();
        }
    }

    public float getCommonFogStartFactor() {
        return (float) this.conditions.stream()
                .mapToDouble(FogCondition::getFogStartFactor)
                .max()
                .orElse(0.0D);
    }

    public float getCommonFogEndFactor() {
        return (float) this.conditions.stream()
                .mapToDouble(FogCondition::getFogEndFactor)
                .max()
                .orElse(0.0D);
    }

    public static FogConditionRegistry get() {
        return FogConditionRegistry.INSTANCE;
    }

    public static class FogCondition {
        private final BooleanSupplier condition;
        private final float maximumFogStart;
        private final float maximumFogEnd;
        private final float tickChange;

        private float fogStartFactor = 1.0F;
        private float fogEndFactor = 1.0F;

        private FogCondition(BooleanSupplier condition, float maximumFogStart, float maximumFogEnd, float tickChange) {
            this.condition = condition;
            this.maximumFogStart = maximumFogStart;
            this.maximumFogEnd = maximumFogEnd;
            this.tickChange = tickChange;
        }

        public void tick() {
            if (condition.getAsBoolean()) {
                if (this.fogStartFactor < this.maximumFogStart) {
                    this.fogStartFactor += tickChange;
                } else if (this.fogStartFactor > this.maximumFogStart) {
                    this.fogStartFactor = this.maximumFogStart;
                }
                if (this.fogEndFactor < this.maximumFogEnd) {
                    this.fogEndFactor += tickChange;
                } else if (this.fogEndFactor > this.maximumFogEnd) {
                    this.fogEndFactor = this.maximumFogEnd;
                }
            } else {
                if (this.fogStartFactor > 1.0F) {
                    this.fogStartFactor -= tickChange;
                } else if (this.fogStartFactor < 1.0F) {
                    this.fogStartFactor = 1.0F;
                }
                if (this.fogEndFactor > 1.0F) {
                    this.fogEndFactor -= tickChange;
                } else if (this.fogEndFactor < 1.0F) {
                    this.fogEndFactor = 1.0F;
                }
            }
        }

        public float getFogStartFactor() {
            return fogStartFactor;
        }

        public float getFogEndFactor() {
            return fogEndFactor;
        }

        public static FogCondition of(BooleanSupplier condition, float maximumFogStart, float maximumFogEnd, float tickChange) {
            return new FogCondition(condition, maximumFogStart, maximumFogEnd, tickChange);
        }
    }

    interface FogConditionFunction {
        boolean apply();
    }
}
