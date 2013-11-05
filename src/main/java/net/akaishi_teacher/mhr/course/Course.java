package net.akaishi_teacher.mhr.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * コースの情報を保持しているクラスです。
 * コースには、コース名・ラップ数・1周に必要なチェックポイント数・チェックポイントのリストを保持しています。
 * @author mozipi
 */
public class Course implements ConfigurationSerializable {

	protected String courseName;

	protected int lap;

	protected int onelapIndex;

	private ArrayList<CheckPoint> checkpoints = new ArrayList<>();

	public Course(String courseName) {
		this.courseName = courseName;
	}

	public Course(String courseName, ArrayList<CheckPoint> checkpoints) {
		this(courseName);
		this.checkpoints = checkpoints;
	}

	public String getName() {
		return courseName;
	}
	
	/**
	 * チェックポイントを追加します。
	 * @param checkpoint 追加するチェックポイント
	 */
	public void addCheckPoint(CheckPoint checkpoint) {
		checkpoints.add(checkpoint);
	}

	/**
	 * チェックポイントを削除します。
	 * @param checkpoint 削除するチェックポイント
	 * @return {@link ArrayList#remove(Object)}の振る舞い
	 */
	public boolean removeCheckPoint(CheckPoint checkpoint) {
		return checkpoints.remove(checkpoint);
	}

	/**
	 * チェックポイントを削除します。
	 * @param index 削除したいチェックポイントの番号
	 * @return チェックポイントの中に指定されたindexと同じ値のチェックポイントがあれば削除し、true。見つからず削除出来なかった場合はfalse
	 */
	public boolean removeCheckPoint(int index) {
		boolean flag = false;
		while (true) {
			int foundIndex = checkpoints.indexOf(new CheckPoint(null, index));
			if (foundIndex != -1) {
				checkpoints.remove(foundIndex);
				flag = true;
			} else {
				break;
			}
		}
		return flag;
	}

	/**
	 * チェックポイントをすべて消去します。
	 */
	public void clearCheckPoint() {
		checkpoints.clear();
	}
	
	/**
	 * 指定されたIndexに一致するチェックポイントを返します。<br>
	 * 存在しない場合はnullを返します。
	 * @param index 取得したいチェックポイントのIndex
	 * @return 指定されたIndexに一致するチェックポイント。存在しない場合はnull
	 */
	public CheckPoint getCheckPoint(int index) {
		int foundIndex = checkpoints.indexOf(new CheckPoint(null, index));
		return checkpoints.get(foundIndex);
	}
	
	/**
	 * チェックポイントが存在するか判定します。
	 * @param index 判定したいチェックポイントのIndex
	 * @return チェックポイントが存在する場合はtrue。存在しなければfalse
	 */
	public boolean hasCheckPoint(int index) {
		return checkpoints.contains(new CheckPoint(null, index));
	}
	
	/**
	 * チェックポイントのリストを返します。
	 * @return チェックポイントのリスト
	 */
	public ArrayList<CheckPoint> getCheckpoints() {
		return checkpoints;
	}

	/**
	 * ラップ数を指定します。
	 * @param lap ラップ数
	 */
	public void setLap(int lap) {
		this.lap = lap;
	}

	/**
	 * ラップ数を取得します。
	 * @return ラップ数
	 */
	public int getLap() {
		return lap;
	}

	/**
	 * 1周に必要なチェックポイントの数を指定します。
	 * @param onelapIndex 1周に必要なチェックポイントの数
	 */
	public void setOneLapIndex(int onelapIndex) {
		this.onelapIndex = onelapIndex;
	}

	/**
	 * 1周に必要なチェックポイントの数を返します。
	 * @return 1周に必要なチェックポイントの数
	 */
	public int getOneLapIndex() {
		return onelapIndex;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Course(Map map) {
		this.courseName = (String) map.get("CourseName");
		this.lap = (int) map.get("Lap");
		this.onelapIndex = (int) map.get("OneLap");
		this.checkpoints =  (ArrayList<CheckPoint>) map.get("CheckPoints");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((courseName == null) ? 0 : courseName.hashCode());
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
		Course other = (Course) obj;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		return true;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("CourseName", courseName);
		map.put("Lap", lap);
		map.put("OneLap", onelapIndex);
		map.put("CheckPoints", checkpoints);
		return map;
	}

}
