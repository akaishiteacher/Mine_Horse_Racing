package net.akaishi_teacher.mhr.commands;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * 馬をテレポートさせるコマンドです。
 * <p/>
 * "/command name [個体番号]"
 */
public class TpCommand extends MineHorseRacingPlayerCommand {

	public TpCommand(MineHorseRacingPlugin plugin, String name) {
		super(plugin, name);
		this.description = "馬をですぽーん";
		this.argsFormat = "[個体番号]";
	}

	@Override
	protected boolean onCommand(Player sender, ArrayList<String> args) {
		this.plugin.getLogger().info("despawn args.length " + args.size());
		if (1 <= args.size() && args.size() <= 3) {
			int individualValue = Integer.parseInt(args.remove(0));
			Player player = sender;
			boolean tpHorseWithPlayerFlag = false;

			Horse horse = this.plugin.findHorseByID(individualValue);
			if (horse == null) {
				sender.sendMessage("No such a Horse.");
				return true;
			}
			if (!args.isEmpty()) {
				Player objectPlayer = this.plugin.getServer().getPlayer(args.remove(0));
				if (objectPlayer != null) {
					player = objectPlayer;
				}
			}
			if (!args.isEmpty()) {
				String arg = args.remove(0);
				tpHorseWithPlayerFlag = Boolean.parseBoolean(arg);
			}

			// ---------- TP ----------
			if (horse.getPassenger() == null || tpHorseWithPlayerFlag) {
				horse.teleport(player);
				this.plugin.getLogger().info("tp Horse(" + individualValue + ") to " + player.getName());
			}
			this.plugin.getLogger().info("tped?");
			return true;
		}
		return false;
	}
}
