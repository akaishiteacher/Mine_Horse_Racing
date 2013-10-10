package net.akaishi_teacher.mhr.cource.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;

public class SetUsingCource extends MHRAbstractCommand {

	public SetUsingCource(MHR plugin, String pattern, String permission,
			String description) {
		super(plugin, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		String courseName = args.get(1);
		pl.getMHRCource().setUsingCource(courseName);
		sender.sendMessage(pl.getLang().getLocalizedString("Cmd_Out_SetUsingCource_Set"));
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return pl.getLang().getLocalizedString("Cmd_Usage_SetUsingCource");
	}

}
