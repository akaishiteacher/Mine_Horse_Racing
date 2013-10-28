package net.akaishi_teacher.mhr.course.data;

import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.other.SimpleLocation;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

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
	
	public boolean isInArea(Block block) {
		Location location = block.getLocation();
        if (this.p1.x <= this.p2.x) {
                if (location.getX() < this.p1.x || this.p2.x + 1 <= location.getX())
                        return false;
        } else {
                if (location.getX() < this.p2.x || this.p1.x + 1 <= location.getX())
                        return false;
        }
        if (this.p1.y <= this.p2.y) {
                if (location.getY() < this.p1.y || this.p2.y + 1 <= location.getY())
                        return false;
        } else {
                if (location.getY() < this.p2.y || this.p1.y + 1 <= location.getY())
                        return false;
        }
        if (this.p1.z <= this.p2.z) {
                if (location.getZ() < this.p1.z || this.p2.z + 1 <= location.getZ())
                        return false;
        } else {
                if (location.getZ() < this.p2.z || this.p1.z + 1 <= location.getZ())
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
