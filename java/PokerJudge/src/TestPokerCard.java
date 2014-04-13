import static org.junit.Assert.*;

import org.junit.Test;


public class TestPokerCard {
	PokerCard aceHeart = new PokerCard('A', 'H');
	PokerCard jackSpade = new PokerCard('J', 'S');
	PokerCard twoDiamond = new PokerCard('2', 'D');
	PokerCard fourSpade = new PokerCard('4', 'S');
	
	@Test
	public void TestCtor(){
		PokerCard success = new PokerCard('K', 'H');
		assertTrue(success != null);
		
		try {
			//Should throw exception
			PokerCard fail = new PokerCard('Z', 'Q');
			assertTrue(false);
		} catch (IllegalArgumentException e){
		}
	}
	
	@Test
	public void TestGetRank(){
		assertEquals(aceHeart.getRank(), 13);
		assertEquals(twoDiamond.getRank(), 1);
		assertEquals(fourSpade.getRank(), 3);
		assertEquals(jackSpade.getRank(), 10);
	}
	
	@Test
	public void TestEquals(){
		PokerCard aceHeart2 = new PokerCard('A', 'H');
		
		assertTrue(aceHeart.equals(aceHeart2));
		assertTrue(aceHeart2.equals(aceHeart));
		assertTrue(aceHeart2.equals(aceHeart2));
		
		assertFalse(aceHeart.equals(jackSpade));
		assertFalse(twoDiamond.equals(fourSpade));
	}
	
	@Test
	public void TestHashCode(){
		PokerCard aceHeart2 = new PokerCard('A', 'H');
		
		assertTrue(aceHeart.equals(aceHeart2));
		assertEquals(aceHeart.hashCode(), aceHeart2.hashCode());
	}
	
	@Test
	public void TestCompareTo(){
		PokerCard fourClub = new PokerCard('4','C');
		
		//suits shouldn't affect order
		assertTrue(fourClub.compareTo(fourSpade) == 0);
		assertTrue(fourSpade.compareTo(fourClub) == 0);
		
		assertTrue(aceHeart.compareTo(jackSpade) > 0);
		assertTrue(twoDiamond.compareTo(aceHeart) < 0);
	}

}
