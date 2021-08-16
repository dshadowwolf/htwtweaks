package com.mcmoddev.htwtweaks.transport.screens;

import com.mcmoddev.htwtweaks.HighTechWolvesTweaks;
import com.mcmoddev.htwtweaks.transport.containers.LaserTransportContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.client.gui.GuiUtils;

import javax.annotation.Nonnull;

public class LaserTransportScreen extends ContainerScreen<LaserTransportContainer> {
	private static final ResourceLocation BACKGROUND = new ResourceLocation("htwtweaks", "textures/gui/background.png");
	private final ITextComponent title;
	private final LaserTransportContainer container;

	public LaserTransportScreen(LaserTransportContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.title = titleIn;
		xSize = 176;
		ySize = 176;
		this.container = screenContainer;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(@Nonnull MatrixStack matrixStack, int x, int y) {
		//super.drawGuiContainerForegroundLayer(matrixStack, x, y);
		Style p = this.title.getStyle();
		p.setBold(false).setColor(Color.fromInt(0x00202020)).setItalic(false);
		TranslationTextComponent zz = (TranslationTextComponent) this.title;
		zz.setStyle(p);
		this.font.drawText(matrixStack, zz, 2, 2, 0x00000000);
		this.font.drawString(matrixStack, "Inventory", 7, 92-this.font.FONT_HEIGHT, 0x00000000);
		int wid = font.getStringWidth("Upgrades");
		this.font.drawString(matrixStack, "Upgrades", 132-(wid/2), 67, 0x00000000);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
		this.renderBackground(matrixStack);
		this.minecraft.getTextureManager().bindTexture(BACKGROUND);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.blit(matrixStack, i, j, 0, 0f, 0f, this.xSize, this.xSize, 176, 176);
	}
}
