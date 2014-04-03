package net.akaishi_teacher.util.command;

import java.util.Comparator;

public class ComparatorCommandArgs_Reverse implements Comparator<AbstractCommand> {

	@Override
	public int compare(AbstractCommand o1, AbstractCommand o2) {
		int o1l = o1.getPattern().equals("") ? 0 : o1.getPattern().getPatternString().split(" ").length;
		int o2l = o2.getPattern().equals("") ? 0 : o2.getPattern().getPatternString().split(" ").length;
		return o1l > o2l ? 1 : -1;
	}

}
