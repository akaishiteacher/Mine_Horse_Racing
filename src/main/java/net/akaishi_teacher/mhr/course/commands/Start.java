package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;
import net.akaishi_teacher.mhr.course.Course;

public class Start extends MHRAbstractCommand {

	public Start(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		Course course = null;

		if (hasOption(args, 1)) {
			course = mhr.getCourseFunc().getManager().getCourse(args.get(1));
			if (course == null) {
				sender.sendMessage(mhr.getLang().get("Err_Course.CourseNotFound"));
				return true;
			}
		} else {
			course = mhr.getCourseFunc().getManager().getUsingCourse();
			if (course == null) {
				sender.sendMessage(mhr.getLang().get("Err_Course.RequiresTheUsingCourse"));
				return true;
			}
		}

		course.getCountdown().start(5, mhr.getPlugin());

		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.Start");
	}

}
