package com.mcmoddev.htwtweaks.blocks;

import net.minecraft.block.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {
	@ObjectHolder("htwtweaks:stone_torch_block")
    public static final Block STONE_TORCH = null;
	@ObjectHolder("htwtweaks:stone_torch_wall_block")
	public static final Block WALL_TORCH = null;

	public ModBlocks() {
		// blank constructor
	}

	@SubscribeEvent
	public static final void register(final RegistryEvent.Register<Block> blockRegistryEvent) {
		IForgeRegistry<Block> registry = blockRegistryEvent.getRegistry();
		registry.register(new StoneTorchBlock());
		registry.register(new WallStoneTorchBlock());
	}
}
