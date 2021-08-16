package com.mcmoddev.htwtweaks.interfaces;

import com.mcmoddev.htwtweaks.transport.containers.LaserTransportContainer;
import net.minecraftforge.items.IItemHandlerModifiable;

/*
 * Many thanks to Giga for this...
 */
public interface InventoryProvider {
	void addWeakListener(LaserTransportContainer listener);

	IItemHandlerModifiable getInventory();

	default boolean isDummy() {
		return false;
	}
}
