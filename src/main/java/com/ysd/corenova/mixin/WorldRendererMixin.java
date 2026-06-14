package com.ysd.corenova.mixin;

import com.ysd.corenova.config.ModConfig;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "shouldRenderChunk", at = @At("RETURN"), cancellable = true)
    private void onShouldRenderChunk(ChunkBuilder.ChunkData chunk, CallbackInfoReturnable<Boolean> cir) {
        if (!ModConfig.get().enableOcclusionCulling) return;
        // 这里可以添加更激进的遮挡判断，暂时保留原版逻辑并确保配置生效
        // 实际优化会在原版基础上利用配置进行
        if (!cir.getReturnValueZ()) {
            cir.setReturnValue(false);
        }
    }
}
