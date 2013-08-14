package net.akaishi_teacher.mhr.area;

import java.util.ArrayList;

/**
 * コース管理クラス
 *
 * @deprecated Fieldクラスにリネームし作り直す予定
 */
public class Course extends Area {
	String label;
	CheckPoint start;
	ArrayList<CheckPoint> checkPoint;
	CheckPoint goal;
}
