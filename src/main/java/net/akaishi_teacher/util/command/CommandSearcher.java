package net.akaishi_teacher.util.command;

import java.util.LinkedList;

/**
 * @author mozipi
 */
public class CommandSearcher {

	public static boolean search(String pattern, String[] commandArgs) {
		//Command that does not have command pattern.
		if (pattern.equals("")) {
			return true;
		}

		//Divided pattern.
		String[] dividedPattern = pattern.split(" ");

		//Arguments shortage.
		if (dividedPattern.length > commandArgs.length) {
			return false;
		}

		for (int i = 0; i < dividedPattern.length; i++) {
			if (dividedPattern[i].equalsIgnoreCase("any")) {
				continue;
			}
			if (dividedPattern[i].endsWith(":any")) {
				String startsWithStr = dividedPattern[i].split(":")[0];
				if (!commandArgs[i].startsWith(startsWithStr)) {
					return false;
				}
				continue;
			}
			if (dividedPattern[i].startsWith("any:")) {
				String endsWithStr = dividedPattern[i].split(":")[1];
				if (!commandArgs[i].endsWith(endsWithStr)) {
					return false;
				}
				continue;
			}
			if (!commandArgs[i].equalsIgnoreCase(dividedPattern[i])) {
				return false;
			}
		}
		return true;
	}

	public static boolean search_notAnys(String pattern, String[] commandArgs) {
		//Command that does not have command pattern.
		if (pattern.equals("")) {
			return true;
		}

		//List that removed the "any".
		LinkedList<String> s1 = new LinkedList<String>();
		LinkedList<String> s2 = new LinkedList<String>();

		//Divided pattern.
		String[] dividedPattern = pattern.split(" ");

		for (int i = 0; i < dividedPattern.length; i++) {
			if (!dividedPattern[i].equals("any")) {
				s1.add(dividedPattern[i]);
			} else break;
		}

		//Compare two list.
		if (commandArgs.length < s1.size()) return false;

		for (int i = 0; i < s1.size(); i++) {
			s2.add(commandArgs[i]);
		}

		for (int i = 0; i < s1.size(); i++) {
			if (!s1.get(i).equalsIgnoreCase(s2.get(i))) {
				return false;
			}
		}
		return true;
	}

}
