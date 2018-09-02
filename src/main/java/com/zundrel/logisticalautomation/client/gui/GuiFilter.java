package com.zundrel.logisticalautomation.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityFilter;
import com.zundrel.logisticalautomation.common.containers.ContainerFilter;
import com.zundrel.logisticalautomation.common.info.ModInfo;
import com.zundrel.logisticalautomation.common.network.MessageButton;
import com.zundrel.logisticalautomation.common.utilities.Filter;

public class GuiFilter extends GuiContainer {

	private static boolean showConfig = false;
	private TileEntityFilter tile;

	public GuiFilter(ContainerFilter inventorySlotsIn) {
		super(inventorySlotsIn);
		xSize = 176;
		ySize = 196;
		tile = ((ContainerFilter) inventorySlots).tile;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (showConfig) {
			this.mc.getTextureManager().bindTexture(new ResourceLocation(ModInfo.MOD_ID, "textures/gui/filter_cutout.png"));
		} else {
			this.mc.getTextureManager().bindTexture(new ResourceLocation(ModInfo.MOD_ID, "textures/gui/filter.png"));
		}
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (showConfig) {
			this.mc.getTextureManager().bindTexture(new ResourceLocation(ModInfo.MOD_ID, "textures/gui/filter_settings.png"));
			this.drawTexturedModalRect(guiLeft - 167, guiTop, 0, 0, 168, 100);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		fontRenderer.drawString("Left", 8, 6, 4210752);
		fontRenderer.drawString("Back", 8, 38, 4210752);
		fontRenderer.drawString("Right", 8, 70, 4210752);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButtonExt(1000, guiLeft - 162, guiTop + 20, 30, 12, ""));
		buttonList.add(new GuiButtonExt(1001, guiLeft - 129, guiTop + 20, 30, 12, ""));
		buttonList.add(new GuiButtonExt(1002, guiLeft - 96, guiTop + 20, 46, 12, ""));
		buttonList.add(new GuiButtonExt(1003, guiLeft - 47, guiTop + 20, 47, 12, ""));
		buttonList.add(new GuiButtonExt(2000, guiLeft - 162, guiTop + 52, 30, 12, ""));
		buttonList.add(new GuiButtonExt(2001, guiLeft - 129, guiTop + 52, 30, 12, ""));
		buttonList.add(new GuiButtonExt(2002, guiLeft - 96, guiTop + 52, 46, 12, ""));
		buttonList.add(new GuiButtonExt(2003, guiLeft - 47, guiTop + 52, 47, 12, ""));
		buttonList.add(new GuiButtonExt(3000, guiLeft - 162, guiTop + 84, 30, 12, ""));
		buttonList.add(new GuiButtonExt(3001, guiLeft - 129, guiTop + 84, 30, 12, ""));
		buttonList.add(new GuiButtonExt(3002, guiLeft - 96, guiTop + 84, 46, 12, ""));
		buttonList.add(new GuiButtonExt(3003, guiLeft - 47, guiTop + 84, 47, 12, ""));

		buttonList.add(new GuiButtonExt(4000, (guiLeft + (xSize / 2)) - (76 / 2), guiTop + 100, 76, 12, ""));

		if (!showConfig) {
			for (GuiButton configButton : buttonList) {
				if (configButton.id != 4000) {
					configButton.visible = false;
				}
			}
		} else {
			for (GuiButton configButton : buttonList) {
				if (configButton.id != 4000) {
					configButton.visible = true;
				}
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		Filter f = null;
		if (button.id >= 1000 && button.id <= 1100)
			f = tile.getWestFilter();
		if (button.id >= 2000 && button.id <= 2100)
			f = tile.getNorthFilter();
		if (button.id >= 3000 && button.id <= 3100)
			f = tile.getEastFilter();
		if (f != null) {
			int lastCipher = button.id % 10;
			switch (lastCipher) {
				case 0:
					f.meta ^= true;
					break;
				case 1:
					f.nbt ^= true;
					break;
				case 2:
					f.ore ^= true;
					break;
				case 3:
					f.white ^= true;
					break;
				default:
					break;
			}
		}
		if (button.id == 4000) {
			showConfig = !showConfig;

			if (!showConfig) {
				for (GuiButton configButton : buttonList) {
					if (configButton.id != 4000) {
						configButton.visible = false;
					}
				}
			} else {
				for (GuiButton configButton : buttonList) {
					if (configButton.id != 4000) {
						configButton.visible = true;
					}
				}
			}
		}
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("n", tile.getNorthFilter().serializeNBT());
		nbt.setTag("w", tile.getWestFilter().serializeNBT());
		nbt.setTag("e", tile.getEastFilter().serializeNBT());
		tile.handleMessage(nbt);
		LogisticalAutomation.networkWrapper.sendToServer(new MessageButton(nbt, tile.getPos()));
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		getButton(1000).displayString = (tile.getWestFilter().meta ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "META";
		getButton(1001).displayString = (tile.getWestFilter().nbt ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "NBT";
		getButton(1002).displayString = (tile.getWestFilter().ore ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "OREDICT";
		getButton(1003).displayString = (tile.getWestFilter().white ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "WHITE";
		getButton(2000).displayString = (tile.getNorthFilter().meta ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "META";
		getButton(2001).displayString = (tile.getNorthFilter().nbt ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "NBT";
		getButton(2002).displayString = (tile.getNorthFilter().ore ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "OREDICT";
		getButton(2003).displayString = (tile.getNorthFilter().white ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "WHITE";
		getButton(3000).displayString = (tile.getEastFilter().meta ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "META";
		getButton(3001).displayString = (tile.getEastFilter().nbt ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "NBT";
		getButton(3002).displayString = (tile.getEastFilter().ore ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "OREDICT";
		getButton(3003).displayString = (tile.getEastFilter().white ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "WHITE";

		getButton(4000).displayString = (showConfig ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "Toggle Config";
	}

	private GuiButton getButton(int id) {
		return buttonList.stream().filter(b -> b.id == id).findFirst().orElse(null);
	}

}