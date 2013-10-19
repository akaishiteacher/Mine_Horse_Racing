package net.akaishi_teacher.mhr.status;

import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.other.SimpleLocation;

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

	public HorseData(int id, SimpleLocation loc) {
		this.id = id;
		this.loc = loc;
	}

	public HorseData(Map<String, Object> map) {
		this.id = (int) map.get("Id");
		this.loc = (SimpleLocation) map.get("Loc");
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Id", id);
		if (horse == null) {
			Location putLoc = horse.getLocation();
			loc = new SimpleLocation(putLoc.getWorld().getName(), putLoc.getX(), putLoc.getY(), putLoc.getZ());
		}
		map.put("Loc", loc);
		return map;
	}

}
