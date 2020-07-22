package websitePack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/*
 * 
 * @author: Olivija Guzelyte (160421859)
 * @version: 03/05/2017
 * 
 */

/*
 * This is used to build the web browser out of its main components:
 * toolbar, menu and a display pane. All added to the main frame.
 * 
 */
public class WebsiteBuilder {

	static JFrame mainFrame = new JFrame("Olivija's Web Browser");
	private static final int BUTTON_WIDTH = 40;
	private static final int BUTTON_HEIGTH = 50;
	private static MenuBar menu;
	private static Toolbar t = new Toolbar(50);
	private static URLDisplayPane p;
	private static JScrollPane scrollPane;

	public static void main(String[] args) throws IOException {

		/*
		 * The main functionality buttons are added to the toolbar
		 * with the ability to change their sizes according to the
		 * user.
		 */
		t.addNavigationButton(NavigationBtnType.BACK, BUTTON_WIDTH, BUTTON_HEIGTH);
		t.addNavigationButton(NavigationBtnType.FORWARD, BUTTON_WIDTH, BUTTON_HEIGTH);
		t.addURLBar();
		t.addHomeButton(BUTTON_WIDTH, BUTTON_HEIGTH);
		t.addRefreshButton(BUTTON_WIDTH, BUTTON_HEIGTH);
		t.addFavouritesButton(BUTTON_WIDTH, BUTTON_HEIGTH);

		/*
		 * A menu bar and a display pane are initialised.
		 */
		menu = new MenuBar();
		p = new URLDisplayPane();
		scrollPane = new JScrollPane(p);
		scrollPane.setPreferredSize(new Dimension(20, 200));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		/*
		 * Everything gets added on to the main frame that is 
		 * then made visible.
		 */
		mainFrame.add(scrollPane, BorderLayout.CENTER);
		mainFrame.add(t.getToolbar(), BorderLayout.NORTH);
		mainFrame.add(menu.getMenuBar(), BorderLayout.SOUTH);
		mainFrame.setPreferredSize(new Dimension(800, 600));
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.requestFocusInWindow();

	}

	/*
	 * Some methods to return the necessary components of
	 * Swing GUI already visible to the user when he/she opens
	 * the web browser. Also the button width and height are 
	 * returned and used in other classes.
	 */
	public static URLDisplayPane getPane() {
		return p;
	}

	public static URLBar getField() {
		return t.getURL();
	}

	public static NavigationBtn getBackBtn() {
		return t.getBackBtn();
	}

	public static NavigationBtn getForwardBtn() {
		return t.getForwardBtn();
	}

	public static FavouritesBtn getFavouritesBtn() {
		return t.getFavouritesBtn();
	}

	public static FavouritesList getFavList() {
		return menu.getBookmarkList();
	}

	public static HistoryList getHistoryList() {
		return menu.getHistoryList();
	}

	public static MenuBar getMenu() {
		return menu;
	}

	public static int getButtonWidth() {
		return BUTTON_WIDTH;
	}

	public static int getButtonHeigth() {
		return BUTTON_HEIGTH;
	}

}
