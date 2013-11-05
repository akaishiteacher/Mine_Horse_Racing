package net.akaishi_teacher.mhr.course.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.SimpleLocation;
import net.akaishi_teacher.mhr.commands.MHRAbstractCommand;
import net.akaishi_teacher.mhr.course.Area;
import net.akaishi_teacher.mhr.course.CheckPoint;
import net.akaishi_teacher.mhr.course.Course;
import net.akaishi_teacher.mhr.course.CourseManager;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.command.CommandSender;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.RegionSelector;

public class AddCheckPoint extends MHRAbstractCommand {

	public AddCheckPoint(MHRCore mhr, String pattern, String permission,
			String description) {
		super(mhr, pattern, permission, description);
	}

	@Override
	public boolean execute(CommandSender sender, ArrayList<String> args) {
		CourseManager manager = mhr.getCourseFunc().getManager();
		Course course = null;
		Area area = null;
		int index = -1;

		course = manager.getUsingCourse();
		if (course == null){
			sender.sendMessage(mhr.getLang().get("Err_Course.RequiresTheUsingCourse"));
			return true;
		}
		
		if (hasOption(args, 1)) {
			if (isNumber(args, 1)) {
				index = Integer.parseInt(args.get(1));
			} else {
				sender.sendMessage(mhr.getLang().get("Err_NotNumber"));
				return true;
			}
		} else {
			index = manager.getMaxCheckPointIndex() + 1;
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
		mhr.getCourseFunc().getManager().addCheckPoint(new CheckPoint(area, index));
		
		Map<String, String> replaceMap = new HashMap<>();
		replaceMap.put("Index", String.valueOf(index));
		sender.sendMessage(Language.replaceArgs(mhr.getLang().get("Cmd_Out_Course.AddPoint_Added"), replaceMap));
		
		return true;
	}

	@Override
	public String getUsage(CommandSender sender) {
		return mhr.getLang().get("Cmd_Usage_Course.AddPoint");
	}

}
