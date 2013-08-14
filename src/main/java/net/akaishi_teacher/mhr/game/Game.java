package net.akaishi_teacher.mhr.game;

import net.akaishi_teacher.mhr.area.Course;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * ゲーム管理クラスの予定
 */
public class Game {
	protected Course course;
	protected ArrayList<Player> players;
	protected GameStatus status;
}
