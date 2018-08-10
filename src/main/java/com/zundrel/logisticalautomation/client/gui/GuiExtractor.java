package com.zundrel.logisticalautomation.client.gui;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityExtractor;
import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityFilter;
import com.zundrel.logisticalautomation.common.containers.ContainerExtractor;
import com.zundrel.logisticalautomation.common.containers.ContainerFilter;
import com.zundrel.logisticalautomation.common.info.ModInfo;
import com.zundrel.logisticalautomation.common.network.MessageButton;
import com.zundrel.logisticalautomation.common.utilities.Filter;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.io.IOException;

public class GuiExtractor extends GuiContainer {

	private static boolean showConfig = false;
	private TileEntityExtractor tile;

	public GuiExtractor(ContainerExtractor inventorySlotsIn) {
		super(inventorySlotsIn);
		xSize = 176;
		ySize = 132;
		tile = ((ContainerExtractor) inventorySlots).tile;
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
			this.mc.getTextureManager().bindTexture(new ResourceLocation(ModInfo.MOD_ID, "textures/gui/extractor_cutout.png"));
		} else {
			this.mc.getTextureManager().bindTexture(new ResourceLocation(ModInfo.MOD_ID, "textures/gui/extractor.png"));
		}
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (showConfig) {
			this.mc.getTextureManager().bindTexture(new ResourceLocation(ModInfo.MOD_ID, "textures/gui/extractor_settings.png"));
			this.drawTexturedModalRect(guiLeft - 167, guiTop, 0, 0, 168, 100);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		fontRenderer.drawString("Output", 8, 6, 4210752);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButtonExt(1000, guiLeft - 162, guiTop + 20, 30, 12, ""));
		buttonList.add(new GuiButtonExt(1001, guiLeft - 129, guiTop + 20, 30, 12, ""));
		buttonList.add(new GuiButtonExt(1002, guiLeft - 96, guiTop + 20, 46, 12, ""));
		buttonList.add(new GuiButtonExt(1003, guiLeft - 47, guiTop + 20, 47, 12, ""));

		buttonList.add(new GuiButtonExt(4000, (guiLeft + (xSize / 2)) - (76 / 2), guiTop + 36, 76, 12, ""));

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
			f = tile.getFilter();
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
		nbt.setTag("f", tile.getFilter().serializeNBT());
		tile.handleMessage(nbt);
		LogisticalAutomation.networkWrapper.sendToServer(new MessageButton(nbt));
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		getButton(1000).displayString = (tile.getFilter().meta ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "META";
		getButton(1001).displayString = (tile.getFilter().nbt ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "NBT";
		getButton(1002).displayString = (tile.getFilter().ore ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "OREDICT";
		getButton(1003).displayString = (tile.getFilter().white ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "WHITE";

		getButton(4000).displayString = (showConfig ? TextFormatting.GREEN.toString() : TextFormatting.RED.toString()) + "Toggle Config";
	}

	private GuiButton getButton(int id) {
		return buttonList.stream().filter(b -> b.id == id).findFirst().orElse(null);
	}

}