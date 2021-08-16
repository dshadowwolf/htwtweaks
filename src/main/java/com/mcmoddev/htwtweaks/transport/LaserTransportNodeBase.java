package com.mcmoddev.htwtweaks.transport;

import com.mcmoddev.htwtweaks.block.RotatedBlockWithGUI;
import com.mcmoddev.htwtweaks.interfaces.*;
import com.mcmoddev.htwtweaks.util.ShapeUtils;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;

public class LaserTransportNodeBase extends RotatedBlockWithGUI {
	private static final VoxelShape baseShape = VoxelShapes.create( 0d,0d,0d,1d,1d,0.4375d );
	public LaserTransportNodeBase(Properties properties, TileEntityGetter getter, BlockActivatedCallback blockActivated, ContainerCallback getContainer) {
		super(properties, getter, blockActivated, () -> baseShape, facing -> ShapeUtils.rotate(baseShape, facing), getContainer, new TranslationTextComponent("container.htwtweaks.base_node"));
	}

}
