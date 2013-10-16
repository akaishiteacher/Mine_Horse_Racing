package net.akaishi_teacher.mhr;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

	/**
	 * MineHorseRacingの処理部
	 */
	private MHR mhr;

	@Override
	public void onDisable() {
		super.onDisable();
		mhr.disable();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		mhr = new MHR(this);
		mhr.loadConfig();
		mhr.init();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return mhr.getCmdExecutor().onCommand(sender, args);
	}

}
