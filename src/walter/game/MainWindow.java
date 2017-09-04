package walter.game;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private HangMan hangMan;
	private JLabel wordLabel;
	private JPanel buttonPanel;
	private static final int MAX_HORIZONTAL_SIZE = 480;
	private static final int MAX_VERTICAL_SIZE = 272;
	
	MainWindow() {
		hangMan = new HangMan();
		initializeFrame();		
		initializeStartButton();		
		initializeWordLabel();		
		initializeButtonPanel();
		initializeLettersButtons();
		this.setVisible(true);
	}

	private void initializeFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(MAX_HORIZONTAL_SIZE, MAX_VERTICAL_SIZE);
	}

	private void initializeStartButton() {
		JButton startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    startGame();
			  }
		} );
		this.add(startButton, BorderLayout.NORTH);
	}

	private void initializeWordLabel() {
		wordLabel = new JLabel();
		wordLabel.setFont(new Font("Serif", Font.BOLD, 14));
		wordLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(wordLabel, BorderLayout.CENTER);
	}

	private void initializeButtonPanel() {
		buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new  FlowLayout());
		buttonPanel.setPreferredSize(new Dimension(MAX_HORIZONTAL_SIZE, 100));		
	}
	
	private void initializeLettersButtons() {
		for (String letter : hangMan.getAvailableLetters()) {			
			final JButton button = new JButton(letter);
			buttonPanel.add(button);
			button.addActionListener(new ActionListener() { 
				  public void actionPerformed(ActionEvent e) { 
				    handleLetterSelected(button);
				  }
			} );
		}
	}
	
	private void startGame() {
		hangMan = new HangMan();
		StringBuilder text = new StringBuilder();
		for(int i = 0 ; i < hangMan.getWord().length() ; i++) {
			text.append(HangMan.UNSELECTED_CHAR);
		}
		wordLabel.setText(text.toString());
		enableAllLetterButtons();
	}
	
	public static void main(String args[]) {
		new MainWindow();
	}

	private void handleLetterSelected(JButton button) {
		String letter = button.getText();
		button.setEnabled(false);
		if (isSelectedLetterInWord(letter)) {
			displaySelectedLetterInWord(letter);
			if (isWordFound()) {
				disableAllLetterButtons();
			}
		}
	}

	private void enableAllLetterButtons() {
		enableDisableAllLetterButtons(true);
	}
	
	private void disableAllLetterButtons() {
		enableDisableAllLetterButtons(false);
	}
	
	private void enableDisableAllLetterButtons(boolean bool) {
		Component[] comp = buttonPanel.getComponents();
	    for (int i = 0;i<comp.length;i++) {
	        if (comp[i] instanceof JButton) {
	           ((JButton)comp[i] ).setEnabled(bool);
	        }
	    }
	}

	private boolean isSelectedLetterInWord(String letter) {
		return hangMan.getWord().contains(letter);
	}
	
	private boolean isWordFound() {
		return !wordLabel.getText().contains(HangMan.UNSELECTED_CHAR);
	}
	
	private void displaySelectedLetterInWord(String letter) {
		char[] wordLetters = hangMan.getWord().toCharArray();
		char letterFound = letter.toCharArray()[0];
		for(int i = 0 ; i < wordLetters.length ; i++) {
			if (wordLetters[i] == letterFound) {
				char[] text = wordLabel.getText().toCharArray();
				text[i] = letterFound;
				wordLabel.setText(new String(text));
			}
		}
	}
}
