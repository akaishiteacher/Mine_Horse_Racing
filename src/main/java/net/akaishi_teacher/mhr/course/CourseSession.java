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
	protected int point;

	/**
	 * x周目
	 */
	protected int lap;

	/**
	 * 失格しているか
	 */
	protected boolean disqualification;

	/**
	 * ゴールしているか。これの代入はhasGoalメソッド呼び出し時に変更されます。
	 */
	protected boolean hasGoal;
	
	/**
	 * 1Lap前のlaptime
	 */
	protected int lapTime;
	
	public CourseSession() {
	}
	
	@SuppressWarnings("rawtypes")
	public CourseSession(Map map) {
		this.point = (int) map.get("Point");
		this.lap = (int) map.get("Lap");
		this.setDisqualification((boolean) map.get("Disqualification"));
	}

	/**
	 * ゴールしているか判定します。
	 * @param course 判定する指標となるコース
	 * @return ゴールしていればtrue。
	 */
	public boolean checkHasGoal(Course course) {
		if (course.getLap() <= lap) {
			hasGoal = true;
			return true;
		}
		return false;
	}
	
	/**
	 * チェックポイントを増加させます。<br>
	 * このメソッドは、Lapを自動追加します。<br>
	 * <b>このメソッドは、踏んだチェックポイントのコースが違うと正常に動作しない可能性があります。</b>
	 * @param walkedCPIndex 踏んだチェックポイントのIndex
	 * @param onelapIndex 踏んだチェックポイントのコースのOneLapIndex
	 * @return lapを追加した場合はLAP。ポイント追加のみならADD、失敗すればFAILED。
	 */
	public EnumPointState addPoint(int walkedCPIndex, int onelapIndex) {
		if (point + 1 > onelapIndex && walkedCPIndex == 1) {
			lap++;
			point = 0;
			return EnumPointState.LAP;
		}
		if (walkedCPIndex >= point + 1) {
			point++;
			return EnumPointState.ADD;
		}
		return EnumPointState.FAILED;
	}

	/**
	 * 現在走っているラップ数を返します。
	 * @return 現在走っているラップ数
	 */
	public int getLap() {
		return lap;
	}
	
	/**
	 * 失格しているかを判定します。
	 * @param walkedCPIndex 踏んだチェックポイントのIndex
	 * @return 失格していればtrue
	 */
	public boolean checkDiqualification(int walkedCPIndex) {
		return (getPoint()+1 < walkedCPIndex);
	}

	/**
	 * 失格しているかを返します。
	 * @return 失格していればtrue
	 */
	public boolean isDisqualification() {
		return disqualification;
	}

	/**
	 * 失格しているかをセットします。
	 * @param disqualification 失格しているならばtrue
	 */
	public void setDisqualification(boolean disqualification) {
		this.disqualification = disqualification;
	}

	/**
	 * ゴールしているかを返します。
	 * @return ゴールしていればtrue
	 */
	public boolean hasGoal() {
		return hasGoal;
	}
	
	/**
	 * 現在のラップのチェックポイント通過数を返します。
	 * @return 現在のラップのチェックポイント通過数
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * LapTimeを指定します。
	 * @param lapTime LapTime
	 */
	public void setLapTime(int lapTime) {
		this.lapTime = lapTime;
	}
	
	/**
	 * LapTimeを追加します。
	 * @param lapTime LapTime
	 */
	public void addLapTime(int lapTime) {
		this.lapTime += lapTime;
	}
	
	/**
	 * 1ラップ前のLapTimeを返します。
	 * @return  1ラップ前のLapTime
	 */
	public int getLapTime() {
		return lapTime;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("Point", getPoint());
		map.put("Disqualification", isDisqualification());
		map.put("Lap", lap);
		return map;
	}

}
