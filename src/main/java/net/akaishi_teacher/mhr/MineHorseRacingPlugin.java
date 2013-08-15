package net.akaishi_teacher.mhr;

import net.akaishi_teacher.mhr.command.BaseParentCommand;
import net.akaishi_teacher.mhr.command.SampleChildCommand;
import net.akaishi_teacher.mhr.command.SampleParentCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

/**
 * MineHorseRacingPlugin
 *
 * @version 0.1.0
 */
public final class MineHorseRacingPlugin extends JavaPlugin {

	public final Random RANDOM = new Random();

	/**
	 * コマンドを読み込む
	 * コマンドの追加もここに
	 */
	public void loadCommands() {
		// メソッドチェーンで追加も可。 サンプルとして何種類か
		MHRCommandExecutor mce = new MHRCommandExecutor(this);
		mce.addCommand(new SampleChildCommand(this, "child1"));
		mce.addCommand(new SampleChildCommand(this, "child2"))
				.addCommand(new SampleChildCommand(this, "child3"));
		mce.addCommand(new BaseParentCommand(this, "parent1")
				.addCommand(new SampleChildCommand(this, "child1"))
				.addCommand(new SampleChildCommand(this, "child2"))
				.addCommand(new SampleChildCommand(this, "child3"))
		).addCommand(new SampleParentCommand(this, "parent2"));

		getCommand("minehorseracing").setExecutor(mce);
	}

	public void onEnable() {
		getServer().getPluginManager().registerEvents(new MHREventListener(this), this);

		this.loadCommands();

		getLogger().info("Enabled " + this.getName());
	}

	public void reload() {
//		HorseManager.detectHorsesCreatedByPluginInServer();
	}

	public MineHorseRacingPlugin sendPluginMessage(CommandSender sender, String text) {
		sender.sendMessage("[" + this.getName() + "] " + text);

		return this;
	}
}
