package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;

public class Remove extends MHRAbstractCommand {

	public Remove(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		String courseName = args.get(1);
		if (mhr.getCourseFunc().getManager().removeCourse(courseName)) {
			sender.sendMessage(mhr.getLang().get("Cmd_Out_Course.Remove_Removed"));
		} else {
			sender.sendMessage(mhr.getLang().get("Err_Course.CourseNotFound"));
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.Remove");
	}

}
