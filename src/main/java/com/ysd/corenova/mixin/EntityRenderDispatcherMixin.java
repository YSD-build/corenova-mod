package com.ysd.corenova.mixin;

import com.ysd.corenova.config.ModConfig;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(Entity entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (!ModConfig.get().enableEntityCulling) return;
        
        // 获取相机位置（客户端当前渲染视角）
        net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
        if (client.player == null) return;
        
        Vec3d cameraPos = client.gameRenderer.getCamera().getPos();
        double distanceSq = entity.getPos().squaredDistanceTo(cameraPos);
        int cullingDist = ModConfig.get().entityCullingDistance;
        if (distanceSq > cullingDist * cullingDist * 256) { // 区块距离转方块距离
            ci.cancel();
            if (ModConfig.get().verboseLogging) {
                CoreNovaMod.LOGGER.debug("Entity culled: {}", entity.getDisplayName().getString());
            }
        }
    }
}
