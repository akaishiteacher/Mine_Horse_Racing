package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.MHR;

import org.bukkit.command.CommandSender;

public class SetSpeed extends MHRAbstractCommand {

	public SetSpeed(MHR plugin, String pattern, String permission,
			String description) {
		super(plugin, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		try {
			double speed = Double.parseDouble(args.get(1));
			plugin.getHorseStats().setSpeed(speed);
			sender.sendMessage(plugin.getLang().getLocalizedString("Cmd_Out_SetSpeed_Set") + "Speed: " + speed);
		} catch (NumberFormatException e) {
			sender.sendMessage(plugin.getLang().getLocalizedString("Cmd_Err_NumberFormatException"));
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return plugin.getLang().getLocalizedString("Cmd_Usage_SetSpeed");
	}

}
