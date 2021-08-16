package com.mcmoddev.htwtweaks.items.properties;

import com.mcmoddev.htwtweaks.items.ItemWrench;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class TweakItemProperties {
	public static float positionStored(ItemStack stackIn, ClientWorld worldIn, LivingEntity entityIn) {
		if (!(stackIn.getItem() instanceof ItemWrench))	return 0.0F;
		return ((ItemWrench)stackIn.getItem()).hasPosition(stackIn)?1.0F:0.0F;
	}
}
