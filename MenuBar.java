package websitePack;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JToolBar;

/*
 * 
 * @author: Olivija Guzelyte (160421859)
 * @version: 03/05/2017
 * 
 */

@SuppressWarnings("serial")
public class MenuBar extends JToolBar {

	/*
	 * Creates two necessary menu buttons: bookmarks
	 * and history. Also initialises the HistoryList and
	 * FavouritesList classes in order to return the 
	 * respective lists from those classes.
	 * 
	 */
	private int heigth;
	private JButton favBtn = new JButton("Bookmarks");
	private JButton historyBtn = new JButton("History");
	private FavouritesList favList = new FavouritesList();
	private HistoryList historyList = new HistoryList();

	public MenuBar() throws IOException {

		/*
		 * Adds the bookmarks and history buttons to the menu.
		 * 
		 */
		addBookmarkBtn();
		addHistoryBtn();

		/*
		 * If the favourites button is clicked, in case it is faulty
		 * and the user clicks a few times, the old dialog is discarded
		 * and the bookmarks list is retrieved from situated in the main class.
		 * It is then passed on to the FavouritesList class, in which it is 
		 * configured and displayed to the user.
		 */
		favBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				favList.getDialog().setVisible(false);
				favList.getBookmarkList(WebsiteBuilder.getFavouritesBtn().getFavouritePgs());

			}

		});

		/*
		 * If the history button is clicked, in case it is faulty
		 * and the user clicks a few times, the old dialog is discarded
		 * and the history list is retrieved from situated in the main class.
		 * It is then passed on to the HistoryList class, in which it is 
		 * configured and displayed to the user.
		 */
		
		historyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				historyList.getDialog().setVisible(false);
				historyList.getHistoryList(WebsiteBuilder.getPane().getHistoryPgs(),
						WebsiteBuilder.getPane().getHistoryPgsDate());

			}

		});
	}


	public JToolBar getMenuBar() {
		setSize(new Dimension(WebsiteBuilder.mainFrame.getWidth(), heigth));
		setVisible(true);
		return this;
	}

	/*
	 * Methods configuring the bookmark and history buttons
	 */
	public void addBookmarkBtn() {

		favBtn.setMargin(new Insets(0, 0, 0, 0));
		favBtn.setMaximumSize(new Dimension(WebsiteBuilder.getButtonWidth() + 20, WebsiteBuilder.getButtonHeigth()));
		add(favBtn);

	}

	public void addHistoryBtn() {

		historyBtn.setMargin(new Insets(0, 0, 0, 0));
		historyBtn.setMaximumSize(new Dimension(WebsiteBuilder.getButtonWidth() + 20, WebsiteBuilder.getButtonHeigth()));
		add(historyBtn);

	}

	public FavouritesList getBookmarkList() {
		return favList;
	}

	public JButton getFavBtn() {
		return favBtn;
	}

	public HistoryList getHistoryList() {
		return historyList;
	}

}
