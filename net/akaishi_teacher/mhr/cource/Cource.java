package net.akaishi_teacher.mhr.cource;

import java.util.ArrayList;

public class Cource {

	protected String courceName;

	protected ArrayList<CheckPoint> checkpointList;

	protected ArrayList<CourceRange> rangeList;

	protected int lapCount;

	public Cource(String courceName) {
		super();
		this.courceName = courceName;
	}

	public ArrayList<CheckPoint> getCheckpointList() {
		return checkpointList;
	}

	public void setCheckpointList(ArrayList<CheckPoint> checkpointList) {
		this.checkpointList = checkpointList;
	}

	public ArrayList<CourceRange> getRangeList() {
		return rangeList;
	}

	public void setRangeList(ArrayList<CourceRange> rangeList) {
		this.rangeList = rangeList;
	}

	public int getLapCount() {
		return lapCount;
	}

	public void setLapCount(int lapCount) {
		this.lapCount = lapCount;
	}

	public String getCourceName() {
		return courceName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((courceName == null) ? 0 : courceName.hashCode());
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
		Cource other = (Cource) obj;
		if (courceName == null) {
			if (other.courceName != null)
				return false;
		} else if (!courceName.equals(other.courceName))
			return false;
		return true;
	}

}
