package websitePack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * 
 * @author: Olivija Guzelyte (160421859)
 * @version: 03/05/2017
 * 
 */

public class FavouritesList {

	/*
	 * Creates the buttons in order to delete
	 * one bookmark or all of them as once, also a
	 * bookmark list in order to display it to the user.
	 * With it comes the necessary panel: delete panel, 
	 * in which the two buttons are placed and a dialog
	 * window showing the bookmark list.
	 */
	private JList<String> bookmarkList = new JList<String>();
	private JPanel delPanel = new JPanel();
	private JDialog dialog = new JDialog();
	private JButton delete = new JButton("Delete");
	private JButton deleteAll = new JButton("Delete all");
	private GridLayout layout = new GridLayout(0, 2);

	public FavouritesList() {

		/*
		 * Window listeners to write and read in the bookmarks.
		 * On web browser close, the bookmarks get written into the
		 * file. On web browser open, bookmarks are read.
		 */
		WebsiteBuilder.mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				try {
					writeToTextFile(WebsiteBuilder.getFavouritesBtn().getFavouritePgs());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void windowOpened(WindowEvent we) {
				try {
					readFromTextFile();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});

		delPanel.setPreferredSize(new Dimension(400, 30));

		delPanel.setLayout(layout);
		delPanel.add(delete);
		delPanel.add(deleteAll);

		/*
		 * If 'delete' button is clicked, the bookmark list is
		 * not empty and the user actually selected something, the bookmark
		 * is removed, dialog is discarded and a new one is made. The new one
		 * displays a list without the removed bookmark.
		 * 
		 */
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!WebsiteBuilder.getFavouritesBtn().getFavouritePgs().isEmpty()
						&& bookmarkList.isSelectionEmpty() == false) {
					WebsiteBuilder.getFavList().getDialog().setVisible(false);
					WebsiteBuilder.getFavouritesBtn().getFavouritePgs().remove(bookmarkList.getSelectedIndex());
					WebsiteBuilder.getMenu().getBookmarkList()
							.getBookmarkList(WebsiteBuilder.getFavouritesBtn().getFavouritePgs());
				}

			}

		});

		/*
		 * If 'delete all' button is clicked, the bookmark list is
		 * not empty, the bookmarks are all removed, previous dialog
		 * set invisible and a new one is made. The new one has no
		 * bookmarks at all.
		 */
		deleteAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!WebsiteBuilder.getFavouritesBtn().getFavouritePgs().isEmpty()) {
					WebsiteBuilder.getFavList().getDialog().setVisible(false);
					WebsiteBuilder.getFavouritesBtn().getFavouritePgs().clear();
					WebsiteBuilder.getMenu().getBookmarkList()
							.getBookmarkList(WebsiteBuilder.getFavouritesBtn().getFavouritePgs());
				}

			}

		});
	}

	/*
	 * This method takes care of new bookmark list making.
	 * It creates a new dialog window, adds to it the new bookmark
	 * list array, passes it to the list. Delete buttons and the
	 * list are then added to the dialog. On a double mouse click, the
	 * user can visit the pages he/she bookmarked.
	 * 
	 */
	public void getBookmarkList(ArrayList<String> favouritePgs) {

		dialog = new JDialog(dialog, "Bookmarks List");
		dialog.setResizable(false);
		dialog.setSize(new Dimension(400, 300));

		String[] array = new String[favouritePgs.size()];
		array = favouritePgs.toArray(array);

		bookmarkList = new JList<String>(array);
		JScrollPane scrollPane = new JScrollPane(bookmarkList);

		dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);
		delPanel.setPreferredSize(new Dimension(200, 30));

		dialog.getContentPane().add(delPanel, BorderLayout.NORTH);

		dialog.setVisible(true);

		/*
		 * Detects a double mouse click and sets the page
		 * to the user bookmarked page. 
		 */
		bookmarkList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = bookmarkList.locationToIndex(e.getPoint());
					bookmarkList.ensureIndexIsVisible(index);
					try {
						WebsiteBuilder.getPane().setPage(bookmarkList.getSelectedValue());
					} catch (IOException e1) {

						e1.printStackTrace();
					}

				}
			}

		});
	}

	/*
	 * This method takes care of writing the bookmark list
	 * into a text file. It receives the bookmark list, checks
	 * if the text file exists, if not, creates a new one. Writes
	 * the bookmarks into the file.
	 * 
	 */
	public void writeToTextFile(ArrayList<String> bookmarkList)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {

		bookmarkList = WebsiteBuilder.getFavouritesBtn().getFavouritePgs();
		File bookmarks = new File("Bookmarks.txt");

		try {
			if (!bookmarks.exists()) {
				bookmarks.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(bookmarks);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int i = 0; i < bookmarkList.size(); i++) {
				bufferedWriter.write(bookmarkList.get(i));
				bufferedWriter.newLine();
			}

			bufferedWriter.close();

		} catch (IOException e) {
			ErrorHandler.throwError("Could not load to bookmarks.");
		}

	}

	/*
	 * This method takes care of reading from the bookmark
	 * file. It checks if a file exists, if not, does nothing.
	 * Reads until finds no more lines with text and loads the
	 * bookmarks into the bookmarks array, alongside with bookmarks that
	 * had been added previously.
	 * 
	 */
	public void readFromTextFile() throws UnsupportedEncodingException, FileNotFoundException, IOException {

		File bookmarks = new File("Bookmarks.txt");
		ArrayList<String> bookmarksTemp = WebsiteBuilder.getFavouritesBtn().getFavouritePgs();
		if (bookmarks.exists()) {

			try {

				FileReader fileReader = new FileReader(bookmarks);

				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String url = bufferedReader.readLine();
				while (url != null) {
					bookmarksTemp.add(url);
					url = bufferedReader.readLine();

				}

				bufferedReader.close();

			} catch (IOException e) {
				ErrorHandler.throwError("Could not read from bookmarks.");
			}

		}
	}

	public JDialog getDialog() {
		return dialog;
	}

}