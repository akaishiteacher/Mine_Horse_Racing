package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;
import net.akaishi_teacher.mhr.course.data.CheckPoint;
import net.akaishi_teacher.mhr.course.data.Course;
import net.akaishi_teacher.util.lang.Language;

public class SetAngle extends MHRAbstractCommand {

	public SetAngle(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		Course course;
		int index = -1;
		int angle = -1;

		course = mhr.getCourseFunc().getManager().getUsingCourse();
		if (course == null) {
			sender.sendMessage(mhr.getLang().get("Err_Course.RequiresTheUsingCourse"));
			return true;
		}

		try {
			index = Integer.parseInt(args.get(1));
			angle = Integer.parseInt(args.get(2));
		} catch (NumberFormatException e) {
			sender.sendMessage(mhr.getLang().get("Err.NotNumber"));
			return true;
		}

		if (!course.hasCheckPoint(index)) {
			sender.sendMessage(mhr.getLang().get("Cmd_Err_Course.CheckPointNotFound"));
			return true;
		}

		CheckPoint point = course.getCheckPoint(index);
		point.setAngle(angle);
		
		Map<String, String> replaceMap = new HashMap<>();
		replaceMap.put("Index", String.valueOf(index));
		replaceMap.put("Angle", String.valueOf(angle));
		sender.sendMessage(Language.replaceArgs(mhr.getLang().get("Cmd_Out_Course.SetAngle_Set"), replaceMap));
		
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.SetAngle");
	}

}
