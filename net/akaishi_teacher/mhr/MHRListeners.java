package net.akaishi_teacher.mhr;

import java.util.ArrayList;

import net.minecraft.server.v1_6_R2.EntityHorse;
import net.minecraft.server.v1_6_R2.GenericAttributes;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftHorse;
import org.bukkit.entity.Horse;
import org.bukkit.event.Listener;

public class MHRListeners implements Listener, Runnable {

	private MHR plugin;

	public MHRListeners(MHR plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		ArrayList<World> worldList = (ArrayList<World>) plugin.getServer().getWorlds();
		for (World world :  worldList) {
			ArrayList<Horse> horseList = (ArrayList<Horse>) world.getEntitiesByClass(Horse.class);
			for (Horse horse : horseList) {
				EntityHorse eh = ((CraftHorse)horse).getHandle();
				eh.getAttributeInstance(GenericAttributes.d).setValue(5);
			}
		}
	}

}
