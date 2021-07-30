package com.mcmoddev.htwtweaks.block;

import com.mcmoddev.htwtweaks.interfaces.BlockActivatedCallback;
import com.mcmoddev.htwtweaks.interfaces.RotationGetter;
import com.mcmoddev.htwtweaks.interfaces.ShapeGetter;
import com.mcmoddev.htwtweaks.interfaces.TileEntityGetter;
import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import java.util.EnumMap;

public class RotatedBlockWithTile extends BasicBlockWithTile {
	private final RotationGetter getRotation;
	public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public RotatedBlockWithTile(Properties properties, TileEntityGetter getter, BlockActivatedCallback blockActivated, ShapeGetter voxelShape, RotationGetter rotateShape) {
		super(properties, getter, blockActivated, voxelShape);
		this.getRotation = rotateShape;
	}

	private final EnumMap<Direction, VoxelShape> cache = new EnumMap<>(Direction.class);

	@Deprecated
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		Direction facing = state.get(FACING);
		return cache.computeIfAbsent(facing, this.getRotation::get);
	}
}
