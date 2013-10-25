package net.akaishi_teacher.mhr.commands;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.util.command.AbstractCommand;

/**
 * MHRのAbstractCommandです。
 * @author mozipi
 */
public abstract class MHRAbstractCommand extends AbstractCommand {

	protected MHRCore mhr;

	public MHRAbstractCommand(MHRCore mhr, String pattern, String permission,
			String description) {
		super(pattern, permission, description);
		this.mhr = mhr;
	}

}
