package net.akaishi_teacher.util.command;

import java.util.LinkedList;

public class CommandPattern {

	protected final String pattern;

	public CommandPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * コマンドのパターン文字列を返します。
	 * @return コマンドのパターン文字列
	 */
	public String getPatternString() {
		return pattern;
	}

	public boolean match(String[] args) {
		//Command that does not have command pattern.
		if (pattern.equals("")) {
			return true;
		}

		//Divided pattern.
		String[] dividedPattern = pattern.split(" ");

		//Arguments shortage.
		if (dividedPattern.length > args.length) {
			return false;
		}

		for (int i = 0; i < dividedPattern.length; i++) {
			if (dividedPattern[i].equalsIgnoreCase("any")) {
				continue;
			}
			if (dividedPattern[i].endsWith(":any")) {
				String startsWithStr = dividedPattern[i].split(":")[0];
				if (!args[i].startsWith(startsWithStr)) {
					return false;
				}
				continue;
			}
			if (dividedPattern[i].startsWith("any:")) {
				String endsWithStr = dividedPattern[i].split(":")[1];
				if (!args[i].endsWith(endsWithStr)) {
					return false;
				}
				continue;
			}
			if (!args[i].equalsIgnoreCase(dividedPattern[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean match_notAnys(String[] args) {
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
		if (args.length < s1.size()) return false;

		for (int i = 0; i < s1.size(); i++) {
			s2.add(args[i]);
		}

		for (int i = 0; i < s1.size(); i++) {
			if (!s1.get(i).equalsIgnoreCase(s2.get(i))) {
				return false;
			}
		}
		return true;
	}

}
