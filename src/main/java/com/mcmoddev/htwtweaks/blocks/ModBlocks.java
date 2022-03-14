package com.mcmoddev.htwtweaks.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
	@ObjectHolder("htwtweaks:stone_torch")
    public static final Block STONE_TORCH = null;
	@ObjectHolder("htwtweaks:wall_torch_stone")
	public static final Block WALL_TORCH = null;

	public ModBlocks() {
		// blank constructor
	}

	@SubscribeEvent
	public static final void register(final RegistryEvent.Register<Block> blockRegistryEvent) {
		IForgeRegistry<Block> registry = blockRegistryEvent.getRegistry();
		registry.register(new TorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((state) -> {
			return 14;
		}).sound(SoundType.WOOD), ParticleTypes.FLAME)
			.setRegistryName(new ResourceLocation("htwtweaks", "stone_torch")));
		registry.register(new WallTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((state) -> {
			return 14;
		}).sound(SoundType.WOOD).lootFrom(ModBlocks.STONE_TORCH), ParticleTypes.FLAME)
			.setRegistryName(new ResourceLocation("htwtweaks", "wall_torch_stone" )));
	}
}
