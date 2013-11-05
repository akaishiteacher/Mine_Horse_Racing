package net.akaishi_teacher.mhr;

import java.util.ArrayList;

import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoDamageEvent implements Listener {

	private MHRCore mhr;

	public NoDamageEvent(MHRCore mhr) {
		this.mhr = mhr;
	}

	@EventHandler
	public void horseNoDamageEvent(EntityDamageEvent event) {
		if (event.getEntity() instanceof Horse) {
			ArrayList<HorseData> datas = mhr.getStatus().getHorseDatas();
			for (HorseData data : datas) {
				if (data.horse.equals(event.getEntity())) {
					event.setDamage(0);
				}
			}
		}
	}

}
