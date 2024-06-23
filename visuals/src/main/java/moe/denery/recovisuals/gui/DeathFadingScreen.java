package moe.denery.recovisuals.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

public class DeathFadingScreen extends Screen {

    private int opacity = 0;
    private boolean fadingIn = true;

    public DeathFadingScreen() {
        super(Component.translatable("reconcept.deathScreen.title"));
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        guiGraphics.fill(RenderType.gui(), 0, 0, width, height, FastColor.ARGB32.color(0, 0, 0, Mth.clamp(this.opacity, 0, 255)));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.fadingIn && this.opacity < 255) {
            this.opacity += 1;
        } else if (this.opacity == 255) {
            this.fadingIn = false;
        }

        if (!this.fadingIn && this.opacity > 0) {
            this.opacity -= 1;
        } else if (this.opacity == 0) {
            this.fadingIn = true;
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null)
                player.respawn();
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
