package net.akaishi_teacher.mhr.course.data;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * 馬のコース走行状態を保持しているクラスです。
 * @author mozipi
 */
public class PointCounter implements ConfigurationSerializable {

	/**
	 * 現在のチェックポイント通過数
	 */
	public int point;

	public PointCounter() {
	}
	
	@SuppressWarnings("rawtypes")
	public PointCounter(Map map) {
		this.point = (int) map.get("Point");
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("Point", point);
		return map;
	}
	
}
