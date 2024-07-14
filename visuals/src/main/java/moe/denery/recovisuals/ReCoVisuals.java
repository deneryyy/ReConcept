package moe.denery.recovisuals;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Mth;

import java.util.concurrent.atomic.AtomicInteger;

public final class ReCoVisuals implements ClientModInitializer {
    public static final float NIGHT_DARKENING_START = -0.05F;

    private static final AtomicInteger nightFogFade = new AtomicInteger(Float.floatToIntBits(0.0F));
    private static volatile float dayCycleValue = 0.0F;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_WORLD_TICK.register(client -> {
            float dayCycleValueAdapted = Mth.clamp(ReCoVisuals.NIGHT_DARKENING_START * 2.0F + 0.5F, 0.0F, 1.0F);
            ReCoVisuals.updateNightFogFadeAtomically(dayCycleValue <= dayCycleValueAdapted, 0.005F);
        });
    }

    private static void updateNightFogFadeAtomically(boolean condition, float amount) {
        if (!condition) {
            amount = -amount;
        }
        for (;;) {
            float prev = Float.intBitsToFloat(nightFogFade.get());
            if (ReCoVisuals.nightFogFade.compareAndSet(Float.floatToIntBits(prev), Float.floatToIntBits(Mth.clamp(prev + amount, 0.0F, 1.0F))))
                break;
        }
    }

    public static float getNightFogFade() {
        return Float.intBitsToFloat(nightFogFade.get());
    }

    public static void updateDayCycleValue(float dayCycleValue) {
        ReCoVisuals.dayCycleValue = dayCycleValue;
    }
}
