package com.multibana.recallpotion;

import net.fabricmc.api.ModInitializer;

public class RecallPotionMod implements ModInitializer {
	@Override
	public void onInitialize() {
		ModEffects.registerEffects();
		ModPotions.registerPotions();
	}
}
