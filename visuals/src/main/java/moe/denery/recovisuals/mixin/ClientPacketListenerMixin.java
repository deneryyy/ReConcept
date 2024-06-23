package moe.denery.recovisuals.mixin;

import moe.denery.recovisuals.gui.DeathFadingScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Shadow private ClientLevel level;

    @ModifyArg(
            method = "handlePlayerCombatKill",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V")
    )
    private @Nullable Screen disableDeathScreen(@Nullable Screen screen) {
        return null;
    }

    @Inject(
            method = "handlePlayerCombatKill",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V")
    )
    private void reconceptDeathScreen(ClientboundPlayerCombatKillPacket clientboundPlayerCombatKillPacket, CallbackInfo ci) {
        if (!this.level.getLevelData().isHardcore()) {
            Minecraft.getInstance().setScreen(new DeathFadingScreen());
        } else {
            Minecraft.getInstance().setScreen(new DeathScreen(clientboundPlayerCombatKillPacket.message(), true));
        }
    }
}
