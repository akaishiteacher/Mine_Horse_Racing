package net.akaishi_teacher.mhr.commands;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.util.command.AbstractCommand;

public abstract class MHRAbstractCommand extends AbstractCommand {

	/**
	 * プラグインのインスタンス
	 */
	protected MHR pl;

	public MHRAbstractCommand(MHR plugin, String pattern, String permission,
			String description) {
		super(pattern, permission, description);
		this.pl = plugin;
	}

}
