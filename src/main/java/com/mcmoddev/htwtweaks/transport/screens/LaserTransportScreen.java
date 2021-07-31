package com.mcmoddev.htwtweaks.transport.screens;

import com.mcmoddev.htwtweaks.transport.containers.LaserTransportContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.*;

import javax.annotation.Nonnull;

public class LaserTransportScreen extends ContainerScreen<LaserTransportContainer> {

	public LaserTransportScreen(LaserTransportContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(@Nonnull MatrixStack matrixStack, int x, int y) {
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
		drawString(matrixStack, this.font, "This is a string, can also be a text component instead", this.guiLeft+20, this.guiTop+20, 0xff000000);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
		this.renderBackground(matrixStack);
		this.minecraft.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
	}
}
