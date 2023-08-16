package com.multibana.recallpotion;

import com.multibana.recallpotion.mixins.BrewingRecipeRegistryMixin;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions {
    public static Potion RECALL_POTION;
	public static Potion ENDER_POTION;

	public static Potion registerPotion(String name, StatusEffectInstance statusEffectInstance, Identifier identifier) {
		return Registry.register(Registries.POTION, identifier,
				new Potion(statusEffectInstance));
	}
	public static void registerPotions() {
		RECALL_POTION = registerPotion("recall_potion", new StatusEffectInstance(ModEffects.RECALL, 20 * 8, 0), new Identifier("recallpotion", "recall"));
		ENDER_POTION = registerPotion("ender_potion", new StatusEffectInstance(ModEffects.WILD_TELEPORTATION, 20 * 6, 0), new Identifier("recallpotion", "wild_tp"));
		registerPotionRecipes();
	}

	private static void registerPotionRecipes(){
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.AWKWARD, Items.ENDER_EYE, ModPotions.ENDER_POTION);
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(ModPotions.ENDER_POTION, Items.DIAMOND, ModPotions.RECALL_POTION);
	}
}
