package net.akaishi_teacher.mhr;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.common.SimpleLocation;
import net.akaishi_teacher.mhr.data.CommonHorseStatus;
import net.akaishi_teacher.mhr.data.HorseData;

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

	/**
	 * サーバーを起動した時に呼び出すべき処理
	 * @param mhr MHRCoreのインスタンス
	 */
	public void serverInit(MHRCore mhr) {
		/*
		 * spawnListにインスタンス参照をバックアップしておかないと、
		 * horseDatasに新しいArrayListを代入すると、参照がなくなってしまうので、こういう処理になっています。
		 * HorseController#spawn(int, SimpleLocation)メソッドは、スポーンと同時に、horseDatasに
		 * 馬のデータを追加してしまうため、horseDatasを新しくする必要があったためです。
		 */
		//馬のデータリストのインスタンス参照をバックアップ
		ArrayList<HorseData> spawnList = horseDatas;
		//horseDatasを新しいArrayListにして、spawnメソッドの仕様を回避
		horseDatas = new ArrayList<>();
		for (int i = 0; i < spawnList.size(); i++) {
			HorseData data = spawnList.get(i);
			mhr.getController().spawn(data.id, data.loc);
		}
	}

	/**
	 * サーバーを終了する時に呼び出すべき処理
	 * @param mhr MHRCoreのインスタンス
	 */
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
