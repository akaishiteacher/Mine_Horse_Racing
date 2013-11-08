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
import net.akaishi_teacher.mhr.course.commands.End;
import net.akaishi_teacher.mhr.course.commands.Remove;
import net.akaishi_teacher.mhr.course.commands.RemoveCheckPoint;
import net.akaishi_teacher.mhr.course.commands.SetAngle;
import net.akaishi_teacher.mhr.course.commands.SetLap;
import net.akaishi_teacher.mhr.course.commands.SetOneLapIndex;
import net.akaishi_teacher.mhr.course.commands.SetUsingCourse;
import net.akaishi_teacher.mhr.course.commands.Start;
import net.akaishi_teacher.mhr.course.commands.ViewRank;
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
		config.addDefault("ViewRank", true);
		
		
		//Get interval.
		int interval = config.getInt("IntervalOfCheckWalk");
		boolean viewRank = config.getBoolean("ViewRank");
		
		//Get courses data.
		courseDataConf = new ConfigurationForData(getPlugin(), "coursesdata.info", this);
		courseDataConf.loadConfig();
		courseDataConf.getConf().addDefault("Courses", new ArrayList<>());
		ArrayList<Course> courses =
				(ArrayList<Course>) courseDataConf.getConf().get("Courses");
		
		//Assignment manager.
		manager = new CourseManager(this, courses);
		
		//Set ViewRank.
		manager.setViewRank(viewRank);
		
		
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
		ConfigurationSerialization.registerClass(Course.class, "Course");
		ConfigurationSerialization.registerClass(CheckPoint.class, "CheckPoint");
		ConfigurationSerialization.registerClass(Area.class, "Area");
	}

	protected void registerCommands() {
		CommandExecutor cmdExecutor = mhr.getCmdExecutor();
		cmdExecutor.addCommand(new Add(mhr, "c_add any", "mhrc.course.add", "コースを追加します。"));
		cmdExecutor.addCommand(new Remove(mhr, "c_remove any", "mhrc.course.remove", "コースを削除します。"));
		cmdExecutor.addCommand(new AddCheckPoint(mhr, "c_addpoint", "mhrc.course.addpoint.", "チェックポイントを追加します。"));
		cmdExecutor.addCommand(new RemoveCheckPoint(mhr, "c_rmpoint", "mhrc.course.removepoint", "チェックポイントを消去します。"));
		cmdExecutor.addCommand(new SetUsingCourse(mhr, "c_usingcourse any", "mhrc.course.usingcourse", "使用するコースを指定します。"));
		cmdExecutor.addCommand(new SetAngle(mhr, "c_setangle any any", "mhrc.course.setangle", "チェックポイントが保持する角度を設定します。"));
		cmdExecutor.addCommand(new SetOneLapIndex(mhr, "c_setonelapindex any", "mhrc.course.setonelapindex", "1周に必要なチェックポイントの通過数を設定します。"));
		cmdExecutor.addCommand(new SetLap(mhr, "c_setlap any", "mhrc.course.setlap", "ラップ数を設定します。"));
		cmdExecutor.addCommand(new Start(mhr, "c_start", "mhrc.course.start", "レースを開始します。"));
		cmdExecutor.addCommand(new End(mhr, "c_end", "mhrc.course.end", "レースを終了します。"));
		cmdExecutor.addCommand(new ViewRank(mhr, "c_viewrank any", "mhrc.course.viewrank", "順位を表示するか指定します。"));
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
