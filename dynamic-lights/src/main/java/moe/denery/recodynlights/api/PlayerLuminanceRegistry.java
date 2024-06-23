package moe.denery.recodynlights.api;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.function.Function;

public final class PlayerLuminanceRegistry {
    private static final Map<Function<Player, Boolean>, Integer> REGISTRY = new HashMap<>();

    public static void register(Function<Player, Boolean> condition, int luminanceAmount) {
        PlayerLuminanceRegistry.REGISTRY.put(condition, luminanceAmount);
    }

    @ApiStatus.Internal
    public static int getLuminance(Player player) {
        List<Integer> applied = new ArrayList<>();
        for (Function<Player, Boolean> condition : PlayerLuminanceRegistry.REGISTRY.keySet()) {
            if (condition.apply(player))
                applied.add(PlayerLuminanceRegistry.REGISTRY.get(condition));
        }
        try {
            return Collections.max(applied);
        } catch (NoSuchElementException e) {
            return 0;
        }
    }
}
