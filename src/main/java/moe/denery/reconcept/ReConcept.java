package moe.denery.reconcept;

import moe.denery.reconcept.block.ReConceptBlocks;
import moe.denery.reconcept.entity.ReConceptEntities;
import moe.denery.reconcept.entity.thrown.ThrownItemEntity;
import moe.denery.reconcept.item.ReConceptItems;
import moe.denery.reconcept.levelgen.feature.ReConceptFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ReConcept implements ModInitializer {
    public static final String MOD_ID = "reconcept";


    @Override
    public void onInitialize() {
        ReConceptBlocks.registerBlocks();
        ReConceptItems.registerItems();
        ReConceptEntities.registerEntities();
        ReConceptFeatures.initialize();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(Commands.literal("test_thrown")
                .requires((CommandSourceStack source) -> source.hasPermission(2))
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayer();
                    if (player == null)
                        return 0;

                    ThrownItemEntity thrownItemEntity = new ThrownItemEntity(player.level(), player, player.getMainHandItem());
                    thrownItemEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 10.0F, 1.0F);
                    player.level().addFreshEntity(thrownItemEntity);
                    return 1;
                })));
    }

    public static ResourceLocation createReConcept(String name) {
        return new ResourceLocation(ReConcept.MOD_ID, name);
    }
}
