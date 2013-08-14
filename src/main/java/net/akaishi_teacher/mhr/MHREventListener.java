package net.akaishi_teacher.mhr;

import net.akaishi_teacher.mhr.area.Area;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.HorseInventory;


/**
 * いべんとりすなー
 */
public class MHREventListener implements Listener {
	private final MineHorseRacingPlugin plugin;

	public MHREventListener(MineHorseRacingPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityTame(EntityTameEvent event) {
		if (event.getEntity().getType().equals(EntityType.HORSE)) {
			Player player = (Player) event.getOwner();
			Horse horse = (Horse) event.getEntity();
			if (this.plugin.horses.containsValue(horse)) {
				// @TODO プラグイン管理下の馬がTameする時の処理
				int id = this.plugin.playersToOwnHorseIdList.get(player);
				if (id >= 1) {
					event.setCancelled(true);
					this.plugin.sendPluginMessage(player, "You already have own horse(ID: " + id + " )");
				} else {
					this.plugin.playersToOwnHorseIdList.put(player, this.plugin.getIndividualValue(horse));
					this.plugin.sendPluginMessage(player, "You tamed horse(ID: " + id + " )");
				}
			}
		}
	}

	@EventHandler
	public void onVehicleEnterEvent(VehicleEnterEvent event) {
		if ((event.getEntered().getType().equals(EntityType.PLAYER)) &&
				event.getVehicle().getType().equals(EntityType.HORSE)) {
			Player player = (Player) event.getEntered();
			Horse horse = (Horse) event.getVehicle();
			if (this.plugin.horses.containsValue(horse)) {
				if (!horse.getOwner().equals(player)) {
					event.setCancelled(true);
					this.plugin.sendPluginMessage(player, "あなたはおーなーではありません. (owner: " + horse.getOwner().getName() + " )");
				}
			}
		}
	}

	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent event) {
		if (event.getInventory() instanceof HorseInventory) {
			Player player = (Player) event.getPlayer();
			Horse horse = (Horse) event.getInventory().getHolder();

			if (!horse.getOwner().equals(player)) {
				event.setCancelled(true);
				this.plugin.sendPluginMessage(player, "あなたはおーなーではありません. (owner: " + horse.getOwner().getName() + " )");
			}
		}
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		if (event.getPlayer().getItemInHand().getType().getId() == this.plugin.magicWand) {
			if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				Player player = event.getPlayer();
				Location location = event.getClickedBlock().getLocation();
				this.plugin.sendPluginMessage(player, "Left clicked (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")");
				this.plugin.setSelectingAreaPos1(player, location);
				event.setCancelled(true);
			} else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				Player player = event.getPlayer();
				Location location = event.getClickedBlock().getLocation();
				this.plugin.sendPluginMessage(player, "Right clicked (" + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + ")");
				this.plugin.setSelectingAreaPos2(player, location);
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		if (!this.plugin.selectingArea.isEmpty()) {
			for (Area area : this.plugin.selectingArea.values()) {
				if (area.isInArea(event.getPlayer())) {
					this.plugin.sendPluginMessage(event.getPlayer(), "checkpoint");
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (this.plugin.selectingArea.containsKey(event.getPlayer())) {
			this.plugin.selectingArea.remove(event.getPlayer());
		}
	}
}
