Included in this source are three classes. PokerJudge.java simply contains
a main method that takes two string arguments representing poker hands
and prints out a message declaring either hand 1 or hand 2 the victor.
Arguments must be in the form of a 10 character string, 2 characters per
card. Example: KHAS2D4C5D (King of hearts, ace of spades, 2 diamonds, 
4 of clubs, 5 of diamonds).
PokerCard.java represents a single card in a hand. PokerHand.java 
contains a class representing a 5 card hand of PokerCards and contains
all of the logic to score hands and compare them to each other. Most
of the logic can be found in a single method private int[] score(), I 
went back and forth on how to break this method up into smaller logical
pieces but found that including it all in one method made it somewhat 
easier to read through. I also included two classes continaing some unit
tests for PokerCard.java and PokerHand.java.

Michael Fallihee
fallimic@cs.washington.edu
