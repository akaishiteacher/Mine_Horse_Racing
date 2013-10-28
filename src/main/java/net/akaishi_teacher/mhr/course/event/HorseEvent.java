package net.akaishi_teacher.mhr.course.event;

import net.akaishi_teacher.mhr.data.HorseData;

import org.bukkit.block.Block;

public interface HorseEvent {

	void walk(Block block, HorseData data);
	
}
