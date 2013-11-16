package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;
import net.akaishi_teacher.mhr.course.CourseManager;
import net.akaishi_teacher.mhr.course.data.Course;
import net.akaishi_teacher.util.lang.Language;

public class SetLap extends MHRAbstractCommand {

	public SetLap(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		CourseManager manager = mhr.getCourseFunc().getManager();
		Course course = null;
		int lap = -1;
		
		if (isNumber(args, 1)) {
			lap = Integer.parseInt(args.get(1));
		} else {
			sender.sendMessage(mhr.getLang().get("Err_NotNumber"));
			return true;
		}
		
		if (hasOption(args, 2)) {
			if (manager.hasCourse(args.get(2))) {
				course = manager.getCourse(args.get(2));
			} else {
				sender.sendMessage(mhr.getLang().get("Err_Course.CourseNotFound"));
				return true;
			}
		} else {
			course = manager.getUsingCourse();
			if (course == null) {
				sender.sendMessage(mhr.getLang().get("Err_Course.RequiresTheUsingCourse"));
				return true;
			}
		}
		
		course.setLap(lap);

		Map<String, String> replaceMap = new HashMap<>();
		replaceMap.put("Course", course.getName());
		replaceMap.put("Lap", String.valueOf(lap));
		sender.sendMessage(Language.replaceArgs(mhr.getLang().get("Cmd_Out_Course.SetLap_Set"), replaceMap));
		
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.SetLap");
	}

}
