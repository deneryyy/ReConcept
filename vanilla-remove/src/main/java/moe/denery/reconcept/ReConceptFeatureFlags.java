package moe.denery.reconcept;

import net.minecraft.world.flag.FeatureFlag;

public record ReConceptFeatureFlags(FeatureFlag vanillaTurnedOff) {
    private static ReConceptFeatureFlags FLAGS;

    public static void setFlags(ReConceptFeatureFlags flags) {
        if (ReConceptFeatureFlags.FLAGS != null)
            throw new IllegalStateException("ReConcept feature flags are already initialized!");
        ReConceptFeatureFlags.FLAGS = flags;
    }

    public static ReConceptFeatureFlags flags() {
        if (ReConceptFeatureFlags.FLAGS == null)
            throw new IllegalStateException("ReConcept feature flags aren't initialized for some reason!");
        return ReConceptFeatureFlags.FLAGS;
    }
}
