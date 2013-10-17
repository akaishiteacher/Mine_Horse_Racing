package net.akaishi_teacher.mhr.status;

import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.other.SimpleLocation;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

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
		map.put("Loc", loc);
		return map;
	}

}
