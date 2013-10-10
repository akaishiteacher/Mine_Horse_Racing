package net.akaishi_teacher.mhr.cource.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;
import net.akaishi_teacher.mhr.cource.Cource;

public class AddCource extends MHRAbstractCommand {

	public AddCource(MHR plugin, String pattern, String permission,
			String description) {
		super(plugin, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		String courceName = args.get(1);
		Cource cource = new Cource(courceName);
		pl.getMHRCource().addCource(cource);
		sender.sendMessage(pl.getLang().getLocalizedString("Cmd_Out_AddCource_Added"));
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return pl.getLang().getLocalizedString("Cmd_Usage_AddCource");
	}

}
