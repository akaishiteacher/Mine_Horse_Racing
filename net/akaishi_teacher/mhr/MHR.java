package net.akaishi_teacher.mhr;

import java.util.ArrayList;
import java.util.logging.Logger;

import net.akaishi_teacher.mhr.commands.CommandExecutor;
import net.akaishi_teacher.mhr.commands.Help;
import net.akaishi_teacher.mhr.commands.TestCommand_A;
import net.akaishi_teacher.mhr.commands.TestCommand_B;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * MineHorseRacingPluginの元になるクラス
 * @author mozipi
 */
public final class MHR extends JavaPlugin {

	private Logger logger;

	private MHRListeners listener;

	private CommandExecutor cmdExecutor;

	@Override
	public void onEnable() {
		super.onEnable();
		logger = getLogger();
		logger.info("MineHorseRacingPlugin Enabled.");
		listener = new MHRListeners(this);
		getServer().getPluginManager().registerEvents(listener, this);
		cmdExecutor = new CommandExecutor(this);
		registerCommands();
	}

	@Override
	public void onDisable() {
		super.onDisable();
		logger.info("MineHorseRacingPlugin Disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return cmdExecutor.onCommand(sender, args);
	}

	protected void registerCommands() {
		cmdExecutor.addCommand(new Help(this, null, null, null, "HelpCommand"));
		cmdExecutor.addCommand(new TestCommand_A(this, "test_A","r r r o o", "mhr.test.a", "テストコマンドA"));
		cmdExecutor.addCommand(new TestCommand_B(this, "test_B","r r r o o", "mhr.test.b", "テストコマンドB"));
	}

}
