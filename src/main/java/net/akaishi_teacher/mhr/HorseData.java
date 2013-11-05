package net.akaishi_teacher.mhr;

import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.course.PointCounter;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Horse;

/**
 * 個別に保存が必要な馬のステータスが存在するクラスです。
 * @author mozipi
 */
public class HorseData implements ConfigurationSerializable {

	/**
	 * 馬のID
	 */
	public int id;

	/**
	 * 馬の座標
	 */
	public SimpleLocation loc;

	/**
	 * 馬のインスタンス
	 */
	public Horse horse;

	/**
	 * 馬のコースの走行状態を保持しているクラスのインスタンス
	 */
	public PointCounter pointCounter;
	
	public HorseData(int id, SimpleLocation loc) {
		this.id = id;
		this.loc = loc;
		this.pointCounter = new PointCounter();
	}

	@SuppressWarnings("rawtypes")
	public HorseData(Map map) {
		this.id = (int) map.get("Id");
		this.loc = (SimpleLocation) map.get("Loc");
		this.pointCounter = (PointCounter) map.get("PointData");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		HorseData other = (HorseData) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Id", id);
		if (horse != null) {
			Location putLoc = horse.getLocation();
			loc = new SimpleLocation(putLoc.getWorld().getName(), putLoc.getX(), putLoc.getY(), putLoc.getZ());
		}
		map.put("Loc", loc);
		map.put("PointData", pointCounter);
		return map;
	}
	
}
