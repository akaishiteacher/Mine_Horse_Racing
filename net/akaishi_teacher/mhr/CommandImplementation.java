package net.akaishi_teacher.mhr;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author mozipi
 * コマンド処理を実装するためのインターフェイスです。
 */
public interface CommandImplementation {

	CommandRunInfo onCommand(JavaPlugin absPlugin, CommandSender sender, Command command,
			String label, String[] args);

}
