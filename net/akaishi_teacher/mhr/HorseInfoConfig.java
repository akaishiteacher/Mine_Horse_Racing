package net.akaishi_teacher.mhr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class HorseInfoConfig extends Config {

	public HorseInfoConfig(MHR plugin, File config) throws IOException {
		super(plugin, config);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadConfig() throws IOException {
		yamlConf.addDefault("HorseInfoList", new ArrayList<HorseInfo>());
		plugin.getHorsesControler().setHorseInfoList((ArrayList<HorseInfo>) yamlConf.getList("HorseInfoList"));
	}

	@Override
	public void saveConfig() throws IOException {
		Iterator<HorseInfo> itHil = plugin.getHorsesControler().getHorseInfoList().iterator();
		while (itHil.hasNext()) {
			HorseInfo info = itHil.next();
			if (info.isDead()) {
				itHil.remove();
				continue;
			}
			Location loc = info.getHorse().getLocation();
			info.setX(loc.getX());
			info.setY(loc.getY());
			info.setZ(loc.getZ());
			info.setDimID(loc.getWorld().getEnvironment().getId());
			info.setDead(info.getHorse().isDead());
		}
		yamlConf.set("HorseInfoList", plugin.getHorsesControler().getHorseInfoList());

		this.yamlConf.save(this.configFile);
	}

	@Override
	public void reloadConfig() throws IOException {
		this.yamlConf =
				YamlConfiguration.loadConfiguration(this.configFile);

		this.loadConfig();
	}

}
