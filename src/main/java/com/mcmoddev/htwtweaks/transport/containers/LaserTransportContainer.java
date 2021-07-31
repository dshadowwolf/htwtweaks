package com.mcmoddev.htwtweaks.transport.containers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

public class LaserTransportContainer extends Container {
	@ObjectHolder("htwtweaks:laser_transport_node_container")
	public static ContainerType<LaserTransportContainer> TYPE;
	private final IWorldPosCallable openedFrom;
	private final World world;

	public LaserTransportContainer(int windowIdIn, PlayerInventory playerInventoryIn) {
		this(windowIdIn, playerInventoryIn, IWorldPosCallable.DUMMY);
	}

	public LaserTransportContainer(int windowIdIn, PlayerInventory playerInventoryIn, final IWorldPosCallable worldPosCallableIn) {
		super(TYPE, windowIdIn);
		this.openedFrom = worldPosCallableIn;
		this.world = playerInventoryIn.player.world;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}
}
