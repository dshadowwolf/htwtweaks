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

public class BasicBlockWithGUI extends BasicBlockWithTile {
	private ContainerCallback getContainer;

	public BasicBlockWithGUI(Properties properties, TileEntityGetter getter, BlockActivatedCallback blockActivated, ShapeGetter shapeGetter, ContainerCallback getGuiContainer) {
		super(properties, getter, blockActivated, shapeGetter);
		this.getContainer = getGuiContainer;
	}
	public BasicBlockWithGUI(Properties properties, TileEntityGetter getter, BlockActivatedCallback blockActivated, ContainerCallback getGuiContainer) {
		this(properties, getter, blockActivated, null, getGuiContainer);
	}

	@Deprecated
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (worldIn.isRemote)
			return ActionResultType.SUCCESS;

		player.openContainer(new SimpleNamedContainerProvider(
			(id, playerInv, p) -> this.getContainer.get(id, playerInv, IWorldPosCallable.of(worldIn, pos)),
			new TranslationTextComponent("container.htwtweaks.base")
        ));

		return ActionResultType.SUCCESS;
	}
}
