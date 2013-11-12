package net.akaishi_teacher.mhr.course;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.HorseData;
import net.akaishi_teacher.mhr.MHRCore;

import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class CannotExitListener implements Listener {

	private MHRCore mhr;

	public CannotExitListener(MHRCore mhr) {
		this.mhr = mhr;
	}

	@EventHandler
	public void horseNoDamageEvent(VehicleExitEvent event) {
		if (mhr.getCourseFunc().getManager().isCannotExitMode()) {
			if (event.getVehicle() instanceof Horse) {
				ArrayList<HorseData> datas = mhr.getStatus().getHorseDatas();
				for (HorseData data : datas) {
					if (data.horse.equals(event.getVehicle())) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

}
