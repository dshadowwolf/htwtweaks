package com.mcmoddev.htwtweaks.data;

import com.mcmoddev.htwtweaks.HighTechWolvesTweaks;
import com.mcmoddev.htwtweaks.items.ItemHammer;
import net.minecraft.item.*;
import net.minecraftforge.common.ToolType;
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

	private static final ItemParameters[] items = new ItemParameters[] { new ItemParameters("wooden_hammer", ItemTier.WOOD, 6, -3.2f, 2, 59),
		new ItemParameters("stone_hammer", ItemTier.STONE, 7, -3.2f, 4, 131 ), new ItemParameters("iron_hammer", ItemTier.IRON, 6, -3.1f, 6, 250 ),
		new ItemParameters("diamond_hammer", ItemTier.DIAMOND, 5, -3.0f, 8, 1561 ), new ItemParameters("golden_hammer", ItemTier.GOLD, 6, -3.0f, 6, 32 ),
		new ItemParameters("netherite_hammer", ItemTier.NETHERITE, 5, -3.0f, 8, 2031 )
	};

	public ModItems() {
		// blank because it seems like we need a default constructor...
	}

	@SubscribeEvent
	public static final void register(final RegistryEvent.Register<Item> itemRegistryEvent) {
		IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
		for (ItemParameters parameters : items) {
			ItemHammer thisHammer = new ItemHammer(parameters.name, parameters.tier, parameters.attackDamage, parameters.attackSpeed, parameters.miningSpeed, new Item.Properties().group(ItemGroup.TOOLS).maxDamage(parameters.stackSize));
			registry.register(thisHammer);
		}
		registry.register(new BlockItem(HighTechWolvesTweaks.TRANSPORT_NODE, new Item.Properties().addToolType(ToolType.PICKAXE, 1).rarity(Rarity.EPIC)).setRegistryName("laser_transport_node"));
	}
}
