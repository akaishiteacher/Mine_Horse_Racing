package net.akaishi_teacher.mhr;

import java.util.Arrays;

public class CommandSearcher {

	public static boolean search(String searchCommand, String[] commandArgs) {
		String[] searchArgs = searchCommand.split(" ");
		if (searchArgs.length > commandArgs.length) {
			return false;
		}
		boolean flag = true;
		for (int i = 0; i < searchArgs.length; i++) {
			if (searchArgs[i].equalsIgnoreCase("any")) {
				continue;
			}
			if (searchArgs[i].endsWith(":any")) {
				String startsWithStr = searchArgs[i].split(":")[0];
				if (!commandArgs[i].startsWith(startsWithStr)) {
					flag = false;
				}
				continue;
			}
			if (searchArgs[i].startsWith("any:")) {
				String endsWithStr = searchArgs[i].split(":")[1];
				if (!commandArgs[i].endsWith(endsWithStr)) {
					flag = false;
				}
				continue;
			}
			if (!commandArgs[i].equalsIgnoreCase(searchArgs[i])) {
				flag = false;
			}
		}
		return flag;
	}

	public static String commandBond(String[] bondStrs) {
		String command = "";
		System.out.println(Arrays.toString(bondStrs));
		for (int i = 0; i < bondStrs.length; i++) {
			command = command + bondStrs[i] + " ";
		}
		return command;
	}

}
