package com.zundrel.logisticalautomation.client;

import com.zundrel.logisticalautomation.common.info.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = ModInfo.MOD_ID, value = Side.CLIENT)
public class ClientEventHandler {
    private static Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public static void renderWorld(RenderWorldLastEvent event) {
//		CapabilityTesting test = mc.world.getCapability(LogisticalAutomation.TEST_DATA, null);
//
//		EntityPlayer player = mc.player;
//
//		double doubleX = -(player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks());
//		double doubleY = -(player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks());
//		double doubleZ = -(player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks());
//
//		// System.out.println("HEH");
//
//		for (Entry<BlockPos, ItemConveyorPos> entry : test.getItems().entrySet()) {
//			BlockPos pos = entry.getKey();
//			ItemConveyorPos item = entry.getValue();
//			ItemStack stack = item.getStack();
//
//			EntityItem customItem = new EntityItem(mc.world, 0, 0, 0, stack);
//			customItem.hoverStart = 0.0F;
//
//			GlStateManager.pushMatrix();
//			{
//
//				GlStateManager.translate(doubleX + 0.5, doubleY, doubleZ + 0.5);
//
//				// GlStateManager.rotate(90 *
//				// item.getFacing().getHorizontalIndex(), 0, 1, 0);
//
//				double x = pos.getX();
//				double z = pos.getZ();
//
//				if (item.getFacing() == EnumFacing.EAST) {
//					x += item.getConveyorPosition();
//				} else if (item.getFacing() == EnumFacing.WEST) {
//					x -= item.getConveyorPosition();
//				} else if (item.getFacing() == EnumFacing.NORTH) {
//					z -= item.getConveyorPosition();
//				} else if (item.getFacing() == EnumFacing.SOUTH) {
//					z += item.getConveyorPosition();
//				}
//
//				mc.getRenderManager().renderEntity(customItem, x, pos.getY(), z, 0, 0, false);
//
//			}
//			GlStateManager.popMatrix();
//		}
    }
}