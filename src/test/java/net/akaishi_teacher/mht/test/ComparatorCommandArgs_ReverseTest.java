package net.akaishi_teacher.mht.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import net.akaishi_teacher.util.command.AbstractCommand;
import net.akaishi_teacher.util.command.ComparatorCommandArgs;
import net.akaishi_teacher.util.command.ComparatorCommandArgs_Reverse;

import org.bukkit.command.CommandSender;

/**
 * Comparatorが正常動作するかテストするためのテストケース
 * @author mozipi
 */
public class ComparatorCommandArgs_ReverseTest extends TestCase {

	public ComparatorCommandArgs_ReverseTest(String name) {
		super(name);
	}

	public void test() {
		AbstractCommand ac1 = new AbstractCommand("", "b", "") {
			@Override
			public String getUsage(CommandSender sender) {
				return null;
			}

			@Override
			public boolean execute(CommandSender sender, ArrayList<String> args) {
				return false;
			}
		};

		AbstractCommand ac2 = new AbstractCommand("", "b", "") {
			@Override
			public String getUsage(CommandSender sender) {
				return null;
			}

			@Override
			public boolean execute(CommandSender sender, ArrayList<String> args) {
				return false;
			}
		};

		ComparatorCommandArgs c = new ComparatorCommandArgs();
		int cindex = c.compare(ac1, ac2);
		assertEquals(cindex, -1);

		ComparatorCommandArgs_Reverse cr = new ComparatorCommandArgs_Reverse();
		int crindex = cr.compare(ac1, ac2);
		assertEquals(crindex, -1);

	}

}
