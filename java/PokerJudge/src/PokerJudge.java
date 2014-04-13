
public class PokerJudge {

	/**
	 * Given two strings representing poker hands as arguments,
	 * prints out a message declaring the victor. 
	 */
	public static void main(String[] args){
		if (args.length != 2){
			Usage();
		}
		
		PokerHand hand1 = new PokerHand(args[0]);
		PokerHand hand2 = new PokerHand(args[1]);
		
		if (hand1.compareTo(hand2) > 0){
			System.out.println("Hand 1 beats Hand 2!");
		} else if (hand1.compareTo(hand2) < 0){
			System.out.println("Hand 2 beats Hand 1!");
		} else {
			System.out.println("It's a tie!");
		}
	}
	
	private static void Usage(){
		System.out.println("Usage: PokerJudge [hand1] [hand2]");
		System.exit(1);
	}
	
}
