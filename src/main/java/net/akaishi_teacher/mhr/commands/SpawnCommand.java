package net.akaishi_teacher.mhr.commands;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * 馬をスポーンするコマンド
 * <p/>
 * "/command name (馬の数)"
 */
public class SpawnCommand extends MineHorseRacingPlayerCommand {
	private static final int HORSE_COLOR_LENGTH = Horse.Color.values().length;
	private static final int HORSE_STYLE_LENGTH = Horse.Style.values().length;
	private static final Random RANDOM = new Random();

	public SpawnCommand(MineHorseRacingPlugin plugin, String name) {
		super(plugin, name);
		this.description = "馬をすぽーん";
		this.argsFormat = "(馬の数)";
	}

	@Override
	protected boolean onCommand(Player sender, ArrayList<String> args) {
		if (args.size() == 1) {
			int n;
			try {
				n = Integer.parseInt(args.get(0));
			} catch (NumberFormatException e) {
				return false;
			}
			for (int i = 0; i < n; ++i) {
				Horse horse = (Horse) sender.getWorld().spawnEntity(sender.getLocation(), EntityType.HORSE);
				horse.setColor(Horse.Color.values()[RANDOM.nextInt(HORSE_COLOR_LENGTH)]);
				horse.setStyle(Horse.Style.values()[RANDOM.nextInt(HORSE_STYLE_LENGTH)]);

				// @TODO 個体値の設定
				int individualValue = this.plugin.addHorse(horse);
				sender.sendMessage("spawned Horse(ID: " + individualValue + " )");
				horse.setCustomName(String.valueOf(individualValue));
				horse.setCustomNameVisible(true);

			}
			this.plugin.getLogger().info(sender.getName() + "spawned " + n + " horse(s) : [" + sender.getName() + "]" + sender.getLocation().toVector().toString());
			sender.sendMessage("Spawned " + n + " horse(s).");
			return true;
		}
		return false;
	}
}
