package com.mcmoddev.htwtweaks.transport;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

interface TileEntityGetter extends FunctionalInterface {
	TileEntity run(BlockState state, IBlockReader world);
}
