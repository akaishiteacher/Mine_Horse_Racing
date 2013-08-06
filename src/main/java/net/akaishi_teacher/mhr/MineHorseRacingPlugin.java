package net.akaishi_teacher.mhr;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * MineHorseRacingPlugin
 */
public class MineHorseRacingPlugin extends JavaPlugin {
	protected HashMap<Integer, Horse> horses = new HashMap<Integer, Horse>();

	public void onEnable() {

		getCommand("minehorseracing").setExecutor(new PlayerCommandExecutor(this));

		getLogger().info("Enabled  " + this.getName());
	}

	// ---------- よく使いそうなもの ----------

	public int addHorse(Horse horse) {
		this.horses.put(horse.getEntityId(), horse);

		return horse.getEntityId();
	}

	public boolean purgeHorses() {
		for (Iterator<Map.Entry<Integer, Horse>> it = horses.entrySet().iterator(); it.hasNext(); ) {
			Horse horse = it.next().getValue();
			getLogger().info("killed horse(" + horse.getMetadata("individualValue") + ")");
			horse.remove();
			it.remove();
		}
		for (World world : Bukkit.getWorlds()) {
			for (Entity entity : world.getLivingEntities()) {
				if (entity instanceof Horse) {
					if (entity.hasMetadata("individualValue")) {
						getLogger().info("killed horse(" + entity.getMetadata("individualValue") + ")");
						entity.remove();
					}
				}
			}
		}
		return true;
	}

	public boolean killHorse(int n) {
		Horse horse = findHorseByID(n);
		if (horse != null) {
			if (this.horses.containsValue(horse)) {
				this.horses.remove(horse);
			}
			getLogger().info("killed horse(" + horse.getMetadata("individualValue") + ")");
			horse.remove();
		}
		return false;
	}

	public Horse findHorseByID(int individualValue) {
		if (this.horses.containsKey(individualValue)) {
			return this.horses.remove(individualValue);
		} else {
			for (World world : getServer().getWorlds()) {
				for (Entity entity : world.getLivingEntities()) {
					if (entity.getEntityId() == individualValue) {
						try {
							horses.put(individualValue, (Horse) entity);
						} catch (ClassCastException e) {
							continue;
						}
						return (Horse) entity;
					}
				}
			}
			return null;
		}
	}
}
