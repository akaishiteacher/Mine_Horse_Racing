package net.akaishi_teacher.mhr.data;



public class CommonHorseStatus {

	/**
	 * 馬のスピード(共通)
	 */
	protected double speed;

	/**
	 * 馬のジャンプ力(共通)
	 */
	protected double jump;


	public CommonHorseStatus(double speed, double jump) {
		this.speed = speed;
		this.jump = jump;
	}

	/**
	 * 馬のスピード(共通)を取得します。
	 * @return 馬のスピード(共通)
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * 馬のスピード(共通)を設定します。<br>
	 * 上限値は5.0Dです。<br>
	 * これを超える場合、自動的に5に補正されます。
	 * @param speed 馬の速度(共通)
	 */
	public void setSpeed(double speed) {
		if (speed > 5.0D)
			this.speed = 5;
		else
			this.speed = speed;
	}

	/**
	 * 馬のジャンプ力(共通)を取得します。<br>
	 * @return 馬のジャンプ力(共通)
	 */
	public double getJump() {
		return jump;
	}

	/**
	 * 馬のジャンプ力(共通)を設定します。<br>
	 * 上限値はMinecraftの仕様上2.0Dです。<br>
	 * これを超える場合自動的に2に補正されます。
	 * @param jump 馬のジャンプ力(共通)
	 */
	public void setJump(double jump) {
		if (jump > 2.0D)
			this.jump = 2;
		else
			this.jump = jump;
	}

}
