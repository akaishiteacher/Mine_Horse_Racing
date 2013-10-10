package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.MHR;

import org.bukkit.command.CommandSender;

public class SetJump extends MHRAbstractCommand {

	public SetJump(MHR plugin, String pattern, String permission,
			String description) {
		super(plugin, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		try {
			double speed = Double.parseDouble(args.get(1));
			pl.getHorseStats().setJump(speed);
			sender.sendMessage(pl.getLang().getLocalizedString("Cmd_Out_SetJump_Set") + "JumpStrength: " + speed);
		} catch (NumberFormatException e) {
			sender.sendMessage(pl.getLang().getLocalizedString("Cmd_Err_NumberFormatException"));
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return pl.getLang().getLocalizedString("Cmd_Usage_SetJump");
	}

}
