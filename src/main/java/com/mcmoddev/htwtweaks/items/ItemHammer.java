package com.mcmoddev.htwtweaks.items;

import com.mcmoddev.htwtweaks.data.HammerRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemHammer extends ToolItem {
	private final int speed;

	public ItemHammer(String name, IItemTier tier, int attackDamageIn, float attackSpeedIn, int miningSpeed, Properties builder) {
		super(attackDamageIn, attackSpeedIn, tier, Collections.emptySet(), builder.addToolType(ToolType.PICKAXE, tier.getHarvestLevel()));
		this.speed = miningSpeed;
		setRegistryName("htwtweaks", name);
	}

	@Override
	public float getDestroySpeed(@Nonnull final ItemStack stack, @Nonnull final BlockState state) {
		if (this.canHarvestBlockInternal(state)) {
			return this.speed * getTier().getEfficiency();
		}

		return super.getDestroySpeed(stack, state);
	}

	@Override
	public boolean canHarvestBlock(@Nonnull final BlockState blockIn) {
		return false;
	}

	private boolean canHarvestBlockInternal(@Nonnull final BlockState blockIn) {
		// classically we check a variety of conditions, including the harvest-tool type of the block
		ToolType harvest = blockIn.getHarvestTool();
		if (harvest == ToolType.PICKAXE) {  // hammer acts like a pickaxe
			return this.getTier().getHarvestLevel() >= blockIn.getHarvestLevel();
		} else if (harvest == ToolType.SHOVEL) { // a shovel, if tier 0 or lower
			return blockIn.getHarvestLevel() <= 0;
		}
		return blockIn.getHarvestLevel() == -1; // doesn't need tools? Yes, we can mine that...
	}

	@Override
	public boolean onBlockDestroyed(@Nonnull final ItemStack stack, @Nonnull final World worldIn, @Nonnull final BlockState state, @Nonnull final BlockPos pos, @Nonnull final LivingEntity entityLiving) {
		if (!(entityLiving instanceof PlayerEntity)) return false;

		/*
		 * At the moment this is broken - I need to find a way to shut down the default block drop when the hammer is used.
		 */
		/*
		if (this.canHarvestBlockInternal(state)) {
			HighTechWolvesTweaks.LOGGER.info("harvesting");
			Inventory inv = new Inventory(1);
			inv.setInventorySlotContents(0,state.getBlock().asItem().getDefaultInstance());
			Optional<HammerRecipe> rec = worldIn.getRecipeManager().getRecipe(HammerRecipe.CRUSHING, inv, worldIn);
			HighTechWolvesTweaks.LOGGER.info("recipe found? {} ({})", rec.isPresent(), rec.isPresent()?rec.get().getRegistryName().toString():"null");

			// we got a recipe ?
			if (rec.isPresent()) {
				HammerRecipe recipe = rec.get();
				ServerWorld test = (ServerWorld) worldIn;
				ItemStack recRes = recipe.getRecipeOutput().copy();
				ItemEntity res = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, recRes);
				HighTechWolvesTweaks.LOGGER.info("Spawning ItemEntity for recipe {} result of {} -- {}", recipe.getRegistryName().toString(), recRes.toString(), res.toString());
				test.addEntity(res);
				return true;
			}
		} else {
			HighTechWolvesTweaks.LOGGER.info("Hardness check failed ? {}", this.canHarvestBlockInternal(state));
		}
        */
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}

	@Override
	public ActionResultType onItemUse(@Nonnull final ItemUseContext context) {
		if (context.getWorld().isRemote) return ActionResultType.PASS;
		if (context.getFace() != Direction.UP) return ActionResultType.PASS;

		final BlockPos coord = context.getPos();
		final World world = context.getWorld();

		final AxisAlignedBB boundingBox = new AxisAlignedBB(coord.getX()-1, coord.getY(),
			coord.getZ()-1, coord.getX() + 1, coord.getY() + 2, coord.getZ() + 1);
		final List<ItemEntity> entities = world.getEntitiesWithinAABB(ItemEntity.class, boundingBox)
			.stream()
			.filter(elem -> elem instanceof ItemEntity)
			.filter(elem -> {
				Inventory inv = new Inventory(1);
				inv.setInventorySlotContents(0, elem.getItem());
				return !world.getRecipeManager().getRecipes(HammerRecipe.CRUSHING, inv, world).isEmpty();
			})
			.collect(Collectors.toList());

		if (entities.isEmpty()) return ActionResultType.PASS;

		ItemStack targetStack = entities.get(0).getItem();
		ItemEntity target = entities.get(0);
		Inventory inv = new Inventory(1);
		inv.setInventorySlotContents(0,targetStack);
		HammerRecipe recipe = world.getRecipeManager().getRecipe(HammerRecipe.CRUSHING, inv, world).orElse(null);

		// recipe should not be null, but might be anyway - paranoia is the rule, not the exception
		if (recipe == null || !hardnessCheck(targetStack)) return ActionResultType.PASS;

		double pX = target.getPosX();
		double pY = target.getPosY();
		double pZ = target.getPosZ();

		ItemStack theTool = context.getItem();
		int maxd = getMaxDamage(theTool);
		int curd = theTool.getDamage();
		int dam = curd < maxd ? curd : maxd;
		if (dam > 64) dam = 64;
		if (targetStack.getCount() < dam) dam = targetStack.getCount();

		targetStack.shrink(dam);
		theTool.damageItem(dam, context.getPlayer(), null);

		ItemStack result = recipe.getResult();
		result.setCount(dam * result.getCount());
		ItemEntity rr = new ItemEntity(world, pX, pY, pZ, result.copy());

		ServerWorld w = (ServerWorld) world;

		w.addEntity(rr);
		if (targetStack.getCount() <= 0) w.removeEntity(target);


		return ActionResultType.SUCCESS;
	}

	private boolean hardnessCheck(final ItemStack targetItemStack) {
		final Item targetItem = targetItemStack.getItem();
		if (targetItem instanceof BlockItem) {
			final Block b = ((BlockItem) targetItem).getBlock();
			if (this.canHarvestBlockInternal(b.getDefaultState())) {
				return true;
			}
		}
		return false;
	}

}
