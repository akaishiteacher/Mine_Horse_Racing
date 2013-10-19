package net.akaishi_teacher.mhr;

import java.util.ArrayList;
import java.util.Iterator;

import net.akaishi_teacher.mhr.other.SimpleLocation;
import net.akaishi_teacher.mhr.status.HorseData;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class HorseController implements AnimalTamer {

	/**
	 * MineHorseRacingPluginのインスタンス
	 */
	private MHR mhr;

	public HorseController(MHR mhr) {
		this.mhr = mhr;
	}

	/**
	 * 馬を複数スポーンします。<br>
	 * このメソッドは、MHRの馬として扱いません。MHRの馬として扱う場合は、{@link #spawns(int, int, SimpleLocation)}メソッドを利用してください。
	 * @param num スポーンさせる数
	 * @param loc スポーンさせる馬の位置
	 * @return スポーンした馬のインスタンス
	 */
	public Horse[] spawns(int num, SimpleLocation loc) {
		Horse[] horses = new Horse[num];
		for (int i = 0; i < horses.length; i++) {
			horses[i] = spawn(loc);
		}
		return horses;
	}

	/**
	 * 馬を複数スポーンします。<br>
	 * このメソッドは、馬をスポーンすると同時に、MHRの馬として扱います。
	 * @param baseId スポーンさせる馬のIDの基になる値
	 * @param num スポーンさせる数
	 * @param loc スポーンさせる馬の位置
	 * @return スポーンした馬のインスタンス
	 */
	public HorseData[] spawns(int baseId, int num, SimpleLocation loc) {
		HorseData[] horses = new HorseData[num];
		for (int i = 0; i < horses.length; i++) {
			horses[i] = spawn(baseId+i, loc);
		}
		return horses;
	}

	/**
	 * 馬をスポーンします。<br>
	 * このメソッドは、馬をスポーンすると同時に、MHRの馬として扱います。
	 * @param id スポーンさせる馬のID
	 * @param loc スポーンさせる馬の位置
	 * @return スポーンした馬を代入したHorseDataのインスタンス
	 */
	public HorseData spawn(int id, SimpleLocation loc) {
		HorseData data = new HorseData(id, loc);
		World world = mhr.getPlugin().getServer().getWorld(data.loc.worldName);
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				world.loadChunk((int) (data.loc.x / 16 + i), (int) (data.loc.z / 16 + j));
			}
		}
		Horse horse = spawn(loc);
		//Set horse status.
		horse.setStyle(Style.values()[(int) (Math.random() * Style.values().length)]);
		horse.setCustomName(String.valueOf(data.id+1));
		horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		horse.setOwner(this);
		data.horse = horse;
		mhr.getStatus().getHorseDatas().add(data);
		return data;
	}

	/**
	 * 馬をスポーンします。<br>
	 * このメソッドは、MHRの馬として扱いません。MHRの馬として扱う場合は、{@link #spawn(int, SimpleLocation)}メソッドを利用してください。
	 * @param loc スポーンさせる馬の位置
	 * @return スポーンした馬のインスタンス
	 */
	public Horse spawn(SimpleLocation loc) {
		World world = mhr.getPlugin().getServer().getWorld(loc.worldName);
		return (Horse) world.spawnEntity(new Location(world, loc.x, loc.y, loc.z), EntityType.HORSE);
	}

	/**
	 * 馬を複数デスポーンします。
	 * @param baseId デスポーンさせたい馬のIDの基となる値
	 * @param num デスポーンさせる数
	 */
	public void despawns(int baseId, int num) {
		ArrayList<HorseData> datas = mhr.getStatus().getHorseDatas();
		ArrayList<Integer> removeList = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			int index = datas.indexOf(new HorseData(baseId+i, null));
			if (index != -1) {
				//Remove horse.
				datas.get(index).horse.remove();
				//Add to the "remove list".
				removeList.add(datas.get(index).id);
			} else { //その番号の馬がいない時(翻訳できませんでした)
				i--;
				continue;
			}
		}

		for (Iterator<Integer> iterator = removeList.iterator(); iterator.hasNext();) {
			int index = datas.indexOf(new HorseData(iterator.next(), null));
			if (index != -1) {
				//Remove the "MHR horse".
				datas.remove(index);
			}
		}

		//Memory reduction.
		removeList = null;

		datas.trimToSize();
	}

	/**
	 * 馬をデスポーンします。
	 * @param id デスポーンさせたい馬のID
	 * @return 馬をデスポーンできた場合はtrueを返します。
	 */
	public boolean despawn(int id) {
		ArrayList<HorseData> datas = mhr.getStatus().getHorseDatas();
		int index = datas.indexOf(new HorseData(id, null));
		if (index != -1) {
			//Remove horse.
			datas.get(index).horse.remove();
			//Remove the "MHR horse".
			datas.remove(index);
		} else {
			return false;
		}

		datas.trimToSize();

		return true;
	}

	/**
	 * 馬をテレポートします。
	 * @param id テレポートする馬
	 * @param loc テレポート先
	 * @param flag 乗っている人もテレポートするか
	 * @return 馬をテレポートできた場合がtrueを返します。
	 */
	public boolean teleport(int id, SimpleLocation loc, boolean flag) {
		int index = mhr.getStatus().getHorseDatas().indexOf(new HorseData(id, null));
		if (index != -1) {
			Horse horse = mhr.getStatus().getHorseDatas().get(index).horse;
			Player passenger = (Player) horse.getPassenger();

			//Cast location.
			Location toLoc = new Location(horse.getWorld(), loc.x, loc.y, loc.z, (float) loc.yaw, (float) loc.pitch);

			//Eject passenger.
			horse.eject();

			//Teleport horse.
			horse.teleport(toLoc);
			if (flag) {
				//Teleport passenger.
				passenger.teleport(toLoc);
				//Ride on.
				horse.setPassenger(passenger);
			}
		} else {
			return false;
		}

		return true;
	}

	@Override
	public String getName() {
		return "MineHorseRacing";
	}

}
