package moe.denery.recovisuals.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @ModifyVariable(
            method = "setScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/DeathScreen;<init>(Lnet/minecraft/network/chat/Component;Z)V",
                    shift = At.Shift.AFTER
            ),
            argsOnly = true
    )
    private Screen disableDeathScreen(Screen screen) {
        return null;
    }

/*
    @Inject(
            method = "setScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/DeathScreen;<init>(Lnet/minecraft/network/chat/Component;Z)V"
            )
    )
    private void reconceptDeathOverlay(Screen screen, CallbackInfo ci) {
        // this.setOverlay(new ReConceptDeathOverlay());
    }

 */
}
