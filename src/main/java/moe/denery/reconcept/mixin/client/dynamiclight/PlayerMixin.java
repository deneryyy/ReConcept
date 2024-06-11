package moe.denery.reconcept.mixin.client.dynamiclight;

import moe.denery.reconcept.dynamiclight.ReConceptDynamicLights;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class PlayerMixin {
    @Shadow private double yLast1;

    @Shadow private double xLast;

    @Shadow private double zLast;

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void tickDynamicLight(CallbackInfo ci) {
        double y = this.yLast1;
        double x = this.xLast;
        double z = this.zLast;
        ReConceptDynamicLights.INSTANCE.setPlayerPos(new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)));
    }
}
