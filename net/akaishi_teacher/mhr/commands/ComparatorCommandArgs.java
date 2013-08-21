package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.Comparator;

public class ComparatorCommandArgs implements Comparator<ArrayList<String>> {

	@Override
	public int compare(ArrayList<String> o1, ArrayList<String> o2) {
		return o1.size() < o2.size() ? 1 : -1;
	}

}
