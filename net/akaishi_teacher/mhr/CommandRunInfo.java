package net.akaishi_teacher.mhr;

import org.bukkit.command.CommandSender;

public class CommandRunInfo {

	private String[] returnMessages;

	public CommandRunInfo(int returnMessagesBuffer) {
		if (returnMessagesBuffer != -1) {
			returnMessages = new String[returnMessagesBuffer];
		} else {
			returnMessages = new String[5];
		}
	}

	public void sendMessage(CommandSender sender) {
		for (String returnMessage : returnMessages) {
			if (returnMessage != null) {
				sender.sendMessage(returnMessage);
			}
		}
	}

	public String[] getReturnMessages() {
		return returnMessages;
	}

	public void setReturnMessages(String[] returnMessages) {
		this.returnMessages = returnMessages;
	}

	public void addReturnMessages(String... returnMessages) {
		int index = 0;
		while (true) {
			if (this.returnMessages[index] == null) {
				break;
			}
			index++;
		}
		for (String returnMessage : returnMessages) {
			this.returnMessages[index] = returnMessage;
			index++;
		}
	}

}