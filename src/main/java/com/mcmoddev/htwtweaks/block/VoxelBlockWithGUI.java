package com.mcmoddev.htwtweaks.block;

import com.mcmoddev.htwtweaks.interfaces.BlockActivatedCallback;
import com.mcmoddev.htwtweaks.interfaces.ContainerCallback;
import com.mcmoddev.htwtweaks.interfaces.ShapeGetter;
import com.mcmoddev.htwtweaks.interfaces.TileEntityGetter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class VoxelBlockWithGUI extends VoxelBlockWithTile {
	private final ContainerCallback container;

	public VoxelBlockWithGUI(Properties properties, TileEntityGetter getter, BlockActivatedCallback blockActivated, ShapeGetter voxelShape, ContainerCallback getContainer) {
		super(properties, getter, blockActivated, voxelShape);
		container = getContainer;
	}

	@Deprecated
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (worldIn.isRemote)
			return ActionResultType.SUCCESS;

		player.openContainer(new SimpleNamedContainerProvider(
			(id, playerInv, p) -> this.container.get(id, playerInv, IWorldPosCallable.of(worldIn, pos)),
			new TranslationTextComponent("container.htwtweaks.base_voxel")
		));

		return ActionResultType.SUCCESS;
	}
}
