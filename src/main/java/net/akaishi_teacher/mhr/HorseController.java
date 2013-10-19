package net.akaishi_teacher.mhr;

import net.akaishi_teacher.mhr.other.SimpleLocation;
import net.akaishi_teacher.mhr.status.HorseData;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
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
		Horse horse = spawn(loc);
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

	@Override
	public String getName() {
		return "MineHorseRacing";
	}

}
