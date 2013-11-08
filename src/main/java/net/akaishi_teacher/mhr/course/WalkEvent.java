package net.akaishi_teacher.mhr.course;

import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.HorseData;
import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.SimpleLocation;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class WalkEvent {

	private MHRCourse mhrCourse;

	private MHRCore mhr;

	public WalkEvent(MHRCourse mhrCourse) {
		this.mhrCourse = mhrCourse;
		this.mhr = mhrCourse.getMHR();
	}

	public void walk(Block block, HorseData data) {
		int cpIndex = mhrCourse.getManager().getWalkedCheckPointIndex(SimpleLocation.toSimpleLocation(block.getLocation()));
		if (cpIndex != -1) {
			Course walkedCPCourse = mhrCourse.getManager().getWalkedCheckPointCourse(SimpleLocation.toSimpleLocation(block.getLocation()));
			checkpointWalked(block, data, cpIndex, walkedCPCourse);
		}
	}

	private void checkpointWalked(Block block, HorseData data, int cpIndex, Course course) {
		JavaPlugin plugin = mhr.getPlugin();
		Server server = plugin.getServer();
		int nowCPIndex = data.courseSession.point;
		//No disqualification?
		if (!data.courseSession.disqualification) {
			//Flying Check
			if (course.getCountdown().started() && !course.getCountdown().ended()) {
				flying(block, data, cpIndex, nowCPIndex, course);
			}
			//Disqualification?
			if (!data.courseSession.isDiqualification(cpIndex)) {
				checkpointPass(block, data, cpIndex, nowCPIndex, course);
			} else {
				disqualification(block, data, cpIndex, nowCPIndex, course);
			}
		}
	}

	private void checkpointPass(Block block, HorseData data, int cpIndex, int nowCPIndex, Course course) {
		JavaPlugin plugin = mhr.getPlugin();
		Server server = plugin.getServer();

		System.out.println(nowCPIndex % course.getOneLapIndex());
		System.out.println((cpIndex-1) % course.getOneLapIndex());
		if (nowCPIndex % course.getOneLapIndex() <= (cpIndex-1) % course.getOneLapIndex()) {
			data.courseSession.point += 1;
			if (nowCPIndex % course.getOneLapIndex() == 0) {
				server.broadcastMessage("1周!");
			}
		}
	}

	private void flying(Block block, HorseData data, int cpIndex, int nowCPIndex, Course course) {
		JavaPlugin plugin = mhr.getPlugin();
		Server server = plugin.getServer();
		data.courseSession.disqualification = true;
		if (data.horse.getPassenger() instanceof Player) {
			Player player = (Player) data.horse.getPassenger();
			Map<String, String> replaceMap = new HashMap<>();
			replaceMap.put("Player", player.getName());
			server.broadcastMessage(Language.replaceArgs(mhr.getLang().get("Message_Course.Flying"), replaceMap));
		}
	}

	private void disqualification(Block block, HorseData data, int cpIndex, int nowCPIndex, Course course) {
		JavaPlugin plugin = mhr.getPlugin();
		Server server = plugin.getServer();
		data.courseSession.disqualification = true;
		if (data.horse.getPassenger() instanceof Player) {
			Player player = (Player) data.horse.getPassenger();
			Map<String, String> replaceMap = new HashMap<>();
			replaceMap.put("Player", player.getName());
			replaceMap.put("Lap", String.valueOf(cpIndex / course.getOneLapIndex() + 1));
			replaceMap.put("LapIndex", String.valueOf(cpIndex % course.getOneLapIndex()));
			server.broadcastMessage(Language.replaceArgs(mhr.getLang().get("Message_Course.Disqualification"), replaceMap));
		}
	}

}
