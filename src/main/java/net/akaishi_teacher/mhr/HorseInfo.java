package net.akaishi_teacher.mhr;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Horse;

/**
 * 馬の個体情報が格納されているクラス
 * @author mozipi
 */
public class HorseInfo implements ConfigurationSerializable {

	/**
	 * 馬のX座標
	 */
	protected double x;

	/**
	 * 馬のY座標
	 */
	protected double y;

	/**
	 * 馬のZ座標
	 */
	protected double z;

	/**
	 * 馬がいるワールドのディメンションID
	 */
	protected int dimID;

	/**
	 * Horseクラスのインスタンス。ここがNullであってはならないとします。
	 */
	protected Horse horse;

	/**
	 * 馬が実際に存在するか。これは、サーバー起動時に必要になるため必須
	 */
	protected boolean isDead;

	/**
	 * 馬の個体番号
	 */
	protected int number;

	@SuppressWarnings("rawtypes")
	public HorseInfo(Map map) {
		x = (double) map.get("X");
		y = (double) map.get("Y");
		z = (double) map.get("Z");
		dimID = (int) map.get("DimID");
		isDead = (boolean) map.get("isDead");
		number = (int) map.get("Number");
	}

	public HorseInfo() {}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public int getDimID() {
		return dimID;
	}

	public void setDimID(int dimID) {
		this.dimID = dimID;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setHorse(Horse horse) {
		this.horse = horse;
	}

	public Horse getHorse() {
		return horse;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
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
		HorseInfo other = (HorseInfo) obj;
		if (number != other.number)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("X", x);
		map.put("Y", y);
		map.put("Z", z);
		map.put("DimID", dimID);
		map.put("isDead", isDead);
		map.put("Number", number);
		return map;
	}

}
