package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.HashMap;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.command.CommandSender;

public class SetJump extends MHRAbstractCommand {

	public SetJump(MHR mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		try {
			double jump = Double.parseDouble(args.get(1));
			mhr.getStatus().getCommonStatus().setJump(jump);
			HashMap<String, String> replaceMap = new HashMap<>();
			replaceMap.put("jump", args.get(1));
			String sendA = Language.replaceArgs(mhr.getLang().get("Cmd_Out_SetJump_Set"), replaceMap);
			sender.sendMessage(sendA);
		} catch (NumberFormatException e) {
			sender.sendMessage(mhr.getLang().get("Err_NotNumber"));
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_SetJump");
	}

}
