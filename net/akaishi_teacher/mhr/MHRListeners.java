package net.akaishi_teacher.mhr;

import java.util.ArrayList;

import net.minecraft.server.v1_6_R2.EntityHorse;
import net.minecraft.server.v1_6_R2.GenericAttributes;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftHorse;
import org.bukkit.entity.Horse;
import org.bukkit.event.Listener;

/**
 * MineHorseRacingPluginのイベントリスナー&スレッド用クラス
 * @author mozipi
 */
public final class MHRListeners implements Listener, Runnable {

	private MHR plugin;

	public MHRListeners(MHR plugin) {
		super();
		this.plugin = plugin;
	}



	@Override
	public void run() {
		ArrayList<World> worlds = (ArrayList<World>) plugin.getServer().getWorlds();
		for (World world : worlds) {
			ArrayList<Horse> horses = (ArrayList<Horse>) world.getEntitiesByClass(Horse.class);
			for (Horse h : horses) {
				EntityHorse horse = ((CraftHorse) h).getHandle();
				horse.getAttributeInstance(GenericAttributes.d).setValue(plugin.getHorseStats().getSpeed());
				h.setJumpStrength(plugin.getHorseStats().getJump());
			}
		}
	}

}
