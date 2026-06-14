package com.ysd.corenova.mixin;

import com.ysd.corenova.config.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    private int tickCounter = 0;

    @Inject(method = "mobTick", at = @At("HEAD"), cancellable = true)
    private void onMobTick(CallbackInfo ci) {
        if (!ModConfig.get().enableAiThrottling) return;
        
        net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
        if (client.player == null) return;
        
        double distanceToPlayer = this.squaredDistanceTo(client.player);
        int throttleDist = ModConfig.get().aiThrottleDistance;
        int reduction = ModConfig.get().aiTickReduction;
        
        if (distanceToPlayer > throttleDist * throttleDist * 256) {
            tickCounter++;
            if (tickCounter % reduction != 0) {
                ci.cancel();
            }
        } else {
            tickCounter = 0;
        }
    }
}
