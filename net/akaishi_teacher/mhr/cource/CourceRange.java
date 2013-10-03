package net.akaishi_teacher.mhr.cource;

import org.bukkit.Location;

public class CourceRange {

	private int number;

	private Location p1;

	private Location p2;

	public CourceRange(int number, Location p1, Location p2) {
		super();
		this.number = number;
		this.p1 = p1;
		this.p2 = p2;
	}

	public int getNumber() {
		return number;
	}

	public Location getP1() {
		return p1;
	}

	public Location getP2() {
		return p2;
	}

}
