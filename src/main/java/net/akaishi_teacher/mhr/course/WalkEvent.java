package net.akaishi_teacher.mhr.course;

import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.HorseData;
import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.SimpleLocation;
import net.akaishi_teacher.util.lang.Language;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.Sound;
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
		int walkedCPIndex = mhrCourse.getManager().getWalkedCheckPointIndex(SimpleLocation.toSimpleLocation(block.getLocation()));
		if (walkedCPIndex != -1) {
			Course walkedCPCourse = mhrCourse.getManager().getWalkedCheckPointCourse(SimpleLocation.toSimpleLocation(block.getLocation()));
			checkpointWalked(block, data, walkedCPIndex, walkedCPCourse);
		}
	}

	private void checkpointWalked(Block block, HorseData data, int walkedCPIndex, Course course) {
		int nowCPIndex = data.courseSession.point;
		//No disqualification?
		if (!data.courseSession.isDisqualification()) {

			//Not have goal?
			if (!data.courseSession.hasGoal) {

				//Flying Check
				if (course.getCountdown().started() && !course.getCountdown().ended())
					flying(block, data, walkedCPIndex, nowCPIndex, course);

				//Disqualification?
				if (!data.courseSession.checkDiqualification(walkedCPIndex))
					checkpointPass(block, data, walkedCPIndex, nowCPIndex, course);
				else
					disqualification(block, data, walkedCPIndex, nowCPIndex, course);

			}
		}
	}

	private void checkpointPass(Block block, HorseData data, int walkedCPIndex, int nowCPIndex, Course course) {
		EnumPointState pointState = 
				data.courseSession.addPoint(walkedCPIndex, course.getOneLapIndex());
		if (pointState == EnumPointState.ADD) {
			alert(data, Sound.LEVEL_UP, 2);
		} else if (pointState == EnumPointState.LAP) {
			alert(data, Sound.NOTE_STICKS, 1.4F, 4, 1, 7);
		}
		if (data.courseSession.checkHasGoal(course)) {
			Map<String, String> replaceMap = new HashMap<>();
			int second = course.getTimer().getTime() / 20;
			int min = course.getTimer().getTime() / (20 * 60);
			replaceMap.put("Time", "" + min + ":" + second);
			replaceMap.put("Player", data.getPlayer().getName());
			Bukkit.broadcastMessage(Language.replaceArgs(mhr.getLang().get("Message_Course.Goal"), replaceMap));
		}
	}

	private void alert(HorseData data, final Sound sound, final float pitch, final int repeat, final int offset, final int overlap) {
		final Player player = data.getPlayer();
		for (int i = 0; i < repeat; i++) {
			Runnable alertRunnable = new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < overlap; j++) {
						player.playSound(player.getLocation(), sound, 1, pitch);
					}
				}
			};
			Bukkit.getScheduler().runTaskLater(mhr.getPlugin(), alertRunnable, offset * i);
		}
	}

	private void alert(HorseData data, Sound sound, float pitch) {
		Player player = data.getPlayer();
		player.playSound(player.getLocation(), sound, 1, pitch);
	}

	private void flying(Block block, HorseData data, int cpIndex, int nowCPIndex, Course course) {
		JavaPlugin plugin = mhr.getPlugin();
		Server server = plugin.getServer();
		data.courseSession.setDisqualification(true);
		if (data.horse.getPassenger() instanceof Player) {
			Player player = (Player) data.horse.getPassenger();
			Map<String, String> replaceMap = new HashMap<>();
			replaceMap.put("Player", player.getName());
			server.broadcastMessage(Language.replaceArgs(mhr.getLang().get("Message_Course.Flying"), replaceMap));
		}
	}

	private void disqualification(Block block, HorseData data, int walkedCPIndex, int nowCPIndex, Course course) {
		JavaPlugin plugin = mhr.getPlugin();
		Server server = plugin.getServer();
		data.courseSession.setDisqualification(true);
		if (data.horse.getPassenger() instanceof Player) {
			Player player = (Player) data.horse.getPassenger();
			Map<String, String> replaceMap = new HashMap<>();
			replaceMap.put("Player", player.getName());
			replaceMap.put("Lap", String.valueOf(walkedCPIndex / course.getOneLapIndex() + 1));
			replaceMap.put("LapIndex", String.valueOf(walkedCPIndex % course.getOneLapIndex()));
			server.broadcastMessage(Language.replaceArgs(mhr.getLang().get("Message_Course.Disqualification"), replaceMap));
		}
	}

}
