package net.akaishi_teacher.mhr.course;

import org.bukkit.block.Block;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.common.Area;
import net.akaishi_teacher.mhr.common.SimpleLocation;
import net.akaishi_teacher.mhr.course.data.CheckPoint;
import net.akaishi_teacher.mhr.course.data.Course;
import net.akaishi_teacher.mhr.data.HorseData;

public final class CourseOutCheckProcess {

	private MHRCourse mhrCourse;

	private MHRCore mhr;

	public CourseOutCheckProcess(MHRCourse mhrCourse) {
		this.mhrCourse = mhrCourse;
		this.mhr = mhrCourse.getMHR();
	}

	public void check(Block block, HorseData data) {
		CourseManager manager = mhrCourse.getManager();
		SimpleLocation location = SimpleLocation.toSimpleLocation(block.getLocation());
		Course course = manager.getInAreaCourse(location);
		if (course != null) {
			SimpleLocation[] locations = course.getRange().expand(-2, -2, -2);
			Area area = new Area(locations[0], locations[1]);
			if (!area.isInArea(location)) {
				CheckPoint checkPoint = course.getCheckPoint(data.courseSession.getPoint());
				if (checkPoint != null) {
					double x = (checkPoint.getArea().p1.x + checkPoint.getArea().p2.x) / 2;
					double y = (checkPoint.getArea().p1.y + checkPoint.getArea().p2.y) / 2 + 3;
					double z = (checkPoint.getArea().p1.z + checkPoint.getArea().p2.z) / 2;
					mhr.getController().teleport(data.id, new SimpleLocation(location.worldName, x, y, z, checkPoint.getAngle(), 0), true);
				}
			}
		}
	}

}
