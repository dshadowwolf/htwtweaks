package com.mcmoddev.htwtweaks.transport.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class LaserTransportTileEntity extends TileEntity {
	@ObjectHolder("htwtweaks:laser_transport_node_tet")
	public static TileEntityType<LaserTransportTileEntity> TYPE;

	public LaserTransportTileEntity() {
		this(TYPE);
	}

	public LaserTransportTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
}
