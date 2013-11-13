package net.akaishi_teacher.mhr.common;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * 範囲を保持するクラスです。
 * @author mozipi
 */
public class Area implements ConfigurationSerializable {

	/**
	 * A点
	 */
	public SimpleLocation p1;

	/**
	 * B点
	 */
	public SimpleLocation p2;

	public Area(SimpleLocation p1, SimpleLocation p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public Area(Location p1, Location p2) {
		this.p1 = SimpleLocation.toSimpleLocation(p1);
		this.p2 = SimpleLocation.toSimpleLocation(p2);
	}

	/**
	 * 範囲内にあるかを判定します。
	 * @param location 判定される座標
	 * @return 引数locationが範囲内にある場合はtrue
	 */
	public boolean isInArea(SimpleLocation location) {
		if (this.p1.x <= this.p2.x) {
			if (location.x < this.p1.x || this.p2.x + 1 <= location.x)
				return false;
		} else {
			if (location.x < this.p2.x || this.p1.x + 1 <= location.x)
				return false;
		}
		if (this.p1.y <= this.p2.y) {
			if (location.y < this.p1.y || this.p2.y + 1 <= location.y)
				return false;
		} else {
			if (location.y < this.p2.y || this.p1.y + 1 <= location.y)
				return false;
		}
		if (this.p1.z <= this.p2.z) {
			if (location.z < this.p1.z || this.p2.z + 1 <= location.z)
				return false;
		} else {
			if (location.z < this.p2.z || this.p1.z + 1 <= location.z)
				return false;
		}
		return true;
	}

	/**
	 * 範囲拡大します。
	 * @param x 範囲拡大量X
	 * @param y 範囲拡大量Y
	 * @param z 範囲拡大量Z
	 */
	public SimpleLocation[] expand(double x, double y, double z) {
		SimpleLocation[] locations = new SimpleLocation[2];
		locations[0] = new SimpleLocation(p1.worldName, p1.x, p1.y, p1.z);
		locations[1] = new SimpleLocation(p2.worldName, p2.x, p2.y, p2.z);
		if (locations[0].x < locations[1].x) {
			locations[0].x += -x;
			locations[1].x += x;
		} else {
			locations[0].x += x;
			locations[1].x += -x;
		}
		if (locations[0].y < locations[1].y) {
			locations[0].y += -y;
			locations[1].y += y;
		} else {
			locations[0].y += y;
			locations[1].y += -y;
		}
		if (locations[0].z < locations[1].z) {
			locations[0].z += -z;
			locations[1].z += z;
		} else {
			locations[0].z += z;
			locations[1].z += -z;
		}
		return locations;
	}

	@SuppressWarnings("rawtypes")
	public Area(Map map) {
		this.p1 = (SimpleLocation) map.get("P1");
		this.p2 = (SimpleLocation) map.get("P2");
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("P1", p1);
		map.put("P2", p2);
		return map;
	}

}
