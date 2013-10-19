package net.akaishi_teacher.mhr.other;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * シンプルなロケーションの保存を提供します。
 * @author mozipi
 */
public class SimpleLocation implements ConfigurationSerializable {

	/**
	 * World名
	 */
	public String worldName;

	/**
	 * 座標
	 */
	public double x, y, z;

	public SimpleLocation(String worldName, int x, int y, int z) {
		super();
		this.worldName = worldName;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public SimpleLocation(String worldName, double x, double y, double z) {
		super();
		this.worldName = worldName;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public SimpleLocation(Map<String, Object> map) {
		this.worldName = (String) map.get("WorldName");
		this.x = (int) map.get("X");
		this.y = (int) map.get("Y");
		this.z = (int) map.get("Z");
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("WorldName", worldName);
		map.put("X", x);
		map.put("Y", y);
		map.put("Z", z);
		return map;
	}

}
