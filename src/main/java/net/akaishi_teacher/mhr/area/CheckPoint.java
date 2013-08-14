package net.akaishi_teacher.mhr.area;

import org.bukkit.Location;

/**
 * チェックポイントを扱うクラス
 */
public class CheckPoint {
	CheckPointType type;
	String label;
	Area area;
	Location spawnPoint;

	public CheckPoint setType(CheckPointType type) {
		this.type = type;
		return this;
	}

	public CheckPoint setLabel(String label) {
		this.label = label;
		return this;
	}

	public CheckPoint setArea(Area area) {
		this.area = area;
		return this;
	}

	public CheckPoint setSpawnPoint(Location location) {
		this.spawnPoint = location;
		return this;
	}
}
