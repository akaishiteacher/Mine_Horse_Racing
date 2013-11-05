package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;
import net.akaishi_teacher.mhr.course.Course;

public class Add extends MHRAbstractCommand {

	public Add(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		String courseName = args.get(1);
		if (mhr.getCourseFunc().getManager().addCourse(new Course(courseName))) {
			sender.sendMessage(mhr.getLang().get("Cmd_Out_Course.Add_Added"));
		} else {
			sender.sendMessage(mhr.getLang().get("Cmd_Err_Course.Add_AlreadyAdded"));
		}
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.Add");
	}

}
