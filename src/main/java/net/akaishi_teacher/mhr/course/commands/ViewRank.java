package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;

public class ViewRank extends MHRAbstractCommand {

	public ViewRank(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		boolean b = false;
		if (hasOption(args, 1)) {
			b = Boolean.parseBoolean(args.get(1));
		}
		
		mhr.getCourseFunc().getManager().setViewRank(b);
		
		mhr.getCourseFunc().getManager().resetScore();
		
		sender.sendMessage(mhr.getLang().get("Cmd_Out_Course.ViewRank_Set"));
		
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.ViewRank");
	}

}
