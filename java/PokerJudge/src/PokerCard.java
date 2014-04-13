
/**
 * PokerCard represents a card for a standard poker game. Each card
 * has a value and a suit. Values range from 2 - Ace, Suits include
 * heats, spades, clubs, and diamonds. Each card also has an
 * associated rank, 2's being rank 1, 3's rank 2, up to aces which 
 * have rank 13.
 */
public class PokerCard implements Comparable<PokerCard> {
	public static final char HEARTS 	= 'H';
	public static final char SPADES 	= 'S';
	public static final char DIAMONDS 	= 'D';
	public static final char CLUBS		= 'C';
	public static final char TWO 		= '2';
	public static final char THREE 		= '3';
	public static final char FOUR 		= '4';
	public static final char FIVE 		= '5';
	public static final char SIX 		= '6';
	public static final char SEVEN 		= '7';
	public static final char EIGHT 		= '8';
	public static final char NINE 		= '9';
	public static final char TEN 		= 'T';
	public static final char JACK 		= 'J';
	public static final char QUEEN 		= 'Q';
	public static final char KING 		= 'K';
	public static final char ACE 		= 'A';
	
	public static final char[] CARD_VALUES = 
		{'2','3', '4','5','6','7','8','9','T','J','Q','K', 'A'};

	private static final String suits = "HSDC";
	private static final String values = "23456789TJQKA";
	private char value;
	private char suit;

	
	/**
	 * Constructs a PokerCard given a character representing
	 * the value and a character representing the suit
	 * @param value
	 * @param suit
	 * @throws IllegalArgumentException if the value
	 * or suit are not legal values (as defined by the 
	 * static members of this class).
	 */
	public PokerCard(char value, char suit) {
		if (suits.indexOf(suit) < 0 || values.indexOf(value) < 0) {
			throw new IllegalArgumentException("Invalid card value or suit");
		}
		this.value = value;
		this.suit = suit;
	}
	
	/**
	 * Returns a character representing the suit of this PokerCard
	 * @return returns 'H' for hearts, 'D' for diamonds, 'C' for
	 * clubs, and 'S' for spades.
	 */
	public char getSuit(){
		return suit;
	}
	
	/**
	 * Returns a character representing the value of this PokerCard.
	 * @return '2' - '9' for numbered card, 'T', 'J', 'Q', 'K', 'A' for
	 * face cards.
	 */
	public char getValue(){
		return value;
	}

	/**
	 * Returns an integer representing the rank of this PokerCards
	 * Aces have rank 13, Kings 12, down to 2's with rank 1
	 */
	public int getRank(){
		return values.indexOf(value) + 1;
	}

	/**
	 * Compares this PokerCard to the give other PokerCard
	 * @return Returns a positive integer if this PokerCard
	 * has a higher rank than other. Returns a negative integer
	 * if other has a higher rank than this poker card.
	 * Returns 0 if the two cards have the same rank.
	 */
	@Override
	public int compareTo(PokerCard other) {
		if (other == null){
			return 1;
		}
		return getRank() - other.getRank();
	}
	
	/**
	 * Returns an integer representing this PokerCard.
	 * If two cards are equal as determined by equals(Object obj)
	 * then they will return the same hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + suit;
		result = prime * result + value;
		return result;
	}

	/**
	 * Returns true if this PokerCard is equal to
	 * the give Object. Returns false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokerCard other = (PokerCard) obj;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	public String toString(){
		return value + "" +  suit;
	}
}