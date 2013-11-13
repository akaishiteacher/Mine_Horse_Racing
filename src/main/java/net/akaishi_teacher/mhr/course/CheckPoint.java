package net.akaishi_teacher.mhr.course;

import java.util.HashMap;
import java.util.Map;

import net.akaishi_teacher.mhr.common.Area;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * チェックポイントの情報を保持するクラスです。<br>
 * チェックポイントには、Area・Index・Angleの3つの情報を保持しています。<br>
 * Angleは、コースアウト機能のために使用されます。
 * @author mozipi
 */
public class CheckPoint implements ConfigurationSerializable {

	/**
	 * チェックポイントのエリア
	 */
	protected Area area;
	
	/**
	 * チェックポイントのIndex
	 */
	protected int index;

	/**
	 * チェックポイントが保持する角度
	 */
	protected int angle;
	
	public CheckPoint(Area area, int index) {
		super();
		this.area = area;
		this.index = index;
	}

	public CheckPoint(Area area, int index, int angle) {
		this(area, index);
		this.angle = angle;
	}
	
	@SuppressWarnings("rawtypes")
	public CheckPoint(Map map) {
		super();
		this.area = (Area) map.get("Area");
		this.index = (int) map.get("Index");
		this.angle = (int) map.get("Angle");
	}
	
	/**
	 * チェックポイントの範囲を示すクラスを返します。
	 * @return area チェックポイントの範囲を示すクラス
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * チェックポイントのindexを返します。
	 * @return index チェックポイントのindex
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * チェックポイントが保持する角度を設定します。
	 * @param angle チェックポイントが保持する角度
	 */
	public void setAngle(int angle) {
		this.angle = angle;
	}
	
	/**
	 * チェックポイントが保持している角度を返します。
	 * @return チェックポイントが保持している角度
	 */
	public int getAngle() {
		return angle;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
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
		CheckPoint other = (CheckPoint) obj;
		if (index != other.index)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("Area", area);
		map.put("Index", index);
		map.put("Angle", angle);
		return map;
	}
	
}
