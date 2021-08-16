package com.mcmoddev.htwtweaks.transport.containers;

import com.mcmoddev.htwtweaks.interfaces.InventoryProvider;
import com.mcmoddev.htwtweaks.transport.inventories.LaserTransportRouterDummyInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LaserTransportContainer extends Container {
	@ObjectHolder("htwtweaks:laser_transport_node_container")
	public static ContainerType<LaserTransportContainer> TYPE;
	private final IWorldPosCallable openedFrom;
	private final World world;

	private static final int NUM_UPGRADES = 4;
	private static final int NUM_INVENTORY = 9 * 3;
	private static final int NUM_HOTBAR = 9;
	private static final int OUTPUTS_START = NUM_UPGRADES;
	private static final int PLAYER_START = NUM_UPGRADES;
	private static final int HOTBAR_START = PLAYER_START + NUM_INVENTORY;
	private static final int PLAYER_END = HOTBAR_START + NUM_HOTBAR;

	private final ItemStack[] inputStacksCache = new ItemStack[]{
		ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY
	};

	private Runnable inventoryUpdateListener = () -> {
	};

	public final IItemHandlerModifiable inputInventory;

	@Nullable
	private final InventoryProvider inventoryProvider;

	public LaserTransportContainer(int windowIdIn, PlayerInventory playerInventoryIn) {
		this(windowIdIn, playerInventoryIn, IWorldPosCallable.DUMMY);
	}

	public LaserTransportContainer(int windowIdIn, PlayerInventory playerInventoryIn, final IWorldPosCallable worldPosCallableIn) {
		this(windowIdIn, playerInventoryIn, worldPosCallableIn, new LaserTransportRouterDummyInventory());
	}

	public LaserTransportContainer(int windowIdIn, PlayerInventory playerInventoryIn, final IWorldPosCallable worldPosCallableIn, InventoryProvider inventoryProvider) {
		super(TYPE, windowIdIn);
		this.openedFrom = worldPosCallableIn;
		this.world = playerInventoryIn.player.world;
		this.inputInventory = inventoryProvider.getInventory();
		this.inventoryProvider = inventoryProvider;
		inventoryProvider.addWeakListener(this);

		this.addSlot(new UpgradeSlot(this.inputInventory, 0, 115, 32));
		this.addSlot(new UpgradeSlot(this.inputInventory, 1, 133, 32));
		this.addSlot(new UpgradeSlot(this.inputInventory, 2, 115, 50));
		this.addSlot(new UpgradeSlot(this.inputInventory, 3, 133, 50));
		bindPlayerInventory(playerInventoryIn);
	}

	@Override
	public boolean canInteractWith(@Nonnull final PlayerEntity playerIn) {
		return true;
	}

	public IWorldPosCallable getOpenLocation() { return openedFrom; }

	public World getWorld() { return world; }

	public void onInventoryChanged()
	{
//		onCraftMatrixChanged(new RecipeWrapper(inputInventory));
//		inventoryUpdateListener.run();
	}

	private void bindPlayerInventory(PlayerInventory playerInventoryIn)
	{
		// top left for main area is 7x93
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 94 + i * 18));
			}
		}

		// top left for the hotbar is 7x150
		for (int k = 0; k < 9; ++k)
		{
			this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 152));
		}
	}

	private static class UpgradeSlot extends SlotItemHandler {

		public UpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
		}

		/**
		 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
		 */
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			ITagCollection<Item> z = TagCollectionManager.getManager().getItemTags();
			return z.getTagByID(new ResourceLocation("htwtweaks", "upgrades")).contains(stack.getItem());
		}

	}
}
