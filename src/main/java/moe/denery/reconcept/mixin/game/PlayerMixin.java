package moe.denery.reconcept.mixin.game;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moe.denery.reconcept.block.ofuda.OfudaOfResurrectionBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Player.class)
public class PlayerMixin {

    @WrapOperation(
            method = "createAttributes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;createLivingAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;"
            )
    )
    private static AttributeSupplier.Builder changePlayerAttributes1(Operation<AttributeSupplier.Builder> original) {
        return original.call().add(Attributes.MAX_HEALTH, 6.0);
    }

    @Inject(
            method = "findRespawnPositionAndUseSpawnBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void addOfudaOfResurrection(ServerLevel serverLevel, BlockPos blockPos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<Vec3>> cir) {
        BlockState blockState = serverLevel.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof OfudaOfResurrectionBlock) {
            cir.setReturnValue(RespawnAnchorBlock.findStandUpPosition(EntityType.PLAYER, serverLevel, blockPos));
        }
    }
}
