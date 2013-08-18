package net.akaishi_teacher.mhr.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.akaishi_teacher.mhr.CommandImplementation;
import net.akaishi_teacher.mhr.CommandRunInfo;

public class Help implements CommandImplementation {

	@Override
	public CommandRunInfo onCommand(JavaPlugin absPlugin, CommandSender sender,
			Command command, String label, String[] args) {
		CommandRunInfo info = new CommandRunInfo(50);
		String[] helpMessages = new String[] {
				"§5[MineHorseRacingPlugin]",
				"§5/minehorseracing : ヘルプを表示します",
				"§5/minehorseracing setspeed (Speed)"
		};
		info.setReturnMessages(helpMessages);
		return info;
	}

}
