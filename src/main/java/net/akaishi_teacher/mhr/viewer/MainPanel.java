package net.akaishi_teacher.mhr.viewer;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends ViewerPanel {

	private static final long serialVersionUID = 1L;
	
	public MenusPanel menusPanel;
	
	public MainPanel(Viewer viewer) {
		super(viewer);

		menusPanel = new MenusPanel(this);
		
		setLayout(new GridLayout(4, 1));
		
		ImageIcon icon = new ImageIcon(getClass().getResource("logo.png"));
		JLabel label = new JLabel(icon);
		add(label);

		add(menusPanel);
		
		ChangeButton horseStatusButton = new ChangeButton(viewer, EnumViewPanel.HORSE_STATUS, EnumViewPanel.MAIN, "HorseStatus");
		menusPanel.addMyComponent(horseStatusButton);
		
		validate();
		menusPanel.reGenerateLayout();
	}

	class MenusPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		ArrayList<JComponent> components = new ArrayList<>();
		MainPanel panel;
		public MenusPanel(MainPanel panel) {
			this.panel = panel;
		}
		public void addMyComponent(JComponent component) {
			components.add(component);
			add(component);
		}
		public void reGenerateLayout() {
			setLayout(new GridLayout(components.size() / 2, 2));
			validate();
		}
	}

}
