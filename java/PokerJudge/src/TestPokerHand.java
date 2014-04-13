import static org.junit.Assert.*;

import org.junit.Test;


public class TestPokerHand {
	public PokerHand royalFlush = new PokerHand("AHKHQHJHTH");
	public PokerHand royalFlush2 = new PokerHand("ADKDQDJDTD");
	public PokerHand badHand = new PokerHand("5S3D7C4C2H");
	public PokerHand onePair = new PokerHand("2H2C3S5D9C");
	public PokerHand onePair2 = new PokerHand("4D4S6C8DAH");
	public PokerHand threeOfKind = new PokerHand("2H2C2S5D9C");
	public PokerHand fourOfKind = new PokerHand("2H2C2S2D9C");
	
	
	@Test
	public void TestCtor(){
		try{
			PokerHand duplicates = new PokerHand("AH2S3C4DAH");
			assertTrue(false);
		} catch (IllegalArgumentException e){}
	}
	
	@Test
	public void TestCompareTo(){
		assertTrue(royalFlush.compareTo(badHand) > 0);
		assertTrue(badHand.compareTo(royalFlush) < 0);
		assertTrue(onePair2.compareTo(onePair) > 0);
		assertTrue(fourOfKind.compareTo(threeOfKind) > 0);
		assertTrue(royalFlush.compareTo(royalFlush2) == 0);
		
	}
}
