package com.mcmoddev.htwtweaks.interfaces;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

public interface RotationGetter {
	VoxelShape get(Direction facing);
}
