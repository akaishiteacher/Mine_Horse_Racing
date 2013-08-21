package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.akaishi_teacher.mhr.MHR;

/**
 * コマンドを実装するための抽象クラス
 * @author mozipi
 */
public abstract class AbstractCommand {

	protected String pattern;

	protected String permission;

	protected String description;

	protected MHR plugin;

	public AbstractCommand(MHR plugin,  String pattern, String permission,
			String description) {
		super();
		this.plugin = plugin;
		this.pattern = pattern;
		this.permission = permission;
		this.description = description;
	}

	public String getPattern() {
		return pattern;
	}

	public String getPermission() {
		return permission;
	}

	public String getDescription() {
		return description;
	}

	public boolean isMatch(ArrayList<String> args) {
		if (CommandSearcher.search(pattern, (String[]) args.toArray())) {
			return true;
		}
		return false;
	}

	public abstract boolean execute(CommandSender sender, ArrayList<String> args);

	public abstract String getUsage(CommandSender sender);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractCommand other = (AbstractCommand) obj;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		return true;
	}

}
