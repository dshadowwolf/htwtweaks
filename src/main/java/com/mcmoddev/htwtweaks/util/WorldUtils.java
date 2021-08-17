package com.mcmoddev.htwtweaks.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class WorldUtils {
	public static Vector3d addVecWithRange(Vector3d dir, Vector3d base, double range) {
		double tX = base.getX() + dir.x * range;
		double tY = base.getY() + dir.y * range;
		double tZ = base.getZ() + dir.z * range;
		return new Vector3d(tX, tY, tZ);
	}
	public static RayTraceResult rayTrace(PlayerEntity playerIn, World worldIn, boolean rayTraceFluids) {
		Vector3d pLook = playerIn.getLookVec();
		Vector3d startVec = new Vector3d(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
		Vector3d endVec = addVecWithRange(pLook, startVec, 5.0d);
		RayTraceContext ctxt = new RayTraceContext(startVec, endVec, RayTraceContext.BlockMode.COLLIDER, rayTraceFluids?RayTraceContext.FluidMode.ANY:RayTraceContext.FluidMode.NONE, playerIn);
		return worldIn.rayTraceBlocks(ctxt);
	}

	public static BlockPos vecToPos(Vector3d hitVector) {
		int tX = (int)hitVector.getX();
		int tY = (int)hitVector.getY();
		int tZ = (int)hitVector.getZ();
		return new BlockPos(tX, tY, tZ);
	}
}
