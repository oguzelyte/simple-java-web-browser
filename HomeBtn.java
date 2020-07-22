package websitePack;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import sun.applet.Main;

/*
 * 
 * @author: Olivija Guzelyte (160421859)
 * @version: 03/05/2017
 * 
 */

public class HomeBtn {

	/*
	 * Creates a home button, specifies its width and heigth,
	 * sets its icon, scales it appropriate to its size.
	 */
	private JButton home = new JButton();
	private int width;
	private int heigth;

	public HomeBtn(int width, int heigth) {
		this.setWidth(width);
		this.setHeigth(heigth);

		URL url = Main.class.getResource(
                "/iconPack/house.png");
		ImageIcon homeIcon = new ImageIcon(url);
		if (width > heigth)
			this.home.setIcon(new ImageIcon(
					homeIcon.getImage().getScaledInstance(heigth - 10, heigth - 10, java.awt.Image.SCALE_SMOOTH)));
		else
			this.home.setIcon(new ImageIcon(
					homeIcon.getImage().getScaledInstance(width - 10, width - 10, java.awt.Image.SCALE_SMOOTH)));

		this.home.setPreferredSize(new Dimension(width, heigth));

		/*
		 * On click it calls a method from the pane
		 * that extends JEditorPane, which sets the home
		 * page to user configured one.
		 */
		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					WebsiteBuilder.getPane().setHomePage();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});
	}

	public int getWidth() {
		return width;
	}

	public JButton getHomeBtn() {
		return home;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

}
