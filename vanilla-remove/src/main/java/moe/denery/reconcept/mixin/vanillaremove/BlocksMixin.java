package moe.denery.reconcept.mixin.vanillaremove;

import moe.denery.reconcept.ReConceptFeatureFlags;
import moe.denery.reconcept.ReConceptVanillaRemove;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Blocks.class)
public class BlocksMixin {
    @Inject(
            method = "register(Ljava/lang/String;Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
            at = @At("HEAD")
    )
    private static void modifyBlockProperties(String string, Block block, CallbackInfoReturnable<Block> cir) {
        if (ReConceptVanillaRemove.VANILLA_TURNED_OFF_BLOCKS.contains(string)) {
            ((BlockBehaviourAccessor) block).setRequiredFeatures(FeatureFlags.REGISTRY.subset(ReConceptFeatureFlags.flags().vanillaTurnedOff()));
        }

        if (BlocksMixin.isWood(string)) {
            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                ((BlockStateBaseAccessor) state).setRequiresCorrectToolForDrops(true);
            }
        }
    }

    @Unique
    private static boolean isWood(String id) {
        // We can't use block tags at this point of registry initialization
        return id.contains("_planks") ||
                id.contains("_wood") ||
                id.contains("_log") ||
                id.contains("oak_") ||
                id.contains("spruce_") ||
                id.contains("birch_") ||
                id.contains("jungle_") ||
                id.contains("acacia_") ||
                id.contains("cherry_") ||
                id.contains("dark_oak_") ||
                id.contains("mangrove_") ||
                id.contains("crimson_") ||
                id.contains("warped_");
    }
}
