package net.akaishi_teacher.mhr;

import java.util.Comparator;

public class ComparatorHorseDataId implements Comparator<HorseData> {

	@Override
	public int compare(HorseData o1, HorseData o2) {
		return o1.id < o2.id ? -1 : 1;
	}

}
