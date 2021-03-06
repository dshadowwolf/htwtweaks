package com.mcmoddev.htwtweaks.data;

import com.mcmoddev.htwtweaks.HighTechWolvesTweaks;
import com.mcmoddev.htwtweaks.blocks.ModBlocks;
import com.mcmoddev.htwtweaks.items.ItemHammer;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

	private static class ItemParameters {
		public final int attackDamage;
		public final float attackSpeed;
		public final int miningSpeed;
		public final int stackSize;
		public final String name;
		public final ItemTier tier;

		public ItemParameters(String n, ItemTier t, int d, float as, int ms, int ss) {
			attackSpeed = as;
			attackDamage = d;
			miningSpeed = ms;
			stackSize = ss;
			name = n;
			tier = t;
		}
	}

	@ObjectHolder("htwtweaks:wooden_hammer")
	public static final Item WOODEN_HAMMER = null;
	@ObjectHolder("htwtweaks:stone_hammer")
	public static final Item STONE_HAMMER = null;
	@ObjectHolder("htwtweaks:iron_hammer")
	public static final Item IRON_HAMMER = null;
	@ObjectHolder("htwtweaks:diamond_hammer")
	public static final Item DIAMOND_HAMMER = null;
	@ObjectHolder("htwtweaks:golden_hammer")
	public static final Item GOLDEN_HAMMER = null;
	@ObjectHolder("htwtweaks:netherite_hammer")
	public static final Item NETHERITE_HAMMER = null;
    @ObjectHolder("htwtweaks:stone_torch")
	public static final Item STONE_TORCH = null;
	@ObjectHolder("htwtweaks:stone_rod")
	public static final Item STONE_ROD = null;

	private static final ItemParameters[] items = new ItemParameters[] {
		new ItemParameters("wooden_hammer", ItemTier.WOOD, 6, -3.2f, 2, 59),
		new ItemParameters("stone_hammer", ItemTier.STONE, 7, -3.2f, 4, 131 ),
		new ItemParameters("iron_hammer", ItemTier.IRON, 6, -3.1f, 6, 250 ),
		new ItemParameters("diamond_hammer", ItemTier.DIAMOND, 5, -3.0f, 8, 1561 ),
		new ItemParameters("golden_hammer", ItemTier.GOLD, 6, -3.0f, 6, 32 ),
		new ItemParameters("netherite_hammer", ItemTier.NETHERITE, 5, -3.0f, 8, 2031 )
	};

	public ModItems() {
		// blank because it seems like we need a default constructor...
	}

	@SubscribeEvent
	public static final void register(final RegistryEvent.Register<Item> itemRegistryEvent) {
		IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
		HighTechWolvesTweaks.LOGGER.fatal("Registering Items");
		for (ItemParameters parameters : items) {
			ItemHammer thisHammer = new ItemHammer(parameters.name, parameters.tier, parameters.attackDamage, parameters.attackSpeed, parameters.miningSpeed, new Item.Properties().group(ItemGroup.TOOLS).maxDamage(parameters.stackSize));
			registry.register(thisHammer);
		}
		HighTechWolvesTweaks.LOGGER.fatal("Hammers Registered, Trying for the Torch...");
		HighTechWolvesTweaks.LOGGER.fatal("Torch Block - {} - Wall Block - {}", ModBlocks.STONE_TORCH, ModBlocks.WALL_TORCH);

		registry.register(new WallOrFloorItem(ModBlocks.STONE_TORCH, ModBlocks.WALL_TORCH, (new Item.Properties()).group(ItemGroup.DECORATIONS))
			.setRegistryName(new ResourceLocation("htwtweaks", "stone_torch")));
		HighTechWolvesTweaks.LOGGER.fatal("Torch Registered, trying for the Stone Rod");
		registry.register( new Item(new Item.Properties()
			                                .group(ItemGroup.MISC).isImmuneToFire().rarity(Rarity.COMMON))
			                       .setRegistryName(new ResourceLocation("htwtweaks", "stone_rod")) );
	}
}
