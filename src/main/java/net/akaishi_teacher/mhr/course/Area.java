package net.akaishi_teacher.mhr.course;

import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.SimpleLocation;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * 範囲を保持するクラスです。
 * @author mozipi
 */
public class Area implements ConfigurationSerializable {

	protected SimpleLocation p1;
	
	protected SimpleLocation p2;
	
	public Area(SimpleLocation p1, SimpleLocation p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Area(Location p1, Location p2) {
		this.p1 = SimpleLocation.toSimpleLocation(p1);
		this.p2 = SimpleLocation.toSimpleLocation(p2);
	}

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
