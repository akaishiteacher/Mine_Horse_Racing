package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.util.command.AbstractCommand;
import net.akaishi_teacher.util.command.ComparatorCommandArgs_Reverse;

import org.bukkit.command.CommandSender;

public class Help extends AbstractCommand {

	public Help(MHR plugin, String pattern, String permission,
			String description) {
		super(plugin, pattern, permission, description);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		sender.sendMessage("§a======== MineHorseRacingPlugin Commands ================");
		HashSet<AbstractCommand> cmdSet = plugin.getCmdExecutor().getCommandSet();
		ArrayList l = new ArrayList<AbstractCommand>(cmdSet);
		Collections.sort(l, new ComparatorCommandArgs_Reverse());
		cmdSet = new HashSet<AbstractCommand>(l);
		for (AbstractCommand command : cmdSet) {
			if (command.getPermission() == null || sender.hasPermission(command.getPermission())) {
				sender.sendMessage(command.getUsage(sender));
			}
		}
		sender.sendMessage("§a===================================================");
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return plugin.getLang().getLocalizedString("Cmd_Usage_Help");
	}

}
