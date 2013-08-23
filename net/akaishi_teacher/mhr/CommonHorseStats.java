package net.akaishi_teacher.mhr;

/**
 * 馬のステータスを保存するクラスです。
 * @author mozipi
 */
public class CommonHorseStats {

	/**
	 * 馬の速度
	 */
	protected double speed;

	/**
	 * 馬のジャンプ力
	 */
	protected double jump;

	public CommonHorseStats(double speed, double jump) {
		super();
		this.speed = speed;
		this.jump = jump;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getJump() {
		return jump;
	}

	public void setJump(double jump) {
		this.jump = jump;
	}

}
