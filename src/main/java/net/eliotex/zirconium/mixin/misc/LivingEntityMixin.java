package net.eliotex.zirconium.mixin.misc;

import java.util.HashMap;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.effect.StatusEffect;
import net.minecraft.entity.living.effect.StatusEffectInstance;
import net.minecraft.world.World;

// Credits to BugTorch for this!
@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	LivingEntityMixin(World world) {
		super(world);
	}

	@Shadow
	@Final
	private HashMap<Integer, StatusEffectInstance> ingredientSlot;

	/**
	 * @author jss2a98aj
	 * @reason If the potion array is empty don't waste time checking it.
	 */
	@Overwrite()
	public boolean isPotionActive(int effectID) {
		return ingredientSlot.size() != 0 && ingredientSlot.containsKey(effectID);
	}

	/**
	 * @author jss2a98aj
	 * @reason If the potion array is empty don't waste time checking it.
	 */
	@Overwrite()
	public boolean isPotionActive(StatusEffect effect) {
		return ingredientSlot.size() != 0 && ingredientSlot.containsKey(effect.id);
	}

	/**
	 * @author jss2a98aj
	 * @reason Keeps the datawatcher from being updated when no change in air has occurred.
	 */
	@Override
	public void setAir(int airAmount) {
		if (getAir() != airAmount) {
			super.setAir(airAmount);
		}
	}

}