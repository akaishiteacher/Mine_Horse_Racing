package net.akaishi_teacher.mhr.commands;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * サンプルコマンドクラス
 */
public class SampleCommand extends MineHorseRacingPlayerCommand {
	public SampleCommand(MineHorseRacingPlugin plugin, String name) {
		super(plugin, name);
		// helpで表示するためのコマンドの説明。未実装
		this.description = "さんぷるこまんど";
		// helpでの表示用。コマンドの引数の渡し方。制限は別途必要。
		// this.argsFormat = "(arg1) [arg2] [arg3] ..." とするとhelpコマンドにて
		// /commands sample (arg1) [arg2] [arg3] と表示されます。
		this.argsFormat = "(arg1) [arg2] [arg3] ...";
	}

	@Override
	protected boolean onCommand(Player sender, ArrayList<String> args) {
		if (1 <= args.size() && args.size() <= 3) {
			// 引数の長さを 1 〜 3 に制限

			// 〜〜
			this.plugin.getLogger().info("Executed sample-command.");
			sender.sendMessage("Executed sample-command.");

			return true;
		}
		return false; // 実行条件に合致しなかった場合 false を返す
	}
}
