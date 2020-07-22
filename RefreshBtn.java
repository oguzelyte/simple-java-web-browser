package websitePack;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

@SuppressWarnings("serial")
public class RefreshBtn extends JButton {

	private int width;
	private int heigth;

	/*
	 * Creates a refresh button and sets the its width and height, assigns
	 * an icon to it.
	 * 
	 */
	public RefreshBtn(int width, int heigth) {
		setWidth(width);
		setHeigth(heigth);

		URL url = Main.class.getResource(
                "/iconPack/reload.png");
		ImageIcon refreshIcon = new ImageIcon(url);

		if (width > heigth)
			setIcon(new ImageIcon(
					refreshIcon.getImage().getScaledInstance(heigth - 10, heigth - 10, java.awt.Image.SCALE_SMOOTH)));
		else
			setIcon(new ImageIcon(
					refreshIcon.getImage().getScaledInstance(width - 10, width - 10, java.awt.Image.SCALE_SMOOTH)));

		setFocusable(false);
		setPreferredSize(new Dimension(width, heigth));

		/*
		 * On click it gets the displayed pane from the main 
		 * method and uses a method in the URLDisplayPane to
		 * load it up to the user.
		 * 
		 */
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WebsiteBuilder.getPane().refreshPage();
			}

		});
	}


	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public JButton getRefreshBtn() {
		return this;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

}
