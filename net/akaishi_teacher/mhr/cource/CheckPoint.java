package net.akaishi_teacher.mhr.cource;

import org.bukkit.Location;

public class CheckPoint {

	protected int number;

	protected Location p1;

	protected Location p2;

	protected double yaw;

	public CheckPoint(int number, Location p1, Location p2) {
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

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
	}

}
