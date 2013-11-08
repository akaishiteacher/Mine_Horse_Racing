package net.akaishi_teacher.mhr.course;

import java.util.ArrayList;
import java.util.Iterator;

import net.akaishi_teacher.mhr.HorseData;
import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.SimpleLocation;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

public final class CourseManager {

	private ArrayList<Course> courses;

	private MHRCore mhrCore;

	private MHRCourse mhrCourse;

	private Course usingCourse;

	private boolean viewRank;

	public CourseManager(MHRCourse mhrCourse) {
		this.mhrCourse = mhrCourse;
		this.mhrCore = mhrCourse.getMHR();
	}

	public CourseManager(MHRCourse mhrCourse, ArrayList<Course> courses) {
		this(mhrCourse);
		this.courses = new ArrayList<>();
		for (Course course : courses) {
			addCourse(course);
		}
	}

	/**
	 * 使用するコースを指定します。
	 * @param usingCourse 使用するコース
	 */
	public void setUsingCourse(Course usingCourse) {
		this.usingCourse = usingCourse;
	}

	/**
	* 現在使用しているコースを指定します。
	* @return 現在使用しているコース
	*/
	public Course getUsingCourse() {
		return usingCourse;
	}

	/**
	 * コースを追加します。<br>
	 * 既にコースが存在する(同じコース名のコース)場合は、追加されず、falseを返します。
	 * @param course 追加するコース
	 * @return コースが既に存在せず、正常に追加できた場合はtrue。既にコースが存在するもしくは正常に追加出来なかった場合はfalse
	 */
	public boolean addCourse(Course course) {
		if (courses.indexOf(course) == -1) {
			course.getCountdown().addListener(new SayCountdownMessage(mhrCore.getLang()));
			course.getCountdown().addListener(new StartTimerListener(course.getTimer()));
			courses.add(course);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * コースを削除します。<br>
	 * コースが存在しない場合はfalseを返します。
	 * @param course 削除したいコース
	 * @return コースが存在し、削除できた場合はtrue。それ以外はfalse
	 */
	public boolean removeCourse(Course course) {
		return courses.remove(course);
	}

	/**
	 * コースを削除します。<br>
	 * コースが存在しない場合がfalseを返します。
	 * @param courseName 削除したいコースのコース名
	 * @return コースが存在し、削除できた場合がtrue。それ以外はfalse
	 */
	public boolean removeCourse(String courseName) {
		return courses.remove(new Course(courseName));
	}

	/**
	 * 指定した名前のコースを取得します。<br>
	 * 指定した名前のコースがない場合はnullを返します。
	 * @param courseName コース名
	 * @return 指定したコース名のコース。存在しない場合はnull
	 */
	public Course getCourse(String courseName) {
		Course c = new Course(courseName);
		int index = courses.indexOf(c);
		if (index != -1)
			return courses.get(index);
		else
			return null;
	}

	/**
	 * コースが存在か判定します。
	 * @param courseName 判定するコース名
	 * @return コースが存在する場合はtrue。存在しない場合はfalse
	 */
	public boolean hasCourse(String courseName) {
		return courses.contains(new Course(courseName));
	}

	/**
	 * コースが存在か判定します。
	 * @param course 判定するコース名
	 * @return コースが存在する場合はtrue。存在しない場合はfalse
	 */
	public boolean hasCourse(Course course) {
		return courses.contains(course);
	}

	/**
	 * コースのリストを取得します。
	 * @return コースのリスト
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}

	/**
	 * チェックポイントを追加します。<br>
	 * このメソッドは、変数usingCourseのCourseにチェックポイントを追加します。<br>
	 * usingCourseがnullの場合はfalseを返します。
	 * @param checkpoint 追加するチェックポイント
	 */
	public void addCheckPoint(CheckPoint checkpoint) {
		usingCourse.addCheckPoint(checkpoint);
	}

	/**
	 * チェックポイントを追加します。
	 * @param course 追加先のコース
	 * @param checkpoint 追加するチェックポイント
	 */
	public void addCheckPoint(Course course, CheckPoint checkpoint) {
		course.addCheckPoint(checkpoint);
	}

	/**
	 * チェックポイントを削除します。<br>
	 * 戻り値は、{@link ArrayList#remove(Object)}メソッドの振る舞いです。
	 * @param checkpoint 削除したいチェックポイント
	 * @return {@link ArrayList#remove(Object)}の振る舞い
	 */
	public boolean removeCheckPoint(CheckPoint checkpoint) {
		return usingCourse.removeCheckPoint(checkpoint);
	}

	/**
	 * チェックポイントを削除します。<br>
	 * 戻り値は、{@link ArrayList#remove(Object)}メソッドの振る舞いです。
	 * @param course  削除先のコース
	 * @param checkpoint 削除したいチェックポイント
	 * @return {@link ArrayList#remove(Object)}の振る舞い
	 */
	public boolean removeCheckPoint(Course course, CheckPoint checkpoint) {
		return course.removeCheckPoint(checkpoint);
	}

	/**
	 * チェックポイントを削除します。
	 * @param index 削除したいチェックポイントのindex
	 * @return チェックポイントの中に指定されたindexと同じ値のチェックポイントがあれば削除し、true。見つからず削除出来なかった場合はfalse
	 */
	public boolean removeCheckPoint(int index) {
		return usingCourse.removeCheckPoint(index);
	}

	/**
	 * チェックポイントを削除します。
	 * @param course 削除先のコース
	 * @param index 削除したいチェックポイントのindex
	 * @return チェックポイントの中に指定されたindexと同じ値のチェックポイントがあれば削除し、true。見つからず削除出来なかった場合はfalse
	 */
	public boolean removeCheckPoint(Course course, int index) {
		return course.removeCheckPoint(index);
	}

	/**
	 * チェックポイントのリストの中で一番大きいindexの数値を返します。<br>
	 * リストに何もない場合は-1を返します。
	 * @param course コース
	 * @return チェックポイントのリストの中で一番大きいindex。リストに何もない場合は-1
	 */
	public int getMaxCheckPointIndex(Course course) {
		int index = -1;
		for (CheckPoint checkPoint : course.getCheckpoints()) {
			if (index < checkPoint.getIndex()) {
				index = checkPoint.getIndex();
			}
		}
		return index;
	}

	/**
	 * チェックポイントのリストの中で一番大きいindexの数値を返します。<br>
	 * リストに何もない場合は0を返します。
	 * @return チェックポイントのリストの中で一番大きいindex。リストに何もない場合は0
	 */
	public int getMaxCheckPointIndex() {
		int index = 0;
		for (CheckPoint checkPoint : usingCourse.getCheckpoints()) {
			if (index < checkPoint.getIndex()) {
				index = checkPoint.getIndex();
			}
		}
		return index;
	}

	/**
	 * 踏んだチェックポイントのIndexを返します。<br>
	 * 踏んだBlockがチェックポイントでない場合は-1を返します。
	 * @param location 踏んだBlockのLocation
	 * @return 踏んだチェックポイントのIndex。踏んだBlockがチェックポイントではない場合は-1
	 */
	public int getWalkedCheckPointIndex(SimpleLocation location) {
		for (Iterator<Course> iterator = courses.iterator(); iterator.hasNext();) {
			Course course = (Course) iterator.next();
			int walkedCheckPoint = course.getWalkedCheckPointIndex(location);
			if (walkedCheckPoint != -1) {
				return walkedCheckPoint;
			}
		}
		return -1;
	}

	/**
	 * 踏んだチェックポイントのコースを返します。<br>
	 * 踏んだBlockがチェックポイントでない場合はnullを返します。
	 * @param location 踏んだBlockのLocation
	 * @return 踏んだチェックポイントのコース。踏んだBlockがチェックポイントではない場合は-1
	 */
	public Course getWalkedCheckPointCourse(SimpleLocation location) {
		for (Iterator<Course> iterator = courses.iterator(); iterator.hasNext();) {
			Course course = (Course) iterator.next();
			int walkedCheckPoint = course.getWalkedCheckPointIndex(location);
			if (walkedCheckPoint != -1) {
				return course;
			}
		}
		return null;
	}

	/**
	 * 順位を表示するかを指定します。
	 * @param viewRank 順位を表示するか
	 */
	public void setViewRank(boolean viewRank) {
		this.viewRank = viewRank;
	}
	
	/**
	 * 順位を表示する場合はtrueを返します。
	 * @return 順位を表示する場合はtrue
	 */
	public boolean viewRank() {
		return viewRank;
	}
	
	/**
	 * スコアを追加します。<br>
	 * viewRankがtrueの時は表示されていないとき表示します。
	 * @param data 馬のデータ
	 * @param cpIndex 踏んだチェックポイントのIndex
	 */
	public void addScore(HorseData data, int cpIndex) {
		Player player = data.getPlayer();
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard.getObjective("MHR_Point") != null) {
			scoreboard.getObjective("MHR_Point").setDisplaySlot(DisplaySlot.SIDEBAR);
			scoreboard.getObjective("MHR_Point").getScore(player).setScore(cpIndex);
		} else if (viewRank) {
			scoreboard.registerNewObjective("MHR_Point", "MHR_Point");
			addScore(data, cpIndex);
		}
	}

	/**
	 * スコアをリセットします。
	 */
	public void resetScore() {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard.getObjective("MHR_Point") != null) {
			scoreboard.getObjective("MHR_Point").unregister();
			scoreboard.registerNewObjective("MHR_Point", "MHR_Point");
			scoreboard.getObjective("MHR_Point").setDisplaySlot(DisplaySlot.SIDEBAR);
		}
	}

}
