package walter.game;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class HangManTest extends TestCase {

	public void testValidLanguageConstructor() {
		try {
			Assert.assertNotNull(new HangMan(HangMan.ENGLISH, 1));
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Override
	protected TestResult createResult() {
		return super.createResult();
	}

	@org.junit.Test
	public void testInvalidLanguageConstructor() {
		try {
			new HangMan("PT", 1);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	public void testValidChancesConstructor() {
		try {
			Assert.assertNotNull(new HangMan(HangMan.ENGLISH, 0));
		} catch (Exception e) {
			Assert.fail();
		}
	}

	public void testInvalidChancesConstructor() {
		try {
			new HangMan(HangMan.ENGLISH, -1);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}
	public void testGenerateWord() {
		HangMan hangMan;
		try {
			hangMan = new HangMan(HangMan.ENGLISH, 1);
			Assert.assertNotNull(hangMan.generateWord());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	public void testgetNormalizedWord_E() {
		HangMan hangMan;
		try {
			hangMan = new HangMan(HangMan.ENGLISH, 1);
			hangMan.setWord("érable");
			Assert.assertEquals("ERABLE", hangMan.getNormalizedWord());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	public void testgetNormalizedWord_A() {
		HangMan hangMan;
		try {
			hangMan = new HangMan(HangMan.ENGLISH, 1);
			hangMan.setWord("Là");
			Assert.assertEquals("LA", hangMan.getNormalizedWord());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	public void testgetNormalizedWord_U() {
		HangMan hangMan;
		try {
			hangMan = new HangMan(HangMan.ENGLISH, 1);
			hangMan.setWord("Où");
			Assert.assertEquals("OU", hangMan.getNormalizedWord());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	public void testgetNormalizedWord_O() {
		HangMan hangMan;
		try {
			hangMan = new HangMan(HangMan.ENGLISH, 1);
			hangMan.setWord("córso");
			Assert.assertEquals("CORSO", hangMan.getNormalizedWord());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	public void testDecreaseChances() {
		HangMan hangMan;
		try {
			hangMan = new HangMan(HangMan.ENGLISH, 1);
			Assert.assertEquals(0, hangMan.decreaseChances());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	public void testPreventDecreaseChancesBelowZero() {
		HangMan hangMan;
		try {
			hangMan = new HangMan(HangMan.ENGLISH, 0);
			Assert.assertEquals(0, hangMan.decreaseChances());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
