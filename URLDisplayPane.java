package websitePack;

import java.awt.event.WindowEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.PriorityQueue;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import sun.applet.Main;

/*
 * 
 * @author: Olivija Guzelyte (160421859)
 * @version: 03/05/2017
 * 
 */

@SuppressWarnings("serial")
public class URLDisplayPane extends JEditorPane {
	/*
	 * Creates an error pane in order to later ask user to specify his/her
	 * homepage if it cannot be properly read from the homepage file. A
	 * NavigationBtn class is instantiated in order to iterate between history,
	 * when back and forward buttons are pressed. Also, arrays of history pages
	 * and dates are added in order to save the overall history.
	 * 
	 */
	private JPanel errPane = new JPanel();
	private NavigationBtn btn = new NavigationBtn();
	private PriorityQueue<String> qForward = new PriorityQueue<String>();
	private ArrayList<String> historyPgs = new ArrayList<String>();
	private ArrayList<String> dateOfHistoryPgs = new ArrayList<String>();

	public URLDisplayPane() throws IOException {

		/*
		 * When web browser window is closed, save the history and its dates to
		 * a text file.
		 * 
		 */
		WebsiteBuilder.mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				try {
					writeToTextFile(WebsiteBuilder.getPane().getHistoryPgs(),
							WebsiteBuilder.getPane().getHistoryPgsDate());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/*
			 * When web browser window is open, load the history and its dates
			 * from a text file and afterwards set the homepage, which is also
			 * then saved on history.
			 * 
			 */
			@Override
			public void windowOpened(WindowEvent we) {
				try {
					readFromTextFile();
					setHomePage();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});

		setEditable(false);

		/*
		 * Detect when a hyperlink is pressed, call the method to see if it
		 * needs to be bookmarked, if so, then do it. Add to the history page
		 * list and add the complete date to the date list, also call a local
		 * method to see if the dialog is opened and a new dialog needs to be
		 * made in order to update the change for the user.
		 * 
		 */
		HyperlinkListener listener = new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent event) {

				if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {

						necessary(event.getURL().toString());

						/*
						 * If a hyperlink is clicked when the forward button is
						 * enabled, it has to be then disabled and its queue
						 * cleared, because that history disappears.
						 * 
						 */
						if (WebsiteBuilder.getForwardBtn().getNavigationBtn().isEnabled()) {
							WebsiteBuilder.getForwardBtn().getNavigationBtn().setEnabled(false);
							qForward.clear();
						}
						/*
						 * Set the url's text field to the hyperlink's url, set
						 * the page to the url, enable the back button, because
						 * a piece of history exists and push the url to the
						 * back button's history stack.
						 * 
						 */
						WebsiteBuilder.getField().setURL(event.getURL().toString());
						WebsiteBuilder.getField().getURLBar().setText(event.getURL().toString());
						setPage(event.getURL());
						WebsiteBuilder.getBackBtn().getNavigationBtn().setEnabled(true);
						btn.pushToHistory(event.getURL().toString());
					} catch (IOException ioe) {
						ErrorHandler.throwError(event.getURL().toString());
						WebsiteBuilder.getBackBtn().getNavigationBtn().setEnabled(false);
					}
				}

			}

		};
		addHyperlinkListener(listener);
	}

	/*
	 * Method called when the back navigation button is pressed.
	 * 
	 */
	public void goBack() throws IOException {

		String url;

		/*
		 * If history size reaches 2, backwards navigation button needs to be
		 * set disabled so user couldn't underflow the stack. Forward button's
		 * queue gets increased by one url. The url of the page is set to the
		 * item before the popped out url.
		 * 
		 */
		if (btn.getHistory().size() == 2) {
			WebsiteBuilder.getBackBtn().getNavigationBtn().setEnabled(false);
			qForward.add(btn.getHistory().pop());
			url = btn.getHistory().peek();

			/*
			 * Bookmarks star is filled if need be, history, dates of history
			 * get updated and a live update displayed to the user if the
			 * history dialog is opened.
			 * 
			 */
			necessary(url);

			/*
			 * The url text bar gets set to the peeked url and the page is
			 * loaded onto the JEditorPane.
			 * 
			 */
			WebsiteBuilder.getField().getURLBar().setText(url);
			WebsiteBuilder.getField().setURL(url);
			super.setPage(url);
		} else {

			/*
			 * If history size is more than 2, forward button's queue gets
			 * increased by one url. The url of the page is set to the item
			 * before the popped out url.
			 * 
			 */
			qForward.add(btn.getHistory().pop());
			url = btn.getHistory().peek();
			necessary(url);

			/*
			 * The url text bar gets set to the peeked url and the page is
			 * loaded onto the JEditorPane.
			 * 
			 */
			WebsiteBuilder.getField().getURLBar().setText(url);
			WebsiteBuilder.getField().setURL(url);
			super.setPage(url);
		}

	}

	/*
	 * If forward's queue size reaches 1, forward navigation button needs to be
	 * set disabled so user couldn't underflow the queue. Backward button's stack
	 * gets increased by one url. The url of the page is set to the
	 * item before the polled out url.
	 * 
	 */
	public void goForward() throws IOException {
		if (qForward.size() == 1) {
			WebsiteBuilder.getForwardBtn().getNavigationBtn().setEnabled(false);
		}
		String url = qForward.poll();

		necessary(url);

		btn.getHistory().add(url);
		WebsiteBuilder.getField().getURLBar().setText(url);
		WebsiteBuilder.getField().setURL(url);
		super.setPage(url);
	}

	/*
	 * If the url text bar's url is the same as it is in the stack, then the
	 * page simply gets refreshed, otherwise check if the forwards button is enabled.
	 * If it is, it needs to be disabled and the forward queue cleared.
	 * 
	 */
	@Override
	public void setPage(String url) throws IOException {

		if (url.equals(btn.getHistory().peek())) {
			refreshPage();
		} else {
			if (WebsiteBuilder.getForwardBtn().getNavigationBtn().isEnabled()) {

				WebsiteBuilder.getForwardBtn().getNavigationBtn().setEnabled(false);
				qForward.clear();
			}

			super.setPage(url);
			necessary(url);

			WebsiteBuilder.getField().getURLBar().setText(url);
			WebsiteBuilder.getField().setURL(url);

			WebsiteBuilder.getBackBtn().getNavigationBtn().setEnabled(true);
			btn.pushToHistory(url);
		}

	}

	/*
	 * Checks if homepage file exists, if it doesn't creates a new file.
	 * 
	 */
	public void setHomePage() throws IOException {

		File homepage = new File("Homepage.txt");

		if (!homepage.exists()) {
			homepage.createNewFile();
		}

		FileReader from = new FileReader(homepage);
		BufferedReader bufferedReader = new BufferedReader(from);
		String url = bufferedReader.readLine();
		bufferedReader.close();
		/*
		 * If homepage file does exist and it is just like the previous
		 * url on the stack, the page simply gets refreshed, otherwise page is set
		 * to the homepage and necessary additions made.
		 * 
		 */
		if (url != null) {

			try {

				if (WebsiteBuilder.getField().getURL().equals(url))
					refreshPage();
				else {
					if (WebsiteBuilder.getForwardBtn().getNavigationBtn().isEnabled()) {
						WebsiteBuilder.getForwardBtn().getNavigationBtn().setEnabled(false);
						qForward.clear();
					}

					super.setPage(url);
					necessary(url);

					WebsiteBuilder.getField().getURLBar().setText(url);
					WebsiteBuilder.getField().setURL(url);
					btn.pushToHistory(url);
				}

				
			} catch (IOException e) {

				/*
				 * If history url is unreadable, a JOptionPane pops up asking for the user to put in 
				 * a valid homepage.
				 * 
				 */
				super.setContentType("text/html");
				URL url2 = Main.class.getResource(
		                "/iconPack/sad.png");
				ImageIcon icon = new ImageIcon(url2);
				String input = (String) JOptionPane.showInputDialog(errPane,
						"Could not load: " + url + "\nPlease add a valid address", "Homepage cannot be accessed",
						JOptionPane.PLAIN_MESSAGE, icon, null, "");
				FileWriter fileWriter = new FileWriter(homepage);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				if (input == null) {
					/*
					 * If user does not do that, a default one is forced upon him/her.
					 * 
					 */

					String defaultUrl = "https://www.google.co.uk/";
					bufferedWriter.write(defaultUrl);
					JOptionPane.showMessageDialog(errPane, "Homepage will be set to default: https://www.google.co.uk/");
					bufferedWriter.close();
					setHomePage();
				} else {
					/*
					 * Otherwise user's homepage is loaded.
					 */

					bufferedWriter.write(input);
					bufferedWriter.close();
					setHomePage();
				}

			}
		} else {
			/*
			 * If the file is empty the user is automatically asked to add in a specific
			 * homepage.
			 * 
			 */

			super.setContentType("text/html");
			URL url2 = Main.class.getResource(
	                "/iconPack/sad.png");
			ImageIcon icon = new ImageIcon(url2);
			String input = (String) JOptionPane.showInputDialog(errPane,
					"No homepage specified." + "\nPlease add a valid address", "Homepage cannot be accessed",
					JOptionPane.PLAIN_MESSAGE, icon, null, "");
			FileWriter fileWriter = new FileWriter(homepage);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			if (input == null) {
				/*
				 * If user does not do that, a default homepage is forced upon him/her.
				 * 
				 */

				String defaultUrl = "https://www.google.co.uk/";
				bufferedWriter.write(defaultUrl);
				JOptionPane.showMessageDialog(errPane, "Webpage will be set to default: https://www.google.co.uk/");
				bufferedWriter.close();
				setHomePage();
			} else {
				/*
				 * Otherwise user's homepage is loaded.
				 */

				bufferedWriter.write(input);
				bufferedWriter.close();
				setHomePage();
			}

		}
	}

	/*
	 * Fetches the url from the url text box, loads it onto the JEditorPane, if it
	 * does not work throws an error. Also, bookmarks the page if it is needed.
	 */
	public void refreshPage() {

		String url;
		url = WebsiteBuilder.getField().getURL();
		try {

			fillStarIfNeeded(url);

			super.setContentType("text/html");
			super.setPage(url);
			WebsiteBuilder.getField().getURLBar().setText(url);
			WebsiteBuilder.getField().setURL(url);

		} catch (IOException e) {
			ErrorHandler.throwError(url);
		}
	}

	/*
	 * Goes through the bookmarks array and checks if the url passed
	 * to this method is already in it, if so, the bookmark star is filled.
	 */
	public void fillStarIfNeeded(String url) {

		boolean found = false;
		for (int i = 0; i < WebsiteBuilder.getFavouritesBtn().getFavouritePgs().size(); i++) {
			if (url.equals(WebsiteBuilder.getFavouritesBtn().getFavouritePgs().get(i))) {
				WebsiteBuilder.getFavouritesBtn().setStarStatus(true);
				WebsiteBuilder.getFavouritesBtn().setStarFilling("/iconPack/star-filled.png");
				found = true;
			}
		}
		if (found == false) {
			WebsiteBuilder.getFavouritesBtn().setStarStatus(false);
			WebsiteBuilder.getFavouritesBtn().setStarFilling("/iconPack/star.png");
		}

	}

	public ArrayList<String> getHistoryPgs() {
		return historyPgs;
	}

	public ArrayList<String> getHistoryPgsDate() {
		return dateOfHistoryPgs;
	}

	/*
	 * Checks if the history's dialog is open, if it is, it needs to be disposed of,
	 * a new one made to display the altered list that is displayed on it.
	 * 
	 */
	public void liveHistoryUpdate() {

		if (WebsiteBuilder.getHistoryList().getDialog().isVisible()) {
			WebsiteBuilder.getMenu().getHistoryList().getDialog().setVisible(false);
			WebsiteBuilder.getMenu().getHistoryList().getHistoryList(WebsiteBuilder.getPane().getHistoryPgs(),
					WebsiteBuilder.getPane().getHistoryPgsDate());
		}

	}

	/*
	 * Writes the history and its dates to a text file.
	 */
	public void writeToTextFile(ArrayList<String> historyPgs, ArrayList<String> dateList)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {

		historyPgs = WebsiteBuilder.getPane().getHistoryPgs();
		dateList = WebsiteBuilder.getPane().getHistoryPgsDate();
		File history = new File("History.txt");

		/*
		 * Checks if history exists, if not, creates a new one. Writes to it all latest
		 * array because it is written on when frame is getting closed.
		 * 
		 */
		try {
			if (!history.exists()) {
				history.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(history);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int i = 0; i < historyPgs.size(); i++) {
				bufferedWriter.write(dateList.get(i) + " " + historyPgs.get(i));
				bufferedWriter.newLine();
			}

			bufferedWriter.close();

		} catch (IOException e) {
			ErrorHandler.throwError("Could not load to history.");
		}

	}

	/*
	 * Reads from text file the dates and history.
	 * 
	 */
	public void readFromTextFile() throws UnsupportedEncodingException, FileNotFoundException, IOException {

		File history = new File("History.txt");

		/*
		 * If file exists at all it reads a line and splits it into 7 tokens, out of which
		 * 6 belong to the date and one to the url. Then adds them all to the date and history arrays.
		 * 
		 */
		if (history.exists()) {

			try {

				FileReader fileReader = new FileReader(history);

				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String url = bufferedReader.readLine();
				while (url != null) {

					String[] tokens = url.split(" ");

					if (tokens.length == 7) {
						dateOfHistoryPgs.add(tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3] + " "
								+ tokens[4] + " " + tokens[5]);
						historyPgs.add(tokens[6]);
					}

					url = bufferedReader.readLine();

				}

				bufferedReader.close();

			} catch (IOException e) {
				ErrorHandler.throwError("Could not read from history.");
			}

		}
	}

	/*
	 * Calls the necessary local methods that add up to the history
	 * and the date lists, fill the bookmark star if needed and update the history
	 * for the user if the history dialog is opened.
	 * 
	 */
	public void necessary(String url) {
		fillStarIfNeeded(url);
		historyPgs.add(url);
		dateOfHistoryPgs.add(Calendar.getInstance().getTime().toString());
		liveHistoryUpdate();
	}

}
