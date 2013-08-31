package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.mhr.commands.func.AbstractCommand;

public class Init extends AbstractCommand {

	public Init(MHR plugin, String pattern, String permission,
			String description) {
		super(plugin, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		plugin.getHorsesControler().init();
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return plugin.getLang().getLocalizedString("Cmd_Usage_Init");
	}

}
