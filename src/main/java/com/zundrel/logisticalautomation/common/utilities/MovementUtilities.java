package com.zundrel.logisticalautomation.common.utilities;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class MovementUtilities {
	public static void pushEntity(Entity entity, BlockPos pos, double speed, EnumFacing facing) {
		pushEntity(entity, pos, speed, facing, true);
	}

	public static void pushEntity(Entity entity, BlockPos pos, double speed, EnumFacing facing, boolean shouldCenter) {
		entity.motionX += speed * facing.getFrontOffsetX();
		entity.motionZ += speed * facing.getFrontOffsetZ();

		if (speed * facing.getFrontOffsetX() > 0) {
			if (entity.motionX > speed) {
				entity.motionX = speed;
			}
		} else if (speed * facing.getFrontOffsetX() < 0) {
			if (entity.motionX < -speed) {
				entity.motionX = -speed;
			}
		}

		if (speed * facing.getFrontOffsetZ() > 0) {
			if (entity.motionZ > speed) {
				entity.motionZ = speed;
			}
		} else if (speed * facing.getFrontOffsetZ() < 0) {
			if (entity.motionZ < -speed) {
				entity.motionZ = -speed;
			}
		}

		if (shouldCenter) {
			centerEntity(entity, pos, speed, facing);
		}
	}

	private static void centerEntity(Entity entity, BlockPos pos, double speed, EnumFacing facing) {
		if (speed * facing.getFrontOffsetX() > 0) {
			if (entity.posZ > pos.getZ() + .55) {
				entity.motionZ += -0.1F;
			} else if (entity.posZ < pos.getZ() + .45) {
				entity.motionZ += 0.1F;
			} else {
				entity.motionZ = 0;
			}
		} else if (speed * facing.getFrontOffsetX() < 0) {
			if (entity.posZ > pos.getZ() + .55) {
				entity.motionZ += -0.1F;
			} else if (entity.posZ < pos.getZ() + .45) {
				entity.motionZ += 0.1F;
			} else {
				entity.motionZ = 0;
			}
		}

		if (speed * facing.getFrontOffsetZ() > 0) {
			if (entity.posX > pos.getX() + .55) {
				entity.motionX += -0.1F;
			} else if (entity.posX < pos.getX() + .45) {
				entity.motionX += 0.1F;
			} else {
				entity.motionX = 0;
			}
		} else if (speed * facing.getFrontOffsetZ() < 0) {
			if (entity.posX > pos.getX() + .55) {
				entity.motionX += -0.1F;
			} else if (entity.posX < pos.getX() + .45) {
				entity.motionX += 0.1F;
			} else {
				entity.motionX = 0;
			}
		}
	}

	private static void centerForcefully(Entity entity, BlockPos pos, double speed, EnumFacing facing) {
		if (speed * facing.getFrontOffsetX() > 0) {
			if (entity.posZ > pos.getZ() + .65) {
				entity.motionZ += -0.1F;
			} else if (entity.posZ < pos.getZ() + .35) {
				entity.motionZ += 0.1F;
			} else {
				entity.posZ = Math.floor(entity.posZ) + 0.5;
			}
		} else if (speed * facing.getFrontOffsetX() < 0) {
			if (entity.posZ > pos.getZ() + .65) {
				entity.motionZ += -0.1F;
			} else if (entity.posZ < pos.getZ() + .35) {
				entity.motionZ += 0.1F;
			} else {
				entity.posZ = Math.floor(entity.posZ) + 0.5;
			}
		}

		if (speed * facing.getFrontOffsetZ() > 0) {
			if (entity.posX > pos.getX() + .65) {
				entity.motionX += -0.1F;
			} else if (entity.posX < pos.getX() + .35) {
				entity.motionX += 0.1F;
			} else {
				entity.posX = Math.floor(entity.posX) + 0.5;
			}
		} else if (speed * facing.getFrontOffsetZ() < 0) {
			if (entity.posX > pos.getX() + .65) {
				entity.motionX += -0.1F;
			} else if (entity.posX < pos.getX() + .35) {
				entity.motionX += 0.1F;
			} else {
				entity.posX = Math.floor(entity.posX) + 0.5;
			}
		}
	}

	public static void pushEntityUp(Entity entity, BlockPos pos, double speed, EnumFacing facing, boolean vertical) {
		entity.motionY = 0;

		entity.motionY += speed;

		if (vertical) {
			entity.motionX += 0.1F * facing.getFrontOffsetX();
			entity.motionZ += 0.1F * facing.getFrontOffsetZ();

			if (entity.motionX > 0.1F) {
				entity.motionX = 0.1F;
			}

			if (entity.motionZ > 0.1F) {
				entity.motionZ = 0.1F;
			}
		}

		if (speed > 0) {
			if (entity.motionY > speed) {
				entity.motionY = speed;
			}
		} else if (speed < 0) {
			if (entity.motionY < speed) {
				entity.motionY = speed;
			}
		}

		if (vertical) {
			centerForcefully(entity, pos, speed, facing);
		} else {
			centerEntity(entity, pos, speed, facing);
		}
	}
}