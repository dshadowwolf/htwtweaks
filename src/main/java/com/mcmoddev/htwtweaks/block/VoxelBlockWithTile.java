package com.mcmoddev.htwtweaks.block;

import com.mcmoddev.htwtweaks.interfaces.BlockActivatedCallback;
import com.mcmoddev.htwtweaks.interfaces.ShapeGetter;
import com.mcmoddev.htwtweaks.interfaces.TileEntityGetter;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class VoxelBlockWithTile extends BasicBlockWithTile {
	private final VoxelShape SHAPE;

	public VoxelBlockWithTile(Properties properties, TileEntityGetter getter, BlockActivatedCallback blockActivated, ShapeGetter voxelShape) {
		super(properties, getter, blockActivated);
		SHAPE = voxelShape.get();
	}

	@Deprecated
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return this.SHAPE;
	}
}
