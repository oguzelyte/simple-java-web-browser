package websitePack;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import sun.applet.Main;

/*
 * 
 * @author: Olivija Guzelyte (160421859)
 * @version: 03/05/2017
 * 
 */

public class FavouritesBtn {

	/*
	 * Creates a button for bookmark saving. Also for the same purpose creates
	 * an array list in order to save all the bookmarks and further on use it to
	 * create persistent bookmarks and display them to the user.
	 */
	private JButton favBtn = new JButton();
	private int width;
	private int heigth;
	private ArrayList<String> favouritePgs = new ArrayList<String>();
	private ImageIcon favouritesIcon;
	private Boolean starActive;

	/*
	 * Sets the icon of the bookmark adder, which is a conventional star, scales
	 * it to the dimension specified by the user.
	 */
	public FavouritesBtn(final int width, final int heigth) throws IOException {
		this.setWidth(width);
		this.setHeigth(heigth);

		URL url = Main.class.getResource("/iconPack/star.png");
		favouritesIcon = new ImageIcon(url);

		if (width > heigth)
			this.favBtn.setIcon(new ImageIcon(favouritesIcon.getImage().getScaledInstance(heigth - 10, heigth - 10,
					java.awt.Image.SCALE_SMOOTH)));
		else
			this.favBtn.setIcon(new ImageIcon(
					favouritesIcon.getImage().getScaledInstance(width - 10, width - 10, java.awt.Image.SCALE_SMOOTH)));

		favBtn.setFocusable(false);

		this.favBtn.setPreferredSize(new Dimension(width, heigth));

		/*
		 * On click the star gets filled up to signal that the page was added to
		 * the bookmarks list.
		 */
		favBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				URL url = Main.class.getResource("/iconPack/star-filled.png");
				favouritesIcon = new ImageIcon(url);
				if (width > heigth)
					favBtn.setIcon(new ImageIcon(favouritesIcon.getImage().getScaledInstance(heigth - 10, heigth - 10,
							java.awt.Image.SCALE_SMOOTH)));
				else
					favBtn.setIcon(new ImageIcon(favouritesIcon.getImage().getScaledInstance(width - 10, width - 10,
							java.awt.Image.SCALE_SMOOTH)));

				if (starActive == false) {
					favouritePgs.add(WebsiteBuilder.getField().getURLBar().getText());
					starActive = true;
				}

				/*
				 * If the bookmarks list is visible and the user clicks a star
				 * as to add the page to his/her bookmarks list the bookmarks
				 * list needs to be closed and the other one created and opened,
				 * that is how the user gets instant updates of what bookmarks
				 * he/she put in the list.
				 */
				if (WebsiteBuilder.getFavList().getDialog().isVisible()) {
					WebsiteBuilder.getFavList().getDialog().setVisible(false);
					WebsiteBuilder.getMenu().getBookmarkList()
							.getBookmarkList(WebsiteBuilder.getFavouritesBtn().getFavouritePgs());

				}

			}

		});

	}

	public FavouritesBtn() {

	}

	private void setHeigth(int heigth) {
		this.heigth = heigth;

	}

	private void setWidth(int width) {
		this.width = width;

	}

	public JButton getFavBtn() {
		return favBtn;
	}

	/*
	 * Returns the list of bookmarked pages.
	 */

	public ArrayList<String> getFavouritePgs() {
		return favouritePgs;
	}

	public void setStarStatus(Boolean t) {
		starActive = t;
	}

	/*
	 * Fills the star or leaves it hollow depending on the image passed to it.
	 */
	public void setStarFilling(String t) {

		URL url = Main.class.getResource(t);
		favouritesIcon = new ImageIcon(url);

		if (width > heigth)
			favBtn.setIcon(new ImageIcon(favouritesIcon.getImage().getScaledInstance(heigth - 10, heigth - 10,
					java.awt.Image.SCALE_SMOOTH)));
		else
			favBtn.setIcon(new ImageIcon(
					favouritesIcon.getImage().getScaledInstance(width - 10, width - 10, java.awt.Image.SCALE_SMOOTH)));
	}

}
