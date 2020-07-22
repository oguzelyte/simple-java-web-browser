package websitePack;

import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/*
 * 
 * @author: Olivija Guzelyte (160421859)
 * @version: 03/05/2017
 * 
 */

public class Toolbar {

	/*
	 * Creates a toolbar panel, back, forward
	 * navigation buttons, an url bar, favourites button
	 * in order to add them all to the toolbar.
	 */	
	private JPanel toolbar = new JPanel();
	private URLBar b = new URLBar();
	private NavigationBtn nBack = new NavigationBtn();
	private NavigationBtn nForward = new NavigationBtn();
	private FavouritesBtn f = new FavouritesBtn();
	private int heigth;

	/*
	 * Sets up a toolbar's heigth, makes a medium empty border
	 * and sets its layout to stretch all through the web browser, also makes it
	 * visible.
	 */	
	public Toolbar(int heigth) {
		toolbar.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.LINE_AXIS));
		this.setHeigth(heigth);
		toolbar.setVisible(true);

	}

	/*
	 * Adds either a home, refresh, favourites, navigation
	 * button or a url bar.
	 * 
	 */	
	public void addHomeButton(int heigth, int width) {
		HomeBtn b = new HomeBtn(width, heigth);
		toolbar.add(b.getHomeBtn());
	}

	public void addRefreshButton(int heigth, int width) {
		RefreshBtn b = new RefreshBtn(width, heigth);
		toolbar.add(b.getRefreshBtn());
	}

	public void addFavouritesButton(int heigth, int width) throws IOException {
		f = new FavouritesBtn(width, heigth);
		toolbar.add(f.getFavBtn());
	}

	/*
	 * Depending on the button type, the upcoming method adds either
	 * back or forwards button.
	 * 
	 */	
	public void addNavigationButton(NavigationBtnType way, int heigth, int width) {
		NavigationBtn n = new NavigationBtn(way, width, heigth);
		if (n.getButtonType() == NavigationBtnType.BACK) {
			nBack = n;
		} else
			nForward = n;
		toolbar.add(n.getNavigationBtn());
	}

	public void addURLBar() {
		toolbar.add(b.getURLBar());
	}

	public URLBar getURL() {
		return b;
	}

	/*
	 * Returns all the buttons it has added because other classes
	 * use them through the main method in order to react to user
	 * clicks.
	 * 
	 */
	public NavigationBtn getBackBtn() {
		return nBack;
	}

	public NavigationBtn getForwardBtn() {
		return nForward;
	}

	public FavouritesBtn getFavouritesBtn() {
		return f;
	}

	public JPanel getToolbar() {
		return toolbar;
	}

	/*
	 * Sets and gets the heigth of the toolbar.
	 */
	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

}
