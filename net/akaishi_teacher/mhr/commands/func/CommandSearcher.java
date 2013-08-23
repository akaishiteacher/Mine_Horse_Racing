package net.akaishi_teacher.mhr.commands.func;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author mozipi
 */
public class CommandSearcher {

	public static boolean search(String searchCommand, String[] commandArgs) {
		if (searchCommand.equals("")) {
			return true;
		}
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

	public static boolean search_notAnys(String searchCommand, String[] commandArgs) {
		if (searchCommand.equals("")) {
			return true;
		}
		LinkedList<String> s1 = new LinkedList<String>();
		LinkedList<String> s2 = new LinkedList<String>();
		String[] searchArgs = searchCommand.split(" ");
		for (int i = 0; i < searchArgs.length; i++) {
			if (!searchArgs[i].equals("any")) {
				s1.add(searchArgs[i]);
			} else break;
		}

		if (commandArgs.length < s1.size()) return false;
		for (int i = 0; i < s1.size(); i++) {
			s2.add(commandArgs[i]);
		}

		boolean flag = true;
		for (int i = 0; i < s1.size(); i++) {
			if (!s1.get(i).equalsIgnoreCase(s2.get(i))) {
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
