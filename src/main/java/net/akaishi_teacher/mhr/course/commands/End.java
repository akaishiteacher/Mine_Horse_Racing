package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;
import net.akaishi_teacher.mhr.course.Course;
import net.akaishi_teacher.util.lang.Language;

public class End extends MHRAbstractCommand {

	public End(MHRCore mhr, String pattern, String permission,
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
		
		Map<String, String> replaceMap = new HashMap<>();
		replaceMap.put("Course", course.getName());
		replaceMap.put("Time", course.getTimer().formattedTime("%01d:%02d"));
		sender.sendMessage(Language.replaceArgs(mhr.getLang().get("Cmd_Out_Course.End_End"), replaceMap));
		
		course.getTimer().stop();
		course.getTimer().reset();
		course.resetRankIndex();
		
		mhr.getCourseFunc().getManager().resetScore();
		
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.End");
	}

}
