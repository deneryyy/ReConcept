package moe.denery.reconcept;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 *  Contains data of content removed from vanilla minecraft
 */
public final class ReConceptVanillaRemove {
    public static final ImmutableList<String> VANILLA_TURNED_OFF_ENTITIES = ImmutableList.of(
            "villager"
    );
    public static final ImmutableList<String> VANILLA_TURNED_OFF_BLOCKS = ImmutableList.of(
            "redstone_ore",
            "deepslate_redstone_ore"
    );
    public static final ImmutableList<ResourceKey<ConfiguredFeature<?, ?>>> REMOVED_VANILLA_CONFIGURED_FEATURES = ImmutableList.of(
            OreFeatures.ORE_REDSTONE
    );
    public static final ImmutableList<ResourceKey<PlacedFeature>> REMOVED_VANILLA_PLACED_FEATURES = ImmutableList.of(
            OrePlacements.ORE_REDSTONE,
            OrePlacements.ORE_REDSTONE_LOWER
    );
}
