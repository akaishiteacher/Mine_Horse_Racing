package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.RegionSelector;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;
import net.akaishi_teacher.mhr.common.Area;
import net.akaishi_teacher.mhr.common.SimpleLocation;
import net.akaishi_teacher.mhr.course.CourseManager;
import net.akaishi_teacher.mhr.course.data.Course;
import net.akaishi_teacher.util.lang.Language;

public class SetRange extends MHRAbstractCommand {

	public SetRange(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		CourseManager manager = mhr.getCourseFunc().getManager();
		Course course;
		Area area;

		course = manager.getUsingCourse();
		if (hasOption(args, 1)) {
			course = manager.getCourse(args.get(1));
			if (course == null) {
				sender.sendMessage(mhr.getLang().get("Err_Course.CourseNotFound"));
				return true;
			}
		}
		if (course == null) { 
			sender.sendMessage(mhr.getLang().get("Err_Course.RequiresTheUsingCourse"));
			return true;
		}

		LocalSession session = WorldEdit.getInstance().getSession(sender.getName());
		if (session != null) {
			RegionSelector regionSelector = session.getRegionSelector(session.getSelectionWorld());
			try {
				String worldName = regionSelector.getRegion().getWorld().getName();
				int x1 = regionSelector.getRegion().getMinimumPoint().getBlockX();
				int y1 = regionSelector.getRegion().getMinimumPoint().getBlockY();
				int z1 = regionSelector.getRegion().getMinimumPoint().getBlockZ();
				int x2 = regionSelector.getRegion().getMaximumPoint().getBlockX();
				int y2 = regionSelector.getRegion().getMaximumPoint().getBlockY();
				int z2 = regionSelector.getRegion().getMaximumPoint().getBlockZ();
				SimpleLocation loc1 = new SimpleLocation(worldName, x1, y1, z1);
				SimpleLocation loc2 = new SimpleLocation(worldName, x2, y2, z2);
				area = new Area(loc1, loc2);
			} catch (IncompleteRegionException e) {
				e.printStackTrace();
				return true;
			}
		} else {
			sender.sendMessage(mhr.getLang().get("Err_Course.RangeNotSpecified"));
			return true;
		}
		course.setRange(area);
		
		Map<String, String> replaceMap = new HashMap<>();
		replaceMap.put("Course", course.getName());
		sender.sendMessage(Language.replaceArgs(mhr.getLang().get("Cmd_Out_Course.SetRange_Set"), replaceMap));
		
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.SetRange");
	}

}
