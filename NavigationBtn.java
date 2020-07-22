package websitePack;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.net.URL;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import sun.applet.Main;

/*
 * 
 * @author: Olivija Guzelyte (160421859)
 * @version: 03/05/2017
 * 
 */

public class NavigationBtn {

	/*
	 * A navigation button is added, also its type
	 * enum class is initialised and a stack with which
	 * it will be iterated through history.
	 * 
	 */
	
	NavigationBtnType buttonType;
	private JButton navBtn = new JButton();
	private int width;
	private int heigth;
	private Stack<String> history = new Stack<String>();

	/*
	 * By default the navigation button is disabled. The 
	 * icon on it is added based by what type of button the
	 * navigation button is: back of forward.
	 * 
	 */
	public NavigationBtn(NavigationBtnType way, int width, int heigth) {
		this.setWidth(width);
		this.setHeigth(heigth);
		this.buttonType = way;
		navBtn.setEnabled(false);

		ImageIcon navBtnIcon;

		if (buttonType == NavigationBtnType.BACK) {

			URL url = Main.class.getResource(
	                "/iconPack/back.png");
			navBtnIcon = new ImageIcon(url);
			if (width > heigth)
				this.navBtn.setIcon(new ImageIcon(navBtnIcon.getImage().getScaledInstance(heigth - 10, heigth - 10,
						java.awt.Image.SCALE_SMOOTH)));
			else
				this.navBtn.setIcon(new ImageIcon(
						navBtnIcon.getImage().getScaledInstance(width - 10, width - 10, java.awt.Image.SCALE_SMOOTH)));
			
			/*
			 * On back button click it triggers a method on the pane
			 * that extends JEditorPane and the forward button gets enabled.
			 * 
			 */
			navBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						WebsiteBuilder.getPane().goBack();
						WebsiteBuilder.getForwardBtn().getNavigationBtn().setEnabled(true);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}

			});

		} else {
			URL url = Main.class.getResource(
	                "/iconPack/next-1.png");
			navBtnIcon = new ImageIcon(url);
			if (width > heigth)
				this.navBtn.setIcon(new ImageIcon(navBtnIcon.getImage().getScaledInstance(heigth - 10, heigth - 10,
						java.awt.Image.SCALE_SMOOTH)));
			else
				this.navBtn.setIcon(new ImageIcon(
						navBtnIcon.getImage().getScaledInstance(width - 10, width - 10, java.awt.Image.SCALE_SMOOTH)));

			/*
			 * On forward button click it triggers a method on the pane
			 * that extends JEditorPane and the back button gets enabled.
			 * 
			 */
			navBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						WebsiteBuilder.getPane().goForward();
						WebsiteBuilder.getBackBtn().getNavigationBtn().setEnabled(true);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}

			});
		}

		/*
		 * Removes an unecessary border on icon when button is clicked.
		 * 
		 */
		this.navBtn.setFocusable(false);
		this.navBtn.setPreferredSize(new Dimension(width, heigth));
	}

	public NavigationBtn() {

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

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public NavigationBtnType getButtonType() {
		return buttonType;
	}

	public JButton getNavigationBtn() {
		return navBtn;
	}

	/*
	 * A method on URLDisplayPane uses this to push the current
	 * history onto the stack.
	 * 
	 */
	public void pushToHistory(String address) {
		history.push(address);
	}

	public Stack<String> getHistory() {
		return history;
	}

}
