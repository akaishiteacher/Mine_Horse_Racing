package net.akaishi_teacher.mhr.course;

import org.bukkit.Bukkit;

import net.akaishi_teacher.util.lang.Language;

public class SayCountdownMessage implements CountdownListener {

	protected Language language;

	public SayCountdownMessage(Language language) {
		this.language = language;
	}

	@Override
	public void start() {
		Bukkit.broadcastMessage(language.get("Message_Course.Countdown_Start"));
	}

	@Override
	public void end() {
		Bukkit.broadcastMessage(language.get("Message_Course.Countdown_End"));
	}

	@Override
	public void count(int now) {
		try {
			Bukkit.broadcastMessage(language.get("Message_Course.Countdown_Count-" + now));
		} catch (Exception e) {
		}
	}

}
