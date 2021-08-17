package com.mcmoddev.htwtweaks.transport.tiles;

import com.google.common.collect.ImmutableList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LaserTransportTileEntity extends TileEntity {
	@ObjectHolder("htwtweaks:laser_transport_node_tet")
	public static TileEntityType<LaserTransportTileEntity> TYPE;

	private final List<Pair<BlockPos, Direction>> links = new LinkedList<Pair<BlockPos, Direction>>();

	public LaserTransportTileEntity() {
		this(TYPE);
	}

	public LaserTransportTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public void link(BlockPos posIn, Direction facingIn) {
		Pair p = Pair.of(posIn, facingIn);
		if (links.contains(p)) {
			links.remove(p);
		} else {
			links.add(p);
		}
	}

	public ImmutableList<ImmutablePair<BlockPos, Direction>> getLinks() {
		return ImmutableList.copyOf(links.stream().map(p -> ImmutablePair.of(p.getLeft(), p.getRight())).collect(Collectors.toList()));
	}
}
