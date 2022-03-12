package com.mcmoddev.htwtweaks.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.WallOrFloorItem;

import com.mcmoddev.htwtweaks.blocks.ModBlocks;

public class ItemStoneTorch extends WallOrFloorItem {
	public ItemStoneTorch() {
		super(ModBlocks.STONE_TORCH, ModBlocks.WALL_TORCH, (new Item.Properties()).group(ItemGroup.DECORATIONS));
	}
}
