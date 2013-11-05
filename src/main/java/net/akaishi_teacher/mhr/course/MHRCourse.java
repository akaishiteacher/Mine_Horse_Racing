package net.akaishi_teacher.mhr.course;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.ConfigurationForData;
import net.akaishi_teacher.mhr.Deserializer;
import net.akaishi_teacher.mhr.HorseData;
import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.MHRFunc;
import net.akaishi_teacher.mhr.Main;
import net.akaishi_teacher.mhr.course.commands.Add;
import net.akaishi_teacher.mhr.course.commands.AddCheckPoint;
import net.akaishi_teacher.mhr.course.commands.Remove;
import net.akaishi_teacher.util.command.CommandExecutor;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

/**
 * MineHorseRacingのコース機能の処理部です。
 * @author mozipi
 */
public final class MHRCourse extends MHRFunc implements Deserializer, HorseEvent {

	/**
	 * MHRCoreクラスのインスタンス
	 */
	private MHRCore mhr;

	/**
	 * 馬が歩いているブロックを取得し、walkメソッドを呼び出すスケジュールタスク
	 */
	private CheckWalkingThread checkWalkingThread;
	
	/**
	 * 馬が歩いたイベントの処理をするクラス
	 */
	private WalkEvent walkEvent;
	
	/**
	 * コース機能を操作するマネージャークラスのインスタンス
	 */
	private CourseManager manager;
	
	/**
	 * コースの情報を保存するためのクラスのインスタンス
	 */
	private ConfigurationForData courseDataConf;
	
	public MHRCourse(Main plugin, MHRCore mhr) {
		super(plugin);
		this.mhr = mhr;
	}

	@Override
	public void init() {
		//Assignment WalkEvent.
		walkEvent = new WalkEvent(this);
		
		//Register commands.
		registerCommands();
		
		mhr.getPlugin().getLogger().info("MineHorseRacing course function enabled!");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void preInit() {
		FileConfiguration config = mhr.getPlugin().getConfig();
		
		//Add defaults.
		config.addDefault("IntervalOfCheckWalk", 1);
		
		
		//Get interval.
		int interval = config.getInt("IntervalOfCheckWalk");
		
		//Get courses data.
		courseDataConf = new ConfigurationForData(getPlugin(), "coursesdata.info", this);
		courseDataConf.loadConfig();
		courseDataConf.getConf().addDefault("Courses", new ArrayList<>());
		ArrayList<Course> courses =
				(ArrayList<Course>) courseDataConf.getConf().get("Courses");
		
		//Assignment manager.
		manager = new CourseManager(this, courses);
		
		//Register the check walking thread.
		registerCheckWalkingThread(interval);
	}

	@Override
	public void disable() {
		//Set configuration.
		setConfig();
		
		//Save courses data.
		courseDataConf.saveConfig();
		
		mhr.getPlugin().getLogger().info("MineHorseRacing course function disabled!");
	}

	@Override
	public void preDisable() {

	}


	@Override
	public void setConfig() {
		FileConfiguration config = mhr.getPlugin().getConfig();
		
		//Set interval.
		config.set("IntervalOfCheckWalk", checkWalkingThread.interval);
		
		//Set courses data.
		courseDataConf.getConf().set("Courses", manager.getCourses());
	}

	@Override
	public void deserializes() {
		ConfigurationSerialization.registerClass(Course.class);
	}

	protected void registerCommands() {
		CommandExecutor executor = mhr.getCmdExecutor();
		executor.addCommand(new Add(mhr, "c_add any", "mhrc.course.add", "コースを追加します。"));
		executor.addCommand(new Remove(mhr, "c_remove any", "mhrc.course.remove", "コースを削除します。"));
		executor.addCommand(new AddCheckPoint(getMHR(), "c_addpoint", "mhrc.course.addpoint.", "チェックポイントを追加します。"));
	}
	
	protected void registerCheckWalkingThread(int interval) {
		checkWalkingThread = new CheckWalkingThread(mhr, interval);
		//Register the check walking thread.
		mhr.getPlugin().getServer().getScheduler().runTaskTimer(mhr.getPlugin(), checkWalkingThread, 0, 0);
	}

	/**
	 * MineHorseRacingPluginの処理部のクラスのインスタンスを返します。
	 * @return MineHorseRacingPluginの処理部のクラスのインスタンス
	 */
	public MHRCore getMHR() {
		return mhr;
	}

	/**
	 * コースを操作するマネージャークラスのインスタンスを返します。
	 * @return コースを操作するマネージャークラスのインスタンス
	 */
	public CourseManager getManager() {
		return manager;
	}
	
	@Override
	public void walk(Block block, HorseData data) {
		walkEvent.walk(block, data);
	}
	
}
