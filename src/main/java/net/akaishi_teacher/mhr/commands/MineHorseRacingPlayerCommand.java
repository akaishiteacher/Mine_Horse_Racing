package net.akaishi_teacher.mhr.commands;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * MinecraftHorseRacingPluginのコマンドを作るときに継承するクラスです。
 */

public class MineHorseRacingPlayerCommand {
	protected MineHorseRacingPlugin plugin;
	protected String name;
	protected String description;
	protected String argsFormat;
	protected String permission;

	public MineHorseRacingPlayerCommand(MineHorseRacingPlugin plugin, String name) {
		this.plugin = plugin;
		this.name = name;
		this.description = "";
		this.argsFormat = "";
		this.permission = "";
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getArgsFormat() {
		return this.argsFormat;
	}

	public boolean execute(Player sender, ArrayList<String> args) {
		return onCommand(sender, args);
	}

	protected boolean onCommand(Player sender, ArrayList<String> args) {

		return false;
	}
}
