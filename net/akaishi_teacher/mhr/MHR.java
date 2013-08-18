package net.akaishi_teacher.mhr;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MHR extends JavaPlugin {

	private ArrayList<HorseInfo> horseInfoList = new ArrayList<HorseInfo>();

	private Logger logger;

	private MHRListeners listener;

	@Override
	public void onEnable() {
		super.onEnable();
		logger = getLogger();
		logger.info("MineHorseRacingPlugin Enabled.");
		listener = new MHRListeners(this);
		getServer().getPluginManager().registerEvents(listener, this);
		getServer().getScheduler().runTaskTimer(this, listener, 0, 1);
	}

	@Override
	public void onDisable() {
		super.onDisable();
		logger.info("MineHorseRacingPlugin Disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return true;
	}

}
