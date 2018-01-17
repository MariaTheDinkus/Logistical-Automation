package com.zundrel.logisticalautomation.common;

import com.zundrel.logisticalautomation.common.info.ModInfo;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

@EventBusSubscriber(modid = ModInfo.MOD_ID)
public class CommonEventHandler {
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<World> event) {
//        System.out.println("I TRIED TO BUT FAILED");
//        if (event.getObject() instanceof World) {
//            System.out.println("I TRIED");
//            event.addCapability(new ResourceLocation(ModInfo.MOD_ID, "conveyorItems"), new CapabilityTesting(event.getObject()));
//        }
    }

    @SubscribeEvent
    public static void onWorldTick(WorldTickEvent event) {
//        CapabilityTesting test = event.world.getCapability(LogisticalAutomation.TEST_DATA, null);
//
//         //System.out.println(test.getItems().isEmpty());
//
//        if (test != null) {
//            // test.setItemAtPos(new BlockPos(9, 9, 9), new ItemConveyorPos(0,
//            // EnumFacing.SOUTH, new ItemStack(Items.APPLE)));
//        }
//
//        if (test != null && !event.world.isRemote) {
//            List<BlockPos> toRemove = new ArrayList<>();
//
//            HashMap<BlockPos, ItemConveyorPos> toAdd = new HashMap<>();
//
//            for (Entry<BlockPos, ItemConveyorPos> entry : test.getItems().entrySet()) {
//                BlockPos pos = entry.getKey();
//                ItemConveyorPos item = entry.getValue();
//
//                if (!(event.world.getBlockState(pos).getBlock() instanceof BlockConveyor)) {
//                    toRemove.add(pos);
//                    event.world.spawnEntity(new EntityItem(event.world, pos.getX(), pos.getY(), pos.getZ(), item.getStack()));
//                }
//
//                if (!test.getItems().containsKey(pos.offset(item.getFacing())) && event.world.getBlockState(pos.offset(item.getFacing())).getBlock() instanceof BlockConveyor && event.world.getBlockState(pos.offset(item.getFacing())).getValue(BlockFacing.FACING) != item.getFacing().getOpposite()) {
//                    item.setConveyorPosition(item.getConveyorPosition() + (0.9 / 20));
//                    LogisticalAutomation.networkWrapper.sendToAll(new MessageItem(new BlockPos(pos), item.serializeNBT()));
//                } else {
//                    item.setConveyorPosition(0);
//                    LogisticalAutomation.networkWrapper.sendToAll(new MessageItem(new BlockPos(pos), item.serializeNBT()));
//                }
//
//                if (item.getConveyorPosition() >= 1) {
//                    if (event.world.getBlockState(pos).getBlock() instanceof BlockConveyor) {
//                        if (event.world.getBlockState(pos.offset(item.getFacing())).getBlock() instanceof BlockConveyor) {
//                            toRemove.add(pos);
//                            BlockPos posToPlace = pos.offset(item.getFacing());
//                            item.setFacing(event.world.getBlockState(pos.offset(item.getFacing())).getValue(BlockFacing.FACING));
//                            toAdd.put(posToPlace, item);
//                        }
//                        item.setConveyorPosition(0);
//                    } else {
//                        item.setConveyorPosition(0);
//                        LogisticalAutomation.networkWrapper.sendToAll(new MessageItem(new BlockPos(pos), item.serializeNBT()));
//                    }
//                }
//            }
//
//            for (BlockPos pos : toRemove) {
//                test.removeItemAtPos(pos);
//            }
//
//            for (Entry<BlockPos, ItemConveyorPos> entry : toAdd.entrySet()) {
//                BlockPos pos = entry.getKey();
//                ItemConveyorPos item = entry.getValue();
//
//                test.setItemAtPos(pos, item);
//            }
//        }
    }
}
