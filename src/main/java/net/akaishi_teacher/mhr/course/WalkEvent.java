package net.akaishi_teacher.mhr.course;

import net.akaishi_teacher.mhr.HorseData;
import net.akaishi_teacher.mhr.MHRCore;

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
