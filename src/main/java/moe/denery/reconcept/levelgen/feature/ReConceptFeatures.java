package moe.denery.reconcept.levelgen.feature;

import moe.denery.reconcept.ReConcept;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class ReConceptFeatures {
    public static final Feature<RandomPatchConfiguration> FLOWER_JUNGLE_RECONCEPT_FEATURE = register("flower_jungle_reconcept", new RandomPatchFeature(RandomPatchConfiguration.CODEC));

    public static void initialize() {}

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(BuiltInRegistries.FEATURE, ReConcept.createReConcept(name), feature);
    }
}
