package moe.denery.reconcept.data.worldgen.biome;

import moe.denery.reconcept.data.worldgen.feature.ReConceptPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.Nullable;

public final class ReConceptBiomes {
    public static void bootstrap(BootstrapContext<Biome> registryBootstrap) {
        HolderGetter<PlacedFeature> placedFeatures = registryBootstrap.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = registryBootstrap.lookup(Registries.CONFIGURED_CARVER);

        // registryBootstrap.register(Biomes.JUNGLE, ReConceptBiomes.jungle(placedFeatures, configuredCarvers));
    }

    public static void generate(HolderLookup.Provider registries, FabricDynamicRegistryProvider.Entries entries) {
        entries.add(Biomes.JUNGLE, jungle(entries.placedFeatures(), entries.configuredCarvers()));
    }

    public static Biome jungle(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.baseJungleSpawns(builder);
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 40, 1, 2)).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 2, 1, 3)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PANDA, 1, 1, 2));
        return baseJungle(placedFeatures, carvers, 0.9f, false, false, true, builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE));
    }

    private static Biome baseJungle(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers, float f, boolean bl, boolean bl2, boolean bl3, MobSpawnSettings.Builder builder, Music music) {
        BiomeGenerationSettings.Builder generationSettingsBuiler = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        globalOverworldGeneration(generationSettingsBuiler);
        BiomeDefaultFeatures.addDefaultOres(generationSettingsBuiler);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettingsBuiler);
        if (bl) {
            BiomeDefaultFeatures.addBambooVegetation(generationSettingsBuiler);
        } else {
            if (bl3) {
                BiomeDefaultFeatures.addLightBambooVegetation(generationSettingsBuiler);
            }
            if (bl2) {
                BiomeDefaultFeatures.addSparseJungleTrees(generationSettingsBuiler);
            } else {
                BiomeDefaultFeatures.addJungleTrees(generationSettingsBuiler);
            }
        }
        BiomeDefaultFeatures.addWarmFlowers(generationSettingsBuiler);
        BiomeDefaultFeatures.addJungleGrass(generationSettingsBuiler);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettingsBuiler);
        // Adding higanbanas here
        generationSettingsBuiler.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReConceptPlacedFeatures.FLOWER_JUNGLE_RECONCEPT);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generationSettingsBuiler);
        BiomeDefaultFeatures.addJungleVines(generationSettingsBuiler);
        if (bl2) {
            BiomeDefaultFeatures.addSparseJungleMelons(generationSettingsBuiler);
        } else {
            BiomeDefaultFeatures.addJungleMelons(generationSettingsBuiler);
        }
        return biome(true, 0.95f, f, builder, generationSettingsBuiler, music);
    }

    private static Biome biome(boolean bl, float f, float g, MobSpawnSettings.Builder builder, BiomeGenerationSettings.Builder builder2, @Nullable Music music) {
        return biome(bl, f, g, 4159204, 329011, null, null, builder, builder2, music);
    }

    private static Biome biome(boolean bl, float f, float g, int i, int j, @Nullable Integer integer, @Nullable Integer integer2, MobSpawnSettings.Builder builder, BiomeGenerationSettings.Builder builder2, @Nullable Music music) {
        BiomeSpecialEffects.Builder builder3 = new BiomeSpecialEffects.Builder().waterColor(i).waterFogColor(j).fogColor(12638463).skyColor(OverworldBiomes.calculateSkyColor(f)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music);
        if (integer != null) {
            builder3.grassColorOverride(integer);
        }
        if (integer2 != null) {
            builder3.foliageColorOverride(integer2);
        }
        return new Biome.BiomeBuilder().hasPrecipitation(bl).temperature(f).downfall(g).specialEffects(builder3.build()).mobSpawnSettings(builder.build()).generationSettings(builder2.build()).build();
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }
}
