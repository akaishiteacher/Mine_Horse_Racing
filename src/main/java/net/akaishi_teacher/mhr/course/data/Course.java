package net.akaishi_teacher.mhr.course.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Course implements ConfigurationSerializable {

	protected String courseName;
	
	protected int lap;
	
	protected int onelap;
	
	protected ArrayList<Area> checkpoints = new ArrayList<>();
	
	public Course(String courseName) {
		this.courseName = courseName;
	}

	public Course(String courseName, ArrayList<Area> checkpoints) {
		this(courseName);
		this.checkpoints = checkpoints;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Course(Map map) {
		this.courseName = (String) map.get("CourseName");
		this.lap = (int) map.get("Lap");
		this.onelap = (int) map.get("OneLap");
		this.checkpoints = (ArrayList<Area>) map.get("CheckPoints");
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("CourseName", courseName);
		map.put("Lap", lap);
		map.put("OneLap", onelap);
		map.put("CheckPoints", checkpoints);
		return map;
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

}
