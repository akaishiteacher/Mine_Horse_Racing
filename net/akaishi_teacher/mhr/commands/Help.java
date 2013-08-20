package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHR;

public class Help extends AbstractCommand {

	public Help(MHR plugin, String label, String pattern, String permission,
			String description) {
		super(plugin, label, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		sender.sendMessage("§aCommand Executed --HelpCommand--");
		return true;
	}

}
