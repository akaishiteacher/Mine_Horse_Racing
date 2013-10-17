package net.akaishi_teacher.mhr.status;

import java.util.ArrayList;

/**
 * 馬のステータスが存在するクラスです。<br>
 * 共通のステータスや個別に保存されている値もここに存在します。
 * @author mozipi
 */
public class HorseStatus {

	/**
	 * 共通の馬のステータスが存在するクラス
	 */
	protected CommonHorseStatus status;

	/**
	 * 個別の馬のステータスが存在するクラスのリスト
	 */
	protected ArrayList<HorseData> horseDatas;

	public HorseStatus(ArrayList<HorseData> horseDatas, CommonHorseStatus status) {
		this.horseDatas = horseDatas;
		this.status = status;
	}

	public HorseStatus(ArrayList<HorseData> horseDatas, double speed, double jump) {
		this.horseDatas = horseDatas;
		this.status = new CommonHorseStatus(speed, jump);
	}

	/**
	 * {@link HorseData}クラスのリストを返します。
	 * @return {@link HorseData}クラスのリスト
	 */
	public ArrayList<HorseData> getHorseDatas() {
		return horseDatas;
	}

	/**
	 * {@link CommonHorseStatus}クラスのインスタンスを取得します。
	 * @return {@link CommonHorseStatus}クラスのインスタンス
	 */
	public CommonHorseStatus getCommonStatus() {
		return status;
	}

}
