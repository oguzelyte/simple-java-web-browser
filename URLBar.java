package websitePack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.io.IOException;

import javax.swing.JTextField;

/*
 * 
 * @author: Olivija Guzelyte (160421859)
 * @version: 03/05/2017
 * 
 */

public class URLBar {

	private JTextField bar = new JTextField();
	private String url = new String();

	public URLBar() {

		/*
		 * Sets a placeholder for the url bar. If there
		 * is a focus on the bar and the placeholder is set, 
		 * then the mouse just selects all url in the text bar, otherwise 
		 * it sets the url to nothing and lets the user type.
		 * If focus is lost from the bar and it is empty, the placeholder
		 * gets set.
		 * 
		 */	
		bar.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (bar.getText().equals("Enter url here")) {
					bar.setText("");
					bar.setForeground(Color.BLACK);
				} else {
					url = bar.getText();
					bar.selectAll();
				}

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (bar.getText().isEmpty()) {
					bar.setForeground(Color.GRAY);
					bar.setText("Enter url here");
				}
			}
		});

		/*
		 * On enter click the url gets read from the text bar
		 * and a method triggered in the main pane that extends
		 * JEditorPane, otherwise a JOptionPane error is thrown.
		 * 
		 */	
		bar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					url = bar.getText();
					WebsiteBuilder.getPane().setPage(url);
				} catch (IOException e1) {
					ErrorHandler.throwError(url);
				}

			}

		});

	}

	public JTextField getURLBar() {
		bar.setVisible(true);
		return bar;

	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}
}
