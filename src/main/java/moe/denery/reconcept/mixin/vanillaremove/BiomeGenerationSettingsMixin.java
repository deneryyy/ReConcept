package moe.denery.reconcept.mixin;

import moe.denery.reconcept.ReConceptVanillaRemove;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(BiomeGenerationSettings.class)
public class BiomeGenerationSettingsMixin {
    @ModifyVariable(
            method = "<init>",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private static List<HolderSet<PlacedFeature>> fixPlacedFeatureBiomeReference(List<HolderSet<PlacedFeature>> value) {
        List<HolderSet<PlacedFeature>> list = value.stream().map(holderSet -> {
            return (HolderSet<PlacedFeature>) HolderSet.direct(holderSet.stream().filter(featureHolder -> {
                return featureHolder.unwrapKey()
                        .map(key -> !ReConceptVanillaRemove.REMOVED_VANILLA_PLACED_FEATURES.contains(key))
                        .orElse(false);
            }).toList());
        }).toList();
        return list;
    }
}
