package net.akaishi_teacher.mhr.viewer;

import java.util.ArrayList;

import javax.swing.JTextArea;

import net.akaishi_teacher.mhr.HorseData;
import net.akaishi_teacher.mhr.course.CourseSession;



public class HorseStatusPanel extends ViewerPanel {

	private static final long serialVersionUID = 1L;

	private JTextArea textArea;

	public HorseStatusPanel(Viewer viewer) {
		super(viewer);
		textArea = new JTextArea();
		textArea.setEditable(false);
		add(textArea);
		validate();
	}

	public void repaintStatus() {
		StringBuffer buffer = new StringBuffer();
		ArrayList<HorseData> datas = viewer.getMHR().getStatus().getHorseDatas();
		for (HorseData data : datas) {
			CourseSession session = data.courseSession;
			buffer.append("No." + data.id + "\n");
			buffer.append("  Location:" + data.loc.toString() + "\n");
			buffer.append("  CourseSession:\n");
			buffer.append("    Lap:" + session.getLap() + "\n");
			buffer.append("    Point:" + session.getPoint() + "\n");
			buffer.append("    LapTime:" + session.getLapTime() + "\n");
		}
		textArea.setText(null);
		textArea.setText(buffer.toString());
	}

}
