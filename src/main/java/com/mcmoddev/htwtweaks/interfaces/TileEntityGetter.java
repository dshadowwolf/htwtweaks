package com.mcmoddev.htwtweaks.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public interface TileEntityGetter {
	TileEntity run(BlockState state, IBlockReader world);
}
