package net.akaishi_teacher.mhr.common;

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

	@SuppressWarnings("rawtypes")
	public SimpleLocation(Map map) {
		this.worldName = (String) map.get("WorldName");
		this.x = (double) map.get("X");
		this.y = (double) map.get("Y");
		this.z = (double) map.get("Z");
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

	@Override
	public String toString() {
		return worldName + "<" + x + ", " + y + ", " + z + " - " + yaw + ", " + pitch;
	}

	public static Location castLocation(SimpleLocation loc, JavaPlugin plugin) {
		return new Location(plugin.getServer().getWorld(loc.worldName), loc.x, loc.y, loc.z);
	}

	public static SimpleLocation toSimpleLocation(Location loc) {
		return new SimpleLocation(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((worldName == null) ? 0 : worldName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleLocation other = (SimpleLocation) obj;
		if (worldName == null) {
			if (other.worldName != null)
				return false;
		} else if (!worldName.equals(other.worldName))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

}
