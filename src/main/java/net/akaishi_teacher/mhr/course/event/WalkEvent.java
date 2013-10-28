package net.akaishi_teacher.mhr.course.event;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.course.MHRCourse;
import net.akaishi_teacher.mhr.data.HorseData;

import org.bukkit.block.Block;

public final class WalkEvent {

	private MHRCourse mhrCourse;
	
	private MHRCore mhr;
	
	public WalkEvent(MHRCourse mhrCourse) {
		this.mhrCourse = mhrCourse;
		this.mhr = mhrCourse.getMHR();
	}
	
	public void walk(Block block, HorseData data) {
	}
	
}
