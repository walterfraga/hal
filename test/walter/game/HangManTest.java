package walter.game;

import junit.framework.Assert;
import junit.framework.TestCase;

public class HangManTest extends TestCase {

	private HangMan hangMan;
	
	protected void setUp() throws Exception {
		super.setUp();
		hangMan = new HangMan(1);
	}

	public void testGenerateWord() {
		Assert.assertNotNull(hangMan.generateWord());
	}
}
