package com.mcmoddev.htwtweaks.items;

import com.mcmoddev.htwtweaks.transport.tiles.LaserTransportTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
				// TODO: set TE Target
			}
			return ActionResultType.PASS;
		}

		BlockPos p = context.getPos();
		TileEntity te = context.getWorld().getTileEntity(p);
		Direction d = context.getFace();
		ItemStack item = context.getItem();

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

	public boolean hasPosition(@Nonnull ItemStack stackIn) {
		CompoundNBT data = stackIn.getChildTag("target");

		if (data == null) return false;
		if (!data.contains("targetPosition") ||
		    !data.contains("targetFacing")) return false;

		return true;
	}

	// raw right click in world...
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
			clearPosition(playerIn.getHeldItem(handIn));
			return ActionResult.resultPass(playerIn.getHeldItem(handIn));
	}
}
