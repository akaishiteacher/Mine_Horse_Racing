package net.akaishi_teacher.mhr;

import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoDamageEvent implements Listener {

	@EventHandler
	public void horseNoDamageEvent(EntityDamageEvent event) {
		//TODO MHRの馬のみにする
		if (event.getEntity() instanceof Horse) {
			event.setDamage(0);
		}
	}

}
