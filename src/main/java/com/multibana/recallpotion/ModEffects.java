package com.multibana.recallpotion;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static StatusEffect RECALL;
    public static StatusEffect WILD_TELEPORTATION;

    public static StatusEffect registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier("recallpotion", name), statusEffect);
    }

    public static void registerEffects() {
        RECALL = registerStatusEffect("recall", new RecallEffect(StatusEffectCategory.NEUTRAL, 3124687));
        WILD_TELEPORTATION = registerStatusEffect("wild_tp", new RandomTPEffect(StatusEffectCategory.NEUTRAL, 28460));
    }
}