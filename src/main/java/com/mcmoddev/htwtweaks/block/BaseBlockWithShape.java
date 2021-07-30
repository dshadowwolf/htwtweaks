package com.mcmoddev.htwtweaks.block;

import com.mcmoddev.htwtweaks.interfaces.ShapeGetter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BaseBlockWithShape extends Block {
	private VoxelShape SHAPE = null;
	private final ShapeGetter shape;

	public BaseBlockWithShape(Properties properties, ShapeGetter shapeFunction) {
		super(properties);
		this.shape = shapeFunction;
	}

	@Deprecated
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		if (this.SHAPE == null && this.shape != null) this.SHAPE = this.shape.get();
		return this.SHAPE==null?super.getShape(state, worldIn, pos, context):this.SHAPE;
	}
}
