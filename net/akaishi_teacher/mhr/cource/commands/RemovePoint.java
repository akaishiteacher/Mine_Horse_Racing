package net.akaishi_teacher.mhr.cource.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;

public class RemovePoint extends MHRAbstractCommand {

	public RemovePoint(MHR plugin, String pattern, String permission,
			String description) {
		super(plugin, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		return false;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return null;
	}

}
