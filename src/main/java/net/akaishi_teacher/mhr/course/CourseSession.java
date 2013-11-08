package net.akaishi_teacher.mhr.course;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * 馬のコース走行状態を保持しているクラスです。
 * @author mozipi
 */
public class CourseSession implements ConfigurationSerializable {

	/**
	 * 現在のチェックポイント通過数
	 */
	public int point;
	
	/**
	 * x周目
	 */
	public int lap;

	/**
	 * 失格しているか
	 */
	public boolean disqualification;
	
	public CourseSession() {
	}
	
	@SuppressWarnings("rawtypes")
	public CourseSession(Map map) {
		this.point = (int) map.get("Point");
		this.disqualification = (boolean) map.get("Disqualification");
	}
	
	/**
	 * チェックポイントを増加させます。<br>
	 * このメソッドは、Lapを自動追加します。<br>
	 * <b>このメソッドは、踏んだチェックポイントのコースが違うと正常に動作しない可能性があります。</b>
	 * @param onelapIndex 踏んだチェックポイントのコースのOneLapIndex
	 */
	public void addPoint(int onelapIndex) {
		if (++point > onelapIndex) {
			lap++;
		}
	}
	
	/**
	 * 失格しているかを判定します。
	 * @param walkedCPIndex 踏んだチェックポイントのIndex
	 * @return 失格していればtrue
	 */
	public boolean isDiqualification(int walkedCPIndex) {
		return (point+1 < walkedCPIndex);
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("Point", point);
		map.put("Disqualification", disqualification);
		return map;
	}
	
}
