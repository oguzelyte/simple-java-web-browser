package websitePack;


import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sun.applet.Main;

/*
 * 
 * @author: Olivija Guzelyte (160421859)
 * @version: 03/05/2017
 * 
 */
public class ErrorHandler {

	/*
	 * Loads to an user an error pane reacting
	 * to a catch in a method it is called upon
	 * and specifies what url could not be loaded.
	 */
	public static void throwError(String url) {
		JPanel errPane = new JPanel();
		URL url1 = Main.class.getResource(
                "/iconPack/sad.png");
		ImageIcon icon = new ImageIcon(url1);
		errPane = new JPanel();
		JOptionPane.showMessageDialog(errPane, "Could not load: " + url + "\nPlease add a valid address",
				"Page cannot be accessed", JOptionPane.PLAIN_MESSAGE, icon);
	}

}
