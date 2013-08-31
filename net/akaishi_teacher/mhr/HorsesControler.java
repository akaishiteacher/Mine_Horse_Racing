package net.akaishi_teacher.mhr;

import java.util.ArrayList;

import net.minecraft.server.v1_6_R2.Item;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

/**
 * 馬をコントロールするクラスです。
 * @author mozipi
 */
public class HorsesControler {

	/**
	 * 馬の情報を持ったクラスのArrayList
	 */
	protected ArrayList<HorseInfo> horseInfoList = new ArrayList<HorseInfo>();

	protected MHR plugin;

	public HorsesControler(MHR plugin) {
		this.plugin = plugin;
	}

	/**
	 * 馬を指定した数だけスポーンさせます。<br>
	 * ただし、馬が足りない場合は足りない馬を含めスポーンさせます。
	 * @param num スポーンさせる馬の数
	 */
	public void spawnHorses(int num, Location spawnLoc) {
		horseInfoList.trimToSize();
		while (num > 0) {
			//足りない馬の補充。パフォーマンスが悪いのは許して
			for (int i = 0; i < horseInfoList.size(); i++) {
				HorseInfo info = horseInfoList.get(i);
				if (info.getHorse().isDead()) {
					Location loc = info.getHorse().getLocation();
					Horse horse = (Horse) info.getHorse().getWorld().spawn(loc, Horse.class);
					horse.setTamed(true);
					horse.setAge(Integer.MAX_VALUE);
					horse.getInventory().setItem(0, new ItemStack(Item.SADDLE.id));
					horse.setCustomName(String.valueOf(info.getNumber()));
					horse.setCustomNameVisible(true);
					HorseInfo newInfo = new HorseInfo();
					newInfo.setHorse(horse);
					newInfo.setNumber(info.getNumber());
					horseInfoList.set(i,
							newInfo);
					num--;
					continue;
				}
			}
			//馬の追加スポーン
			int a = horseInfoList.size();
			for (int i = 0; i < num; i++) {
				Horse horse = spawnLoc.getWorld().spawn(spawnLoc, Horse.class);
				horse.setTamed(true);
				horse.setAge(Integer.MAX_VALUE);
				horse.setCustomNameVisible(true);
				horse.getInventory().setItem(0, new ItemStack(Item.SADDLE.id));
				HorseInfo info = new HorseInfo();
				info.setHorse(horse);
				info.setNumber(a + i + 1);
				horse.setCustomName(String.valueOf(info.getNumber()));
				horseInfoList.add(info);
			}
			break;
		}
	}



	/**
	 * horseInfoListの馬をすべてデスポーンさせます。
	 */
	public void despawnHorse() {
		for (HorseInfo info : horseInfoList) {
			loadChunks(info);
			info.getHorse().remove();
		}
	}


	/**
	 * 指定した馬をデスポーンさせます。
	 * @param num デスポーンさせる馬の個体番号
	 */
	public void despawnHorse(int num) {
		for (HorseInfo info : horseInfoList) {
			if (info.getNumber() == num) {
				loadChunks(info);
				info.getHorse().remove();
			}
		}
	}



	/**
	 * すべての馬を引数locの場所にテレポートします。<br>
	 * flagがtrueの場合は乗っている人もテレポートします。
	 * @param locテレポート先
	 * @param flag 乗っている人もテレポートするかどうか
	 */
	public void alltp(Location loc, boolean flag) {
		for (HorseInfo info : horseInfoList) {
			Horse horse = info.getHorse();
			Entity rider = horse.getPassenger();
			if (rider != null && flag) {
				horse.eject();
			}
			horse.teleport(loc);
			if (rider != null && flag) {
				rider.teleport(loc);
				horse.setPassenger(rider);
			}
		}
	}



	/**
	 * 馬をテレポートします。
	 * @param num テレポートする馬の個体番号
	 * @param loc テレポート先
	 * @param flag 乗っている人もテレポートするかどうか
	 */
	public void tp(int num, Location loc, boolean flag) {
		for (HorseInfo info : horseInfoList) {
			if (info.getNumber() == num) {
				Horse horse = info.getHorse();
				Entity rider = horse.getPassenger();
				if (rider != null && flag) {
					horse.eject();
				}
				horse.teleport(loc);
				if (rider != null && flag) {
					rider.teleport(loc);
					horse.setPassenger(rider);
				}
			}
		}
	}



	/**
	 * HorseInfoの情報を元にし、馬をスポーンさせ、isDeadがtrueの場合は馬をHorse.removeメソッドを呼び出します。
	 */
	public void init() {
		for (HorseInfo info : horseInfoList) {
			loadChunks(info);
		}
		for (World world : plugin.getServer().getWorlds()) {
			for (Entity entity : world.getEntitiesByClasses(Horse.class)) {
				Horse horse = (Horse) entity;
				if (horse.getCustomName() != null) {
					try {
						Integer.parseInt(horse.getCustomName());
						horse.remove();
					} catch (NumberFormatException e) {
						continue;
					}
				}
			}
		}
		for (HorseInfo info : horseInfoList) {
			World world = plugin.getServer().getWorlds().get(info.getDimID());
			double x = info.getX();
			double y = info.getY();
			double z = info.getZ();
			Location loc = new Location(world, x, y, z);
			loadChunks(info);
			Horse horse = world.spawn(loc, Horse.class);
			horse.setTamed(true);
			horse.setAge(Integer.MAX_VALUE);
			horse.setCustomName(String.valueOf(info.number));
			horse.setCustomNameVisible(true);
			horse.getInventory().setItem(0, new ItemStack(Item.SADDLE.id));
			info.setHorse(horse);
			if (info.isDead()) {
				horse.remove();
			}
		}
		plugin.getServer().broadcastMessage(plugin.getLang().getLocalizedString("Cmd_UseOK"));
	}



	public ArrayList<HorseInfo> getHorseInfoList() {
		return horseInfoList;
	}



	public void setHorseInfoList(ArrayList<HorseInfo> horseInfoList) {
		this.horseInfoList = horseInfoList;
	}

	protected void loadChunks(HorseInfo info) {
		World world = plugin.getServer().getWorlds().get(info.getDimID());
		int chunkX = (int) (info.getX() / 16);
		int chunkZ = (int) (info.getZ() / 16);
		if (!world.isChunkInUse(chunkX, chunkZ)) {
			world.loadChunk(chunkX*1, chunkZ, true);
			world.loadChunk(chunkX, chunkZ+1, true);
			world.loadChunk(chunkX-1, chunkZ, true);
			world.loadChunk(chunkX, chunkZ-1, true);
			world.loadChunk(chunkX+1, chunkZ+1, true);
			world.loadChunk(chunkX-1, chunkZ-1, true);
			world.loadChunk(chunkX+1, chunkZ-1, true);
			world.loadChunk(chunkX-1, chunkZ+1, true);
			world.loadChunk(chunkX, chunkZ, true);
		}
	}

}
