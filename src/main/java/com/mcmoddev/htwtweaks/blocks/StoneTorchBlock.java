package com.mcmoddev.htwtweaks.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;

public class StoneTorchBlock extends TorchBlock {
	public StoneTorchBlock() {
		super(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((state) -> {
			return 14;
		}).sound(SoundType.WOOD), ParticleTypes.FLAME);
		setRegistryName(new ResourceLocation("htwtweaks", "stone_torch_block"));
	}
}
