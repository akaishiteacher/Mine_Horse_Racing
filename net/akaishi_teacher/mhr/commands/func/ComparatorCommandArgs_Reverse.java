package net.akaishi_teacher.mhr.commands.func;

import java.util.Comparator;

public class ComparatorCommandArgs_Reverse implements Comparator<AbstractCommand> {

	@Override
	public int compare(AbstractCommand o1, AbstractCommand o2) {
		int o1l = o1.getPattern().equals("") ? 0 : o1.getPattern().split(" ").length;
		int o2l = o2.getPattern().equals("") ? 0 : o2.getPattern().split(" ").length;
		return o1l > o2l ? 1 : -1;
	}

}
