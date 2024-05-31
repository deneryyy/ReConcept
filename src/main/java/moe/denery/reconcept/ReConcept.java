package moe.denery.reconcept;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

public class ReConcept implements ModInitializer {
    public static final String MOD_ID = "reconcept";


    @Override
    public void onInitialize() {

    }

    public static ResourceLocation createReConcept(String name) {
        return new ResourceLocation(ReConcept.MOD_ID, name);
    }
}
