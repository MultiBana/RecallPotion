package com.multibana.recallpotion;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

public class RecallEffect extends StatusEffect {
    public RecallEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }
    void teleportTargetToPlayerSpawn(LivingEntity target, ServerPlayerEntity player){
        BlockPos spawn = player.getSpawnPointPosition();
        ServerWorld serverWorld = (ServerWorld) player.world;
        RegistryKey<World> spawnDimension = player.getSpawnPointDimension();
        ServerWorld destination = ((ServerWorld) player.world).getServer().getWorld(spawnDimension);

        // If recalling in a dimension different from the player's spawn dimension, or null for some reason, fail.
        if (destination == null || !(spawnDimension.equals(serverWorld.getRegistryKey()))) {
            Vec3d pos = target.getPos();
            target.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.PLAYERS, 1f, 1f);
            return;
        }

        if (spawn == null) {
            spawn = player.world.getSpawnPos();
        }
        Optional<Vec3d> a = PlayerEntity.findRespawnPosition(destination, spawn, 0, true, true);
        if(a.isPresent()){
            BlockState blockState = destination.getBlockState(spawn);
            if(blockState.isIn(BlockTags.BEDS)){
                spawn = new BlockPos(a.get());
            }
            else{
                Optional<Vec3d> b = PlayerEntity.findRespawnPosition(destination, player.world.getSpawnPos(), 0, true, true);
                spawn = b.map(BlockPos::new).orElseGet(() -> player.world.getSpawnPos());
                if (target.isPlayer()){
                    ServerPlayerEntity tPlayer = (ServerPlayerEntity) target;
                    if(tPlayer.equals(player)){
                        player.setSpawnPoint(serverWorld.getRegistryKey(), spawn, 0, true, false);
                    }
                }
            }
        }

        target.stopRiding();
        player.fallDistance = 0;
        target.teleport(spawn.getX() + 0.5F,spawn.getY()+0.6F,spawn.getZ()+ 0.5F);
        target.world.playSound(null, spawn.getX() + 0.5F, spawn.getY()+0.6F, spawn.getZ() + 0.5F, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
    }

    @Override
    public void applyUpdateEffect(LivingEntity pLivingEntity, int pAmplifier){
        if (pLivingEntity.world.isClient || !pLivingEntity.isPlayer()) {
            return;
        }
        ServerPlayerEntity player = (ServerPlayerEntity) pLivingEntity;
        teleportTargetToPlayerSpawn(pLivingEntity, player);
    }
    @Override
    public boolean canApplyUpdateEffect(int pDuration, int pAmplifier){
        return pDuration == 1;
    }
}
