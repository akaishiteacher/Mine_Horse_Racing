package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;
import net.akaishi_teacher.util.lang.Language;

public class CannotExitMode extends MHRAbstractCommand {

	public CannotExitMode(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		boolean b = Boolean.parseBoolean(args.get(1));
		mhr.getCourseFunc().getManager().setCannotExitMode(b);
		Map<String, String> replaceMap = new HashMap<>();
		replaceMap.put("Mode", String.valueOf(b));
		sender.sendMessage(Language.replaceArgs(mhr.getLang().get("Cmd_Out_Course.CannotExitMode_Set"), replaceMap));
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.CannotExitMode");
	}

}
