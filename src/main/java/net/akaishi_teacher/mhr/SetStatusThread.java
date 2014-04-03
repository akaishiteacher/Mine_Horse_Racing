package net.akaishi_teacher.mhr;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.data.HorseData;
import net.minecraft.server.v1_6_R3.AttributeInstance;
import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.GenericAttributes;

import org.bukkit.craftbukkit.v1_6_R3.entity.CraftLivingEntity;

public final class SetStatusThread implements Runnable {

	private MHRCore mhr;

	public SetStatusThread(MHRCore mhr) {
		this.mhr = mhr;
	}

	@Override
	public void run() {
		ArrayList<HorseData> datas = mhr.getStatus().getHorseDatas();
		for (HorseData data : datas) {
			data.horse.setJumpStrength(mhr.getStatus().getCommonStatus().getJump());
			AttributeInstance attributes = ((EntityInsentient)((CraftLivingEntity)data.horse).getHandle()).getAttributeInstance(GenericAttributes.d);
			attributes.setValue(mhr.getStatus().getCommonStatus().getSpeed());
		}
	}

}
