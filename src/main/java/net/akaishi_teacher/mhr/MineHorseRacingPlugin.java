package net.akaishi_teacher.mhr;

import net.akaishi_teacher.mhr.area.Area;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * MineHorseRacingPlugin
 *
 * @version 0.1.0
 */
public class MineHorseRacingPlugin extends JavaPlugin {
	/**
	 * 個体番号 -> 馬
	 */
	protected HashMap<Integer, Horse> horses = new HashMap<>();
	/**
	 * プレイヤー -> 保持している馬
	 */
	protected HashMap<Player, Integer> playersToOwnHorseIdList = new HashMap<>();
	/**
	 * プレイヤー -> stickで選択しているエリア
	 */
	protected HashMap<Player, Area> selectingArea = new HashMap<>();
	/**
	 * エリアの選択に使うアイテム
	 */
	protected int magicWand = Material.STICK.getId();

	/**
	 * 乱数生成用の共有インスタンス
	 */
	public final Random RANDOM = new Random();

	/**
	 * プラグインが有効化された時に実行される
	 */
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new MHREventListener(this), this);
		getCommand("minehorseracing").setExecutor(new MHRCommandExecutor(this));

		reloadHorsesList();

		getLogger().info("Enabled " + this.getName());

	}

	/**
	 * 指定されたプレイヤーの場所に馬をスポーンする
	 *
	 * @param player スポーン先のプレイヤー
	 * @return 馬の個体番号
	 */
	public int spawnHorse(Player player) {
		return spawnHorse(player.getLocation());
	}

	/**
	 * 指定されば場所に馬をスポーンする
	 *
	 * @param location スポーン先の場所
	 * @return 馬の個体番号
	 */
	public int spawnHorse(Location location) {
		Horse horse = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
		int individualValue = addToHorsesList(horse);
		horse.setStyle(Horse.Style.values()[RANDOM.nextInt(Horse.Style.values().length)]);
		horse.setColor(Horse.Color.values()[RANDOM.nextInt(Horse.Color.values().length)]);
		horse.setRemoveWhenFarAway(false);
		// debug用に個体値を名前に設定。
		horse.setCustomName(String.valueOf(individualValue));
		horse.setCustomNameVisible(true);

		// @TODO 能力値の変更
		return individualValue;
	}

	/**
	 * 馬に個体番号を付与し、プラグインの管理下に置く
	 *
	 * @param horse プラグイン管理下にない、個体番号の付与されていない馬。
	 * @return 馬の個体番号
	 */
	public int addToHorsesList(Horse horse) {
		// IDの衝突確率が十分に低いので、衝突回避するまで乱数でID生成。
		// 大規模に対応するために要変更。
		int individualValue;
		do {
			int MAX_INDIVIDUAL_VALUE = Integer.MAX_VALUE;
			individualValue = RANDOM.nextInt(MAX_INDIVIDUAL_VALUE - 1) + 1;
		} while (this.horses.containsKey(individualValue));

		horse.setMetadata("individualValue", new FixedMetadataValue(this, individualValue));
		this.horses.put(individualValue, horse);
		return individualValue;
	}

	/**
	 * 指定した馬をデスポーンする
	 *
	 * @param horse デスポーンする馬
	 */
	public void removeHorse(Horse horse) {
		this.horses.remove(this.getIndividualValue(horse));
		horse.remove();
	}

	/**
	 * 指定した個体番号を持つ馬をデスポーンする
	 *
	 * @param individualValue デスポーンする馬の個体番号
	 */
	public void removeHorse(int individualValue) {
		removeHorse(getHorse(individualValue));
	}

	/**
	 * 指定された個体番号の馬を取得する
	 *
	 * @param individualValue 取得する馬の個体番号
	 * @return horse
	 */
	public Horse getHorse(int individualValue) {
		return this.horses.get(individualValue);
	}

	/**
	 * 馬の個体番号を取得する
	 *
	 * @param horse 馬
	 * @return 取得に成功した場合は個体番号を、失敗した場合は0を返す
	 */
	public int getIndividualValue(Horse horse) {
		if (horse.hasMetadata("individualValue")) {
			List<MetadataValue> metadataList = horse.getMetadata("individualValue");
			int n = 0;
			if (metadataList.size() > 0) {
				n = metadataList.get(0).asInt();
			}
			return n;
		}
		return 0;
	}

	/**
	 * プラグイン管理下の馬を全てデスポーンする
	 */
	public void purgeHorses() {
		reloadHorsesList();
		for (Iterator<Map.Entry<Integer, Horse>> it = this.horses.entrySet().iterator(); it.hasNext(); ) {
			Horse horse = it.next().getValue();
			removeHorse(horse);
		}
	}

	/**
	 * プラグイン管理下の馬を再構成する
	 */
	public void reloadHorsesList() {
		this.horses.putAll(detectHorsesCreatedByPluginInServer());
	}

	/**
	 * マップデータからプラグイン管理下の馬を検出する
	 *
	 * @return 検出された馬のリスト
	 */
	public HashMap<Integer, Horse> detectHorsesCreatedByPluginInServer() {
		HashMap<Integer, Horse> ret = new HashMap<>();
		for (World world : this.getServer().getWorlds()) {
			for (Entity entity : world.getLivingEntities()) {
				if (entity.getType().equals(EntityType.HORSE) && entity instanceof Horse) {
					if (entity.hasMetadata("individualValue")) {
						List<MetadataValue> metadataList = entity.getMetadata("individualValue");
						if (metadataList.size() > 0) {
							int n = metadataList.get(0).asInt();
							ret.put(n, (Horse) entity);
							getLogger().info("Detected Horse(ID: " + n + ")");
							this.playersToOwnHorseIdList.put((Player) (((Horse) entity).getOwner()), n);
						}
					}
				}
			}
		}
		return ret;
	}

	/**
	 * 馬を指定されたプレイヤーの場所にテレポートする
	 *
	 * @param individualValue テレポートする馬の個体番号
	 * @param jumpTo          テレポート先のプレイヤー
	 */
	public void tpHorse(int individualValue, Player jumpTo) {
		tpHorse(individualValue, jumpTo.getLocation());
	}

	/**
	 * 馬を指定されたプレイヤーの場所にテレポートする
	 *
	 * @param individualValue テレポートする馬の個体番号
	 * @param jumpTo          テレポート先のプレイヤー
	 * @param tpWithPlayer    馬にEntityが乗っていてもテレポートする場合はtrue
	 */
	public void tpHorse(int individualValue, Player jumpTo, boolean tpWithPlayer) {
		tpHorse(individualValue, jumpTo.getLocation(), tpWithPlayer);
	}

	/**
	 * 馬を指定された場所にテレポートする
	 *
	 * @param individualValue テレポートする馬の個体番号
	 * @param location        テレポート先
	 */
	public void tpHorse(int individualValue, Location location) {
		tpHorse(individualValue, location, false);
	}

	/**
	 * 馬を指定された場所にテレポートする
	 *
	 * @param individualValue テレポートする馬の個体番号
	 * @param location        テレポート先
	 * @param tpWithPlayer    馬にEntityが乗っていてもテレポートする場合true
	 */
	public void tpHorse(int individualValue, Location location, boolean tpWithPlayer) {
		Horse horse = getHorse(individualValue);
		if (horse == null) return;
		Entity passenger = horse.getPassenger();
		if (passenger == null) {
			horse.teleport(location);
		} else if (tpWithPlayer) {
			passenger.leaveVehicle();
			horse.teleport(location);
			horse.setPassenger(passenger);
		}
	}

	/**
	 * プラグイン管理下にある全ての馬を指定されたプレイヤーの場所にテレポートする
	 *
	 * @param jumpTo テレポート先のプレイヤー
	 */
	public void tpAllHorse(Player jumpTo) {
		tpAllHorse(jumpTo.getLocation());
	}

	/**
	 * プラグイン管理下にある全ての馬を指定されたプレイヤーの場所にテレポートする
	 *
	 * @param jumpTo       テレポート先のプレイヤー
	 * @param tpWithPlayer 馬にEntityが乗っていてもテレポートする場合はtrue
	 */
	public void tpAllHorse(Player jumpTo, boolean tpWithPlayer) {
		tpAllHorse(jumpTo.getLocation(), tpWithPlayer);
	}

	/**
	 * プラグイン管理下にある全ての馬を指定された場所にテレポートする
	 *
	 * @param location テレポート先の場所
	 */
	public void tpAllHorse(Location location) {
		tpAllHorse(location, false);
	}

	/**
	 * プラグイン管理下にあるすべての馬を指定された場所にテレポートする
	 *
	 * @param location     テレポート先の場所
	 * @param tpWithPlayer 馬にEntityが乗っていてもてレポートする場合はtrue
	 */
	public void tpAllHorse(Location location, boolean tpWithPlayer) {
		for (int id : this.horses.keySet()) {
			tpHorse(id, location, tpWithPlayer);
		}
	}

	/**
	 * コマンド送信元にプラグインprefixをつけたメッセージを送る。
	 *
	 * @param commandSender コマンド送信元
	 * @param text          送信するメッセージ
	 */
	public void sendPluginMessage(CommandSender commandSender, String text) {
		commandSender.sendMessage("[MHR] " + text);
	}

	/**
	 * エリアの始点を設定する
	 *
	 * @param player   エリアを選択しているプレイヤー
	 * @param location エリアの始点
	 */
	public void setSelectingAreaPos1(Player player, Location location) {
		if (!this.selectingArea.containsKey(player)) {
			this.selectingArea.put(player, new Area());
		}
		this.selectingArea.get(player).setPos1(location);
	}

	/**
	 * エリアの終点を設定する
	 *
	 * @param player   エリアを選択しているプレイヤー
	 * @param location エリアの終点
	 */
	public void setSelectingAreaPos2(Player player, Location location) {
		if (!this.selectingArea.containsKey(player)) {
			this.selectingArea.put(player, new Area());
		}
		this.selectingArea.get(player).setPos2(location);
	}
}
