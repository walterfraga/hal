package walter.game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HangMan {
	
	public static final String UNSELECTED_CHAR = "-";
	public static final String ENGLISH = "EN";
	public static final String FRENCH = "FR";
	public static final String[] LETTERS = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private static final String FRENCH_ES = "éèêÉÈÊ";
	private static final String FRENCH_AS = "àâÀÂ";
	private static final String FRENCH_US = "ùûÙÛ";
	private static final String FRENCH_OS = "òôÒÔ";
	private String word;
	private int chances;
	private String language;
	private String normalizedWord;

	public String getWord() {
		return word;
	}

	public String replaceAccentLetters(String normalizedWord, String accentLetters, String replacedLetter) {
		for (int i = 0 ; i < accentLetters.length() ; i++) {
			normalizedWord = normalizedWord.replace(accentLetters.charAt(i) , replacedLetter.toCharArray()[0]);
		}
		return normalizedWord;
	}
	
	public String getNormalizedWord() {
		if (normalizedWord == null) {
			normalizedWord = word;
			normalizedWord = replaceAccentLetters(normalizedWord, FRENCH_ES, "E");
			normalizedWord = replaceAccentLetters(normalizedWord, FRENCH_AS, "A");
			normalizedWord = replaceAccentLetters(normalizedWord, FRENCH_US, "U");
			normalizedWord = replaceAccentLetters(normalizedWord, FRENCH_OS, "O");
			System.out.println("Normalized Word: " + normalizedWord);
		}
		return normalizedWord;
	}
	
	public HangMan(String pLanguage, int pChances) {
		chances = pChances;
		language = pLanguage;
		word = generateWord();
	}
	
	public int decreaseChances() {
		chances--;
		return chances;
	}

	public String generateWord() {
		List<String> words = readWords();		
		String pickedWord = pickWord(words).toUpperCase();
		System.out.println("Picked Word: " + pickedWord);
		return pickedWord;
	}
	
	private List<String> readWords() {
		List<String> words = new ArrayList<String>();
		BufferedReader br = null;
		try {
			InputStream res = HangMan.class.getResourceAsStream(language.equals(FRENCH)?"/words_fr.txt":"/words_en.txt");
			br = new BufferedReader(new InputStreamReader(res));
			String line = br.readLine();
			while (line != null) {
				words.add(line);
			    line = br.readLine();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return words;
	}
	
	private String pickWord(List<String> words) {
		int randomNum = ThreadLocalRandom.current().nextInt(0, words.size());
		return words.get(randomNum);
	}

	public int getChances() {
		return chances;
	}

	public void setChances(int chances) {
		this.chances = chances;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
