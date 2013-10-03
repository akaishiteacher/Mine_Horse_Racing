package net.akaishi_teacher.util.command;

import java.util.Comparator;

public class ComparatorCommandArgs implements Comparator<AbstractCommand> {

	@Override
	public int compare(AbstractCommand o1, AbstractCommand o2) {
		return o1.getPattern().split(" ").length < o2.getPattern().split(" ").length ? 1 : -1;
	}

}
