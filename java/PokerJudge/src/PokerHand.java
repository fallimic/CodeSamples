import java.util.HashSet;
import java.util.Set;

/**
 * PokerHand represents a five card poker hand. PokerHand is composed
 * of a set of five PokerCard objects. Standard poker scoring applies.
 */
public class PokerHand implements Comparable<PokerHand>{
	
	public static final int HAND_SIZE = 5;
	private static final int STRAIGHT_FLUSH_VALUE = 9;
	private static final int FOUR_OF_KIND_VALUE = 8;
	private static final int FULL_HOUSE_VALUE = 7;
	private static final int FLUSH_VALUE = 6;
	private static final int STRAIGHT_VALUE = 5;
	private static final int THREE_OF_KIND_VALUE = 4;
	private static final int TWO_PAIR_VALUE = 3;
	private static final int ONE_PAIR_VALUE = 2;
	private static final int HIGH_CARD_VALUE = 1;
	
	
	
	private Set<PokerCard> hand;
	
	/**
	 * Constructs a PokerHand give a string representing
	 * the cards in the hand
	 * @param cards the string representing the 5 card hand.
	 * Each card is represented by 2 characters.
	 * @throws IllegalArgumentException if the string is not
	 * 10 characters in length or if it contains duplicate cards
	 * or is otherwise improperly formatted.
	 */
	public PokerHand(String cards){
		if (cards.length() != 10){
			throw new IllegalArgumentException();
		}
		hand = new HashSet<PokerCard>();
		for (int i = 0; i < HAND_SIZE; i++){
			char value = cards.charAt(2*i);
			char suit = cards.charAt(2*i + 1);
			hand.add(new PokerCard(value, suit));
		}
		
		//check for duplicate cards
		if (hand.size() != HAND_SIZE){
			throw new IllegalArgumentException("CHEATER! No duplicate cards allowed");
		}
		
	}
	
	/**
	 * Compares this to another PokerHand.
	 * Returns a positive integer if this PokerHand
	 * beats other, returns a negative integer if
	 * other beats this PokerHand. Returns 0 if this 
	 * and other tie.
	 */
	public int compareTo(PokerHand other){
		if (other == null){
			return 1;
		}
		
		//Holds the score for the hand
		int[] score = computeScore();
		int[] otherScore = other.computeScore();
		
		for (int i= 0; i < score.length; i++){
			if (score[i] > otherScore[i]){
				return 1;
			} else if (score[i] < otherScore[i]){
				return -1;
			}
		}
		
		return 0;		
	}
	
	
	//Returns a int array of the score of the hand
	//index 0 holds the type of hand (determined by HAND_VALUES)
	//indexes 1-5 hold tie breaker scores for each hand type
	//depending on the hand these may not be used
	private int[] computeScore(){
		int[] score = new int[6];
		int[] rankCounts = new int[PokerCard.CARD_VALUES.length + 1];
		int[] highestSingles = new int[5];
		
		int bestSetLen = 1, bestSetLen2 = 1;
		int bestSetRank = 0, bestSetRank2 = 0;
		
		for (PokerCard c : hand){
			rankCounts[c.getRank()]++;
		}
		
		int j = 0;
		for (int i = 13; i > 0; i--){
			if (rankCounts[i] == 1){
				//keep track of highest single cards
				//in order
				highestSingles[j] = i;
				j++;
			}
			if (rankCounts[i] > bestSetLen){
				bestSetLen2 = bestSetLen;
				bestSetRank2 = bestSetRank;
				bestSetLen = rankCounts[i];
				bestSetRank = i;
			} else if (rankCounts[i] > bestSetLen2){
				bestSetLen2 = rankCounts[i];
				bestSetRank2 = i;
			}
		}
		
		if (bestSetLen == 4){
			//four of a kind
			score[0] = FOUR_OF_KIND_VALUE;
			score[1] = bestSetRank;
			score[2] = 0; //next highest card
		} else if (bestSetLen == 3){
			//we have a 3 of a kind
			if (bestSetLen2 == 2){
				//full house
				score[0] = FULL_HOUSE_VALUE;
				score[1] = bestSetRank;
				score[2] = bestSetRank2;
			} else {
				//just 3 of a kind
				score[0] = THREE_OF_KIND_VALUE;
				score[1] = bestSetRank;
				score[2] = highestSingles[0];
				score[3] = highestSingles[1];
			}
		} else if (bestSetLen == 2){
			//our best is a pair
			if (bestSetLen2 == 2){
				//two pair
				score[0] = TWO_PAIR_VALUE;
				score[1] = bestSetRank;
				score[2] = bestSetRank2;
				score[3] = highestSingles[0]; //remaining card
			} else {
				//one pair
				score[0] = ONE_PAIR_VALUE;
				score[1] = bestSetRank;
				score[2] = highestSingles[0];
				score[3] = highestSingles[1];
				score[4] = highestSingles[2];
			}
		} else {
			//we have no sets
			
			//do we have a straight?
			boolean haveStraight = false;
			int straightRank = 0;
			//highest rank for start of straight is 10 (rank 9)
			for (int i = 1; i <= 9; i++){
				if (rankCounts[i] == 1 && rankCounts[i+1] == 1 &&
						rankCounts[i+2] == 1 && rankCounts[i+3] == 1 &&
						rankCounts[i+4] == 1){
					haveStraight = true;
					straightRank = i+4;
				}
			}
			
			//do we have a flush?
			boolean haveFlush = true;
			char handSuit = '\0';
			for(PokerCard card : hand){
				if (handSuit == '\0'){
					//first card
					handSuit = card.getSuit();
				}
				if (handSuit != card.getSuit()){
					haveFlush = false;
					break;
				}
			}
			
			if (haveStraight){
				if (haveFlush){
					//straight flush
					score[0] = STRAIGHT_FLUSH_VALUE;
					score[1] = straightRank;
				} else {
					//just straight
					score[0] = STRAIGHT_VALUE;
					score[1] = straightRank;
				}
			} else if (haveFlush){
				score[0] = FLUSH_VALUE;
				score[1] = highestSingles[0];
			} else {
				//we have nothing but highCard
				score[0] = HIGH_CARD_VALUE;
				score[1] = highestSingles[0];
				score[2] = highestSingles[1];
				score[3] = highestSingles[2];
				score[4] = highestSingles[3];
				score[5] = highestSingles[4];
			}
		}
		return score;
	}	
}
