package net.akaishi_teacher.mhr;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.common.SimpleLocation;

import org.bukkit.World;

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
	protected ArrayList<HorseData> horseDatas = new ArrayList<>();

	public HorseStatus(ArrayList<HorseData> horseDatas, CommonHorseStatus status) {
		this.horseDatas = horseDatas;
		this.status = status;
	}

	public HorseStatus(ArrayList<HorseData> horseDatas, double speed, double jump) {
		this.horseDatas = horseDatas;
		this.status = new CommonHorseStatus(speed, jump);
	}

	public HorseStatus(ArrayList<HorseData> horseDatas, double speed, double jump, boolean flag) {
		if (flag) {
			this.horseDatas = horseDatas;
		}
		this.status = new CommonHorseStatus(speed, jump);
	}

	public void serverInit(MHRCore mhr, ArrayList<HorseData> datas) {
		for (int i = 0; i < datas.size(); i++) {
			HorseData data = datas.get(i);
			mhr.getController().spawn(data.id, data.loc);
		}
	}

	public void serverEnd(MHRCore mhr) {
		for (HorseData data : horseDatas) {
			World world = mhr.getPlugin().getServer().getWorld(data.loc.worldName);
			data.loc = SimpleLocation.toSimpleLocation(data.horse.getLocation());
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					world.loadChunk((int) (data.loc.x / 16 + i), (int) (data.loc.z / 16 + j));
				}
			}
			data.horse.remove();
		}
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
