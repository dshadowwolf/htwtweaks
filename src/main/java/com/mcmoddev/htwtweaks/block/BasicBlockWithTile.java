package com.mcmoddev.htwtweaks.block;

import com.mcmoddev.htwtweaks.interfaces.BlockActivatedCallback;
import com.mcmoddev.htwtweaks.interfaces.ShapeGetter;
import com.mcmoddev.htwtweaks.interfaces.TileEntityGetter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BasicBlockWithTile extends BaseBlockWithShape {
	private final TileEntityGetter makeTile;
	private final BlockActivatedCallback activated;

	public BasicBlockWithTile(Properties properties, TileEntityGetter getter, BlockActivatedCallback blockActivated, ShapeGetter shapeGetter) {
		super(properties, shapeGetter);
		this.makeTile = getter;
		this.activated = blockActivated;
	}

	public BasicBlockWithTile(Properties properties, TileEntityGetter getter, BlockActivatedCallback blockActivated) {
		this(properties, getter, blockActivated, null);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return this.makeTile.run(state, world);
	}

	@Deprecated
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
		return this.activated.run(state, worldIn, pos, player, hand, blockRayTraceResult);
	}
}
