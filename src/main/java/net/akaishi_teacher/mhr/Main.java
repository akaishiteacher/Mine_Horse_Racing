package net.akaishi_teacher.mhr;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Pluginの実行部分。詳細は{@link MHRCore MHR}クラスを参照してください。
 * @author mozipi
 */
public final class Main extends JavaPlugin {

	/**
	 * MineHorseRacingの処理部
	 */
	private MHRCore mhr;

	@Override
	public void onDisable() {
		super.onDisable();
		mhr.preDisable();
		mhr.disable();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		mhr = new MHRCore(this);
		mhr.preInit();
		mhr.init();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return mhr.getCmdExecutor().onCommand(sender, args);
	}

}
