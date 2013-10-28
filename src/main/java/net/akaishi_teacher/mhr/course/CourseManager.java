package net.akaishi_teacher.mhr.course;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.course.data.Course;

public final class CourseManager {

	private ArrayList<Course> courses;

	private MHRCore mhrCore;

	private MHRCourse mhrCourse;

	public CourseManager(MHRCourse mhrCourse) {
		this.mhrCourse = mhrCourse;
		this.mhrCore = mhrCourse.getMHR();
	}

	public CourseManager(MHRCourse mhrCourse, ArrayList<Course> courses) {
		this(mhrCourse);
		this.courses = courses;
	}
	
	/**
	 * コースを追加します。<br>
	 * 既にコースが存在する(同じコース名のコース)場合は、追加されず、falseを返します。
	 * @param course 追加するコース
	 * @return コースが既に存在せず、正常に追加できた場合はtrue。既にコースが存在するもしくは正常に追加出来なかった場合はfalse
	 */
	public boolean addCourse(Course course) {
		if (courses.indexOf(course) == -1) {
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
	 * コースのリストを取得します。
	 * @return コースのリスト
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}

}
