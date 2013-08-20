package net.akaishi_teacher.mhr;

import java.util.UUID;

import org.bukkit.entity.Horse;

/**
 * 馬の情報が格納されているクラス
 * @author mozipi
 */
public class HorseInfo {

	protected UUID uuid;

	protected double x;

	protected double y;

	protected double z;

	protected double jumpStrength;

	protected double speed;

	protected Horse horse;

	protected int number;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getJumpStrength() {
		return jumpStrength;
	}

	public void setJumpStrength(double jumpStrength) {
		this.jumpStrength = jumpStrength;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Horse getHorse() {
		return horse;
	}

}
