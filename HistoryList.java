package websitePack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.IOException;

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

public class HistoryList {

	/*
	 * Creates two separate lists in order to display
	 * the url of history and the date it was created.
	 * Creates necessary delete and body panels, in which 
	 * are the delete buttons and the lists.
	 * 
	 */
	private JList<String> historyList = new JList<String>();
	private JList<String> date = new JList<String>();
	private JPanel delPanel = new JPanel();
	private JPanel bodyPanel = new JPanel();
	private JDialog dialog = new JDialog();
	private JButton delete = new JButton("Delete");
	private JButton deleteAll = new JButton("Delete all");
	private GridLayout layout = new GridLayout(0, 2);

	public HistoryList() throws IOException {

		delPanel.setPreferredSize(new Dimension(400, 30));

		delPanel.setLayout(layout);
		delPanel.add(delete);
		delPanel.add(deleteAll);

		/*
		 * If 'delete' button is clicked, the history list is
		 * not empty and the user actually selected something, the history url
		 * is removed, dialog is discarded and a new one is made. The new one
		 * displays a list without the removed bookmark.
		 * 
		 */
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!WebsiteBuilder.getPane().getHistoryPgs().isEmpty() && historyList.isSelectionEmpty() == false) {
					WebsiteBuilder.getHistoryList().getDialog().setVisible(false);

					WebsiteBuilder.getPane().getHistoryPgs().remove(historyList.getSelectedIndex());
					WebsiteBuilder.getPane().getHistoryPgsDate().remove(historyList.getSelectedIndex());

					WebsiteBuilder.getMenu().getHistoryList().getHistoryList(WebsiteBuilder.getPane().getHistoryPgs(),
							WebsiteBuilder.getPane().getHistoryPgsDate());
				}

			}

		});

		/*
		 * If 'delete all' button is clicked, the history list is
		 * not empty, the history urls are all removed, previous dialog
		 * set invisible and a new one is made. The new one has no
		 * history links at all.
		 */
		deleteAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!WebsiteBuilder.getPane().getHistoryPgs().isEmpty()) {
					WebsiteBuilder.getHistoryList().getDialog().setVisible(false);
					WebsiteBuilder.getPane().getHistoryPgs().clear();
					WebsiteBuilder.getPane().getHistoryPgsDate().clear();
					WebsiteBuilder.getMenu().getHistoryList().getHistoryList(WebsiteBuilder.getPane().getHistoryPgs(),
							WebsiteBuilder.getPane().getHistoryPgsDate());
				}

			}

		});

	}
	
	/*
	 * This method takes care of new history list making.
	 * It creates a new dialog window, adds to it the new history
	 * list array and the array of the dates when it was made,
	 * passes it to two separate lists. Delete buttons and the
	 * list are then added to the dialog. On a double mouse click, the
	 * user can visit the pages he/she has on history.
	 * 
	 */
	public void getHistoryList(ArrayList<String> historyPgs, ArrayList<String> dateList) {

		dialog = new JDialog(dialog, "History List");
		dialog.setResizable(false);
		dialog.setSize(new Dimension(500, 300));

		bodyPanel = new JPanel();

		String[] array = new String[historyPgs.size()];
		String[] arrayDate = new String[dateList.size()];

		array = historyPgs.toArray(array);
		arrayDate = dateList.toArray(arrayDate);

		historyList = new JList<String>(array);
		date = new JList<String>(arrayDate);
		date.setPreferredSize(new Dimension(160, 30));
		date.setEnabled(false);

		bodyPanel.setLayout(layout);
		bodyPanel.add(date);
		bodyPanel.add(historyList);

		JScrollPane scrollPane = new JScrollPane(bodyPanel);

		delPanel.setPreferredSize(new Dimension(400, 30));

		dialog.getContentPane().add(delPanel, BorderLayout.NORTH);
		dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);

		dialog.setVisible(true);

		/*
		 * Detects a double mouse click and sets the page
		 * to the user clicked history page. 
		 */
		
		historyList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = historyList.locationToIndex(e.getPoint());
					historyList.ensureIndexIsVisible(index);
					try {
						WebsiteBuilder.getPane().setPage(historyList.getSelectedValue());
					} catch (IOException e1) {

						e1.printStackTrace();
					}

				}
			}

		});
	}

	public JDialog getDialog() {
		return dialog;
	}

}
