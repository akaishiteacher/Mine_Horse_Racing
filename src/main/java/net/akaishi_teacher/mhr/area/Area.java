package net.akaishi_teacher.mhr.area;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 範囲を扱うクラス
 *
 * @version 0.1.0
 */
public class Area {
	protected Location pos1;
	protected Location pos2;

	public Area setPos1(Location location) {
		this.pos1 = location;
		return this;
	}

	public Location getPos1() {
		return this.pos1;
	}

	public Area setPos2(Location location) {
		this.pos2 = location;
		return this;
	}

	public Location getPos2() {
		return this.pos2;
	}

	/**
	 * エリアが正常に定義されているかを判定
	 *
	 * @return 正常に定義されていればtrueを返す。
	 */
	public boolean isSetPositions() {
		return this.pos1 != null && this.pos2 != null && this.pos1.getWorld().equals(this.pos2.getWorld());
	}

	/**
	 * 指定されたプレイヤーがエリアの範囲内にあるかを判定
	 *
	 * @param player 判定するプレイヤー
	 * @return エリアの範囲内にあればtrueを返す。 エリアの範囲外またはエリアが未定義であればfalseを返す。
	 */
	public boolean isInArea(Player player) {
		return isInArea(player.getLocation());
	}

	/**
	 * 指定された場所がエリアの範囲内にあるかを判定
	 *
	 * @param location 判定する場所
	 * @return エリアの範囲内にあればtrueを返す。 エリアの範囲外またはエリアが未定義であればfalseを返す。
	 */
	public boolean isInArea(Location location) {
		if (!isSetPositions()) return false;
		// X軸の判定
		if (this.pos1.getBlockX() <= this.pos2.getBlockX()) {
			if (location.getBlockX() < this.pos1.getBlockX() || this.pos2.getBlockX() + 1 <= location.getBlockX())
				return false;
		} else {
			if (location.getBlockX() < this.pos2.getBlockX() || this.pos1.getBlockX() + 1 <= location.getBlockX())
				return false;
		}
		// Y軸の判定
		if (this.pos1.getBlockY() <= this.pos2.getBlockY()) {
			if (location.getBlockY() < this.pos1.getBlockY() || this.pos2.getBlockY() + 1 <= location.getBlockY())
				return false;
		} else {
			if (location.getBlockY() < this.pos2.getBlockY() || this.pos1.getBlockY() + 1 <= location.getBlockY())
				return false;
		}
		// Z軸の判定
		if (this.pos1.getBlockZ() <= this.pos2.getBlockZ()) {
			if (location.getBlockZ() < this.pos1.getBlockZ() || this.pos2.getBlockZ() + 1 <= location.getBlockZ())
				return false;
		} else {
			if (location.getBlockZ() < this.pos2.getBlockZ() || this.pos1.getBlockZ() + 1 <= location.getBlockZ())
				return false;
		}
		return true;
	}

	public List<Map<String, Integer>> toJsonArrayFormat() {
		if (!isSetPositions()) return null;
		List<Map<String, Integer>> area = new ArrayList<>();

		Map<String, Integer> pos1 = new HashMap<>();
		pos1.put("x", this.pos1.getBlockX());
		pos1.put("y", this.pos1.getBlockY());
		pos1.put("z", this.pos1.getBlockZ());

		Map<String, Integer> pos2 = new HashMap<>();
		pos2.put("x", this.pos2.getBlockX());
		pos2.put("y", this.pos2.getBlockY());
		pos2.put("z", this.pos2.getBlockZ());

		area.add(pos1);
		area.add(pos2);
		return area;
	}
}
