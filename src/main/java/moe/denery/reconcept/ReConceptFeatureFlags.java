package moe.denery.reconcept;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.flag.FeatureFlag;

public record ReConceptFeatureFlags(FeatureFlag vanillaTurnedOff) {
    private static ReConceptFeatureFlags FLAGS;

    public static final ImmutableList<String> VANILLA_TURNED_OFF_ENTITIES = ImmutableList.of("villager");

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
