package net.akaishi_teacher.mhr.command;

import net.akaishi_teacher.mhr.MineHorseRacingPlugin;

/**
 * 親コマンドのサンプル
 * <p/>
 * "/command field add [args]" のようなコマンドを作る時に利用する
 */
public final class SampleParentCommand extends BaseParentCommand {
	public SampleParentCommand(MineHorseRacingPlugin plugin, String label) {
		super(plugin, label);

		this.addCommand(new SampleChildCommand(this.plugin, "child1"));
		this.addCommand(new SampleChildCommand(this.plugin, "child2"));
		this.addCommand(new SampleChildCommand(this.plugin, "child3"));
	}
}
