package net.akaishi_teacher.mhr.area;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * 待合室とか部屋用
 */
public class Room extends CheckPoint {
	protected ArrayList<Player> players;

	public Room addPlayer(Player player) {
		players.add(player);
		return this;
	}

	public Room removePlayer(Player player) {
		players.remove(player);
		return this;
	}

	public Room tpToThisRoom(Player player) {
		player.teleport(this.spawnPoint);
		addPlayer(player);
		return this;
	}

	public Room tpFromThisRoom(Player player, Player tpTo) {
		tpFromThisRoom(player, tpTo.getLocation());
		return this;
	}

	public Room tpFromThisRoom(Player player, Location location) {
		player.teleport(location);
		removePlayer(player);
		return this;
	}

	public Room tpToOtherRoom(Player player, Room room) {
		room.tpToThisRoom(player);
		return this;
	}
}
