package com.mcmoddev.htwtweaks.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;

public class WallStoneTorchBlock extends WallTorchBlock {
	public WallStoneTorchBlock() {
		super(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((state) -> {
			return 14;
		}).sound(SoundType.WOOD).lootFrom(ModBlocks.STONE_TORCH), ParticleTypes.FLAME);
		setRegistryName(new ResourceLocation("htwtweaks", "wall_torch_stone" ));
	}
}
