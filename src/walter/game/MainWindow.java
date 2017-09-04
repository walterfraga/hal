package walter.game;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int MAX_HORIZONTAL_SIZE = 480;
	private static final int MAX_VERTICAL_SIZE = 272;
	private HangMan hangMan;
	private JLabel wordLabel;
	private JLabel chancesLabel;
	private JPanel buttonPanel;
	private CheckboxGroup languagesCheckboxGroup;
	private JComboBox<Integer> chancesCombo;
	
	
	MainWindow() {
		initializeFrame();		
		initializeTopSection();		
		initializeMiddleSection();		
		initializeButtonPanel();
		initializeLettersButtons();
		this.setVisible(true);
	}

	private void initializeFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(MAX_HORIZONTAL_SIZE, MAX_VERTICAL_SIZE);
	}

	private void initializeTopSection() {
		JPanel topSection = new JPanel();
		this.add(topSection, BorderLayout.NORTH);
		initializeStartButton(topSection);
		initializeLanuageSelection(topSection);
		initializeChances(topSection);
	}
	
	private void initializeChances(JPanel topSection) {
		Integer[] chances = new Integer[]{1,2,3,4,5,6,7,8,9,10};
		chancesCombo = new JComboBox<Integer>(chances);
		chancesCombo.setSelectedIndex(4);
		topSection.add(chancesCombo);
	}

	private void initializeStartButton(JPanel topSection) {
		JButton startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    startGame();
			  }
		} );
		topSection.add(startButton);
	}

	private void initializeLanuageSelection(JPanel topSection) {
	   languagesCheckboxGroup = new CheckboxGroup();
       Checkbox englishButton = new Checkbox(HangMan.ENGLISH, languagesCheckboxGroup, true);
       Checkbox frenchButton = new Checkbox(HangMan.FRENCH, languagesCheckboxGroup, true);
       topSection.add(englishButton);
       topSection.add(frenchButton);
	}

	private void initializeMiddleSection() {
		initializeWordLabel();
		initializeChancesLabel();
	}
	
	private void initializeWordLabel() {
		wordLabel = new JLabel();
		wordLabel.setFont(new Font("Serif", Font.BOLD, 28));
		wordLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(wordLabel);
	}
	
	private void initializeChancesLabel() {
		chancesLabel = new JLabel();
		chancesLabel.setFont(new Font("Serif", Font.BOLD, 28));
		chancesLabel.setForeground(Color.red);
		chancesLabel.setHorizontalAlignment(JLabel.CENTER);
		chancesLabel.setVerticalAlignment(JLabel.CENTER);
		this.add(chancesLabel, BorderLayout.EAST);
	}

	private void initializeButtonPanel() {
		buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new  FlowLayout());
		buttonPanel.setPreferredSize(new Dimension(MAX_HORIZONTAL_SIZE, 100));		
	}
	
	private void initializeLettersButtons() {
		for (String letter : HangMan.LETTERS) {			
			final JButton button = new JButton(letter);
			button.setEnabled(false);
			buttonPanel.add(button);
			button.addActionListener(new ActionListener() { 
				  public void actionPerformed(ActionEvent e) { 
				    handleLetterSelected(button);
				  }
			} );
		}
	}
	
	private void startGame() {
		hangMan = new HangMan(languagesCheckboxGroup.getCurrent().getLabel(), ((Integer)chancesCombo.getSelectedItem()).intValue());
		chancesLabel.setText(String.valueOf(hangMan.getChances()));
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
		}else {
			decreaseChances();
			if (hangMan.getChances() == 0) {
				disableAllLetterButtons();
				displayAnswer();
			}
		}
	}

	private void displayAnswer() {
		wordLabel.setText(hangMan.getWord());
	}

	private void decreaseChances() {
		chancesLabel.setText(String.valueOf(hangMan.decreaseChances()));
	}

	private void enableAllLetterButtons() {
		enableDisableAllLetterButtons(true);
	}
	
	private void disableAllLetterButtons() {
		enableDisableAllLetterButtons(false);
	}
	
	private void enableDisableAllLetterButtons(boolean bool) {
		Component[] comp = buttonPanel.getComponents();
	    for (int i = 0 ;i < comp.length ; i++) {
	        if (comp[i] instanceof JButton) {
	           ((JButton)comp[i]).setEnabled(bool);
	        }
	    }
	}

	private boolean isSelectedLetterInWord(String letter) {
		return hangMan.getNormalizedWord().contains(letter);
	}
	
	private boolean isWordFound() {
		return !wordLabel.getText().contains(HangMan.UNSELECTED_CHAR);
	}
	
	private void displaySelectedLetterInWord(String letter) {
		char[] wordLetters = hangMan.getNormalizedWord().toUpperCase().toCharArray();
		char letterFound = letter.toCharArray()[0];
		for(int i = 0 ; i < wordLetters.length ; i++) {
			if (wordLetters[i] == letterFound) {
				char[] text = wordLabel.getText().toCharArray();
				text[i] = hangMan.getWord().toUpperCase().toCharArray()[i];
				wordLabel.setText(new String(text));
			}
		}
	}
}
