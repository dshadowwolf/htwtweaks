package com.mcmoddev.htwtweaks.block;

import com.mcmoddev.htwtweaks.interfaces.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class RotatedBlockWithGUI extends RotatedBlockWithTile {
	private final ContainerCallback container;
	private final ITextComponent titleKey;

	public RotatedBlockWithGUI(Properties properties, TileEntityGetter getter, BlockActivatedCallback blockActivated, ShapeGetter voxelShape, RotationGetter rotateShape, ContainerCallback getContainer, ITextComponent titleKey) {
		super(properties, getter, blockActivated, voxelShape, rotateShape);
		this.container = getContainer;
		this.titleKey = titleKey;
	}

	@Deprecated
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (worldIn.isRemote)
			return ActionResultType.SUCCESS;

		player.openContainer(new SimpleNamedContainerProvider(
			(id, playerInv, p) -> this.container.get(id, playerInv, IWorldPosCallable.of(worldIn, pos)), this.titleKey
		));

		return ActionResultType.SUCCESS;
	}
}
