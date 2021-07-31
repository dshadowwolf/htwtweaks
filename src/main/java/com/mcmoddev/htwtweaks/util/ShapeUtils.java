package com.mcmoddev.htwtweaks.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class ShapeUtils {
	public static final VoxelShape rotate(final VoxelShape base, final Direction facing) {
		double x1, x2, y1, y2, z1, z2;
		x1 = base.getBoundingBox().minX;
		x2 = base.getBoundingBox().maxX;
		y1 = base.getBoundingBox().minY;
		y2 = base.getBoundingBox().maxY;
		z1 = base.getBoundingBox().minZ;
		z2 = base.getBoundingBox().maxZ;

		switch (facing)
		{
			case SOUTH:
				return VoxelShapes.create(1 - x2, y1, 1 - z2, 1 - x1, y2, 1 - z1);
			case WEST:
				return VoxelShapes.create(z1, y1, 1 - x2, z2, y2, 1 - x1);
			case EAST:
				return VoxelShapes.create(1 - z2, y1, x1, 1 - z1, y2, x2);
			case UP:
				return VoxelShapes.create(1 - y1, x1, z1, 1 - y2, x2, z2);
			case DOWN:
				return VoxelShapes.create(y1, 1 - x1, z1, y2, 1 - x2, z2);
			case NORTH:
			default:
				return base;
		}
	}
}
