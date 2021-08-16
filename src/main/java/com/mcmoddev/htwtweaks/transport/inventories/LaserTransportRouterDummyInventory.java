package com.mcmoddev.htwtweaks.transport.inventories;

import com.mcmoddev.htwtweaks.interfaces.InventoryProvider;
import com.mcmoddev.htwtweaks.transport.containers.LaserTransportContainer;
import com.mcmoddev.htwtweaks.util.ListenableHolder;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class LaserTransportRouterDummyInventory extends ItemStackHandler implements InventoryProvider {
	private final ListenableHolder listenable = new ListenableHolder();

	public LaserTransportRouterDummyInventory() {
		super(4);

	}

	@Override
	protected void onContentsChanged(int slot)
	{
		super.onContentsChanged(slot);
		listenable.doCallbacks();
	}

	@Override
	public void addWeakListener(LaserTransportContainer e)
	{
		listenable.addWeakListener(e);
	}

	@Override
	public IItemHandlerModifiable getInventory()
	{
		return this;
	}

	@Override
	public boolean isDummy()
	{
		return true;
	}
}
