package moe.denery.reconcept;

import moe.denery.reconcept.block.ReConceptBlocks;
import moe.denery.reconcept.item.ReConceptItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

public class ReConcept implements ModInitializer {
    public static final String MOD_ID = "reconcept";


    @Override
    public void onInitialize() {
        ReConceptBlocks.registerBlocks();
        ReConceptItems.registerItems();
    }

    public static ResourceLocation createReConcept(String name) {
        return new ResourceLocation(ReConcept.MOD_ID, name);
    }
}
