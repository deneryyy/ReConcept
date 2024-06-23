package moe.denery.reconcept.data.worldgen.feature;

import moe.denery.reconcept.ReConcept;
import moe.denery.reconcept.block.ReConceptBlocks;
import moe.denery.reconcept.levelgen.feature.ReConceptFeatures;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public final class ReConceptConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_JUNGLE_RECONCEPT = ReConceptConfiguredFeatures.createKey("flower_jungle_reconcept");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> registryBootstrap) {
        FeatureUtils.register(
                registryBootstrap,
                ReConceptConfiguredFeatures.FLOWER_JUNGLE_RECONCEPT,
                ReConceptFeatures.FLOWER_JUNGLE_RECONCEPT_FEATURE,
                grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ReConceptBlocks.HIGANBANA.defaultBlockState(), 2)), 64)
        );
    }

    public static void generate(HolderLookup.Provider registries, FabricDynamicRegistryProvider.Entries entries) {
        entries.add(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE), ReConceptConfiguredFeatures.FLOWER_JUNGLE_RECONCEPT);

        entries.add(VegetationFeatures.FLOWER_MEADOW, new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new DualNoiseProvider(new InclusiveRange<Integer>(1, 3), new NormalNoise.NoiseParameters(-10, 1.0, new double[0]), 1.0f, 2345L, new NormalNoise.NoiseParameters(-3, 1.0, new double[0]), 1.0f, List.of(
                Blocks.TALL_GRASS.defaultBlockState(),
                Blocks.ALLIUM.defaultBlockState(),
                Blocks.POPPY.defaultBlockState(),
                Blocks.AZURE_BLUET.defaultBlockState(),
                Blocks.DANDELION.defaultBlockState(),
                Blocks.CORNFLOWER.defaultBlockState(),
                Blocks.OXEYE_DAISY.defaultBlockState(),
                Blocks.SHORT_GRASS.defaultBlockState(),
                ReConceptBlocks.HIGANBANA.defaultBlockState()
        )))))));

        SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; ++i) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                builder.add(Blocks.PINK_PETALS.defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, i).setValue(PinkPetalsBlock.FACING, direction), 1);
            }
        }
        builder.add(ReConceptBlocks.HIGANBANA.defaultBlockState());
        entries.add(VegetationFeatures.FLOWER_CHERRY, new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(builder))))));

        entries.add(VegetationFeatures.FLOWER_PLAIN, new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseThresholdProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0, new double[0]), 0.005f, -0.8f, 0.33333334f, Blocks.DANDELION.defaultBlockState(), List.of(
                Blocks.ORANGE_TULIP.defaultBlockState(),
                Blocks.RED_TULIP.defaultBlockState(),
                Blocks.PINK_TULIP.defaultBlockState(),
                Blocks.WHITE_TULIP.defaultBlockState()
        ), List.of(
                Blocks.POPPY.defaultBlockState(),
                Blocks.AZURE_BLUET.defaultBlockState(),
                Blocks.OXEYE_DAISY.defaultBlockState(),
                Blocks.CORNFLOWER.defaultBlockState(),
                ReConceptBlocks.HIGANBANA.defaultBlockState()
        )))))));

        entries.add(VegetationFeatures.FOREST_FLOWERS, new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC))), new PlacementModifier[0]),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH))), new PlacementModifier[0]),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY))), new PlacementModifier[0]),
                PlacementUtils.inlinePlaced(Feature.NO_BONEMEAL_FLOWER, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILY_OF_THE_VALLEY))), new PlacementModifier[0]),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ReConceptBlocks.HIGANBANA))), new PlacementModifier[0])
        ))));

        entries.add(VegetationFeatures.FLOWER_FLOWER_FOREST, new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0, new double[0]), 0.020833334f, List.of(
                Blocks.DANDELION.defaultBlockState(),
                Blocks.POPPY.defaultBlockState(),
                Blocks.ALLIUM.defaultBlockState(),
                Blocks.AZURE_BLUET.defaultBlockState(),
                Blocks.RED_TULIP.defaultBlockState(),
                Blocks.ORANGE_TULIP.defaultBlockState(),
                Blocks.WHITE_TULIP.defaultBlockState(),
                Blocks.PINK_TULIP.defaultBlockState(),
                Blocks.OXEYE_DAISY.defaultBlockState(),
                Blocks.CORNFLOWER.defaultBlockState(),
                Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
                ReConceptBlocks.HIGANBANA.defaultBlockState()
        )))))));
    }

    private static RandomPatchConfiguration grassPatch(BlockStateProvider blockStateProvider, int i) {
        return FeatureUtils.simpleRandomPatchConfiguration(i, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(blockStateProvider)));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ReConcept.createReConcept(name));
    }
}
