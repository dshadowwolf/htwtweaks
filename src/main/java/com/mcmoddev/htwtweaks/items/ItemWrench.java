package com.mcmoddev.htwtweaks.items;

import com.mcmoddev.htwtweaks.HighTechWolvesTweaks;
import com.mcmoddev.htwtweaks.transport.tiles.LaserTransportTileEntity;
import com.mcmoddev.htwtweaks.util.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public class ItemWrench extends Item {
	private BlockPos storedPos = null;
	private Direction positionFacing = null;

	public ItemWrench(String name, @Nonnull Item.Properties builder) {
		super(builder.rarity(Rarity.COMMON).maxDamage(-1).setNoRepair().maxStackSize(64));
		setRegistryName("htwtweaks", name);
	}

	// right click on block, I think...
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if (!context.getPlayer().isSneaking()) {
			if (context.getWorld().getTileEntity(context.getPos()) instanceof LaserTransportTileEntity) {
				LaserTransportTileEntity t = (LaserTransportTileEntity) context.getWorld().getTileEntity(context.getPos());
				t.link(context.getPos(), context.getFace());
			}
			return ActionResultType.PASS;
		}

		BlockPos p = context.getPos();
		TileEntity te = context.getWorld().getTileEntity(p);
		Direction d = context.getFace();
		ItemStack item = context.getItem();

		if (te == null) return ActionResultType.FAIL;

		if (te.getCapability(CapabilityEnergy.ENERGY, d).isPresent() ||
			te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, d).isPresent() ||
			te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, d).isPresent()) {
			setPosition(item, p, d);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}

	protected void clearPosition(@Nonnull ItemStack stackIn) {
		CompoundNBT data = stackIn.getTag();
		if (data == null ||
			!data.contains("target")) return;

		data.remove("target");
	}

	protected void setPosition(@Nonnull ItemStack stackIn, @Nonnull BlockPos posIn, @Nonnull Direction directionIn) {
		CompoundNBT data = stackIn.getTag();
		if (data == null) data = new CompoundNBT();

		CompoundNBT dataTag = new CompoundNBT();
		dataTag.putIntArray("targetPosition", new int[] { posIn.getX(), posIn.getY(), posIn.getZ() });
		dataTag.putString("targetFacing", directionIn.toString());
		data.put("target", dataTag);

		stackIn.setTag(data);
	}

	private boolean hasPositionInternal(@Nonnull ItemStack stackIn) {
		CompoundNBT data = stackIn.getChildTag("target");

		HighTechWolvesTweaks.LOGGER.fatal("data: %s", data==null?"null":data.toString());
		if (data == null) return false;

		HighTechWolvesTweaks.LOGGER.fatal("has targetPosition: %s", data.contains("targetPosition"));
		HighTechWolvesTweaks.LOGGER.fatal("has targetFacing: %s", data.contains("targetFacing"));
		if (!data.contains("targetPosition") ||
		    !data.contains("targetFacing")) return false;

		return true;
	}

	public boolean hasPosition(@Nonnull ItemStack stackIn) {
		boolean res = hasPositionInternal(stackIn);
		HighTechWolvesTweaks.LOGGER.fatal("hasPosition: %s", res);
		return res;
	}

	// raw right click in world...
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack item = playerIn.getHeldItem(handIn);
		clearPosition(item);
		return ActionResult.resultPass(item);
	}
}
