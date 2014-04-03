package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;
import net.akaishi_teacher.mhr.course.data.Course;
import net.akaishi_teacher.util.lang.Language;

public class SetUseCourse extends MHRAbstractCommand {

	public SetUseCourse(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		Course course = null;

		course = mhr.getCourseFunc().getManager().getCourse(args.get(1));
		if (course == null) {
			sender.sendMessage(mhr.getLang().get("Err_Course.CourseNotFound"));
			return true;
		}

		mhr.getCourseFunc().getManager().setUsingCourse(course);

		Map<String, String> replaceMap = new HashMap<>();
		replaceMap.put("UsingCourse", course.getName());
		sender.sendMessage(Language.replaceArgs(mhr.getLang().get("Cmd_Out_Course.SetUseCourse_Set"), replaceMap));

		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.SetUseCourse");
	}

}
