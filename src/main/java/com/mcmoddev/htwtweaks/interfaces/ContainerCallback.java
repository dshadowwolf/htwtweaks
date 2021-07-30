package com.mcmoddev.htwtweaks.interfaces;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IWorldPosCallable;

public interface ContainerCallback {
	public Container get(int id, PlayerInventory inv, IWorldPosCallable position);
}
