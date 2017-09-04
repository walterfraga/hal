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
	private String word;
	private int chances;
	private String language;

	public String getWord() {
		return word;
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
		return pickWord(words).toUpperCase();
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
