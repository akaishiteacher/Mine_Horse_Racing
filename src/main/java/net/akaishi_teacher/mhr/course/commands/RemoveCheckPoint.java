package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;
import net.akaishi_teacher.mhr.course.Course;

public class RemoveCheckPoint extends MHRAbstractCommand {

	public RemoveCheckPoint(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		Course course = null;
		int index = -1;
		
		course = mhr.getCourseFunc().getManager().getUsingCourse();
		if (course == null) {
			sender.sendMessage(mhr.getLang().get("Err_Course.RequiresUsingCourse"));
			return true;
		}
		
		if (hasOption(args, 1)) {
			if (isNumber(args, 1)) {
				index = Integer.parseInt(args.get(1));
				course.removeCheckPoint(index);
			} else {
				sender.sendMessage(mhr.getLang().get("Err_NotNumber"));
			}
		} else {
			mhr.getCourseFunc().getManager().getUsingCourse().clearCheckPoint();
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.RemoveCheckPoint");
	}

}
