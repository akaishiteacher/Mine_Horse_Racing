package net.akaishi_teacher.mhr.other;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.plugin.java.JavaPlugin;

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
	public double x, y, z, yaw, pitch;

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

	public SimpleLocation(String worldName, double x, double y, double z, double yaw, double pitch) {
		super();
		this.worldName = worldName;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public SimpleLocation(Map<String, Object> map) {
		this.worldName = (String) map.get("WorldName");
		this.x = (int) map.get("X");
		this.y = (int) map.get("Y");
		this.z = (int) map.get("Z");
		this.yaw = (double) map.get("Yaw");
		this.pitch = (double) map.get("Pitch");
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("WorldName", worldName);
		map.put("X", x);
		map.put("Y", y);
		map.put("Z", z);
		map.put("Yaw", yaw);
		map.put("Pitch", pitch);
		return map;
	}

	public static Location castLocation(SimpleLocation loc, JavaPlugin plugin) {
		return new Location(plugin.getServer().getWorld(loc.worldName), loc.x, loc.y, loc.z);
	}

	public static SimpleLocation toSimpleLocation(Location loc) {
		return new SimpleLocation(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ());
	}

}
