package net.akaishi_teacher.mhr.commands;

import net.akaishi_teacher.mhr.MHR;
import net.akaishi_teacher.util.command.AbstractCommand;

public abstract class MHRAbstractCommand extends AbstractCommand {

	protected MHR mhr;

	public MHRAbstractCommand(MHR mhr, String pattern, String permission,
			String description) {
		super(pattern, permission, description);
		this.mhr = mhr;
	}

}
