package moe.denery.reconcept.client;

import moe.denery.reconcept.ReConcept;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * For Future. Shader use generally breaks compatibility with sodium.
 */
public class ReConceptShaders {
    public static final ResourceLocation RECONCEPT_TRANSLUCENT_SHADER = ReConcept.createReConcept("reconcept_translucent");

    @Nullable
    private static ShaderInstance reconceptTranslucentShader = null;

    public static ShaderInstance reconceptTranslucentShader() {
        if (ReConceptShaders.reconceptTranslucentShader == null)
            throw new IllegalStateException("Wrong initialization point for calling shaders!");
        return ReConceptShaders.reconceptTranslucentShader;
    }

    public static void setReconceptTranslucentShader(@Nullable ShaderInstance reconceptTranslucent) {
        ReConceptShaders.reconceptTranslucentShader = reconceptTranslucent;
    }
}
