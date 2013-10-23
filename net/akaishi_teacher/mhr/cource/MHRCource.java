package net.akaishi_teacher.mhr.cource;

import java.util.ArrayList;

public final class MHRCource {

	private ArrayList<Cource> courceList;

	private String usingCourceName;

	private boolean isView;

	private boolean started;

	public void addCource(Cource cource) {
		courceList.add(cource);
	}

	public void removeCource(Cource cource) {
		courceList.remove(cource);
	}

	public void removeCource(String courceName) {
		courceList.add(new Cource(courceName));
	}

	public Cource getCource(String courceName) {
		int i = courceList.indexOf(courceName);
		if (i != -1) return courceList.get(i);
		else return null;
	}

	public void setUsingCource(String courseName) {
		usingCourceName = courseName;
	}

	public String getUsingCourceName() {
		return usingCourceName;
	}

	public boolean isView() {
		return isView;
	}

	public void setView(boolean isView) {
		this.isView = isView;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

}
