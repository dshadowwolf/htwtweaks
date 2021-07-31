package com.mcmoddev.htwtweaks.transport.containers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.*;
import net.minecraftforge.server.command.TextComponentHelper;

public class LaserTransportScreen extends ContainerScreen<LaserTransportContainer> {

	public LaserTransportScreen(LaserTransportContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
		drawString(matrixStack, this.font, "This is a string, can also be a text component instead", x, y, 0xff000000);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

	}
}
