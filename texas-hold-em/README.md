# texas-hold-em
In context of Texas Hold`em Poker game, given a list of seven cards, selects the best five-card hand

Possible combinations, from highest to lowest:
* Straight flush: Five cards in sequence, all of the same suit. The Ace can be played either high or low. Between two straight flushes, the highest-ranking card wins; two straight flushes with the same sequence tie.
* Four of a kind: Four cards of the same rank, plus one unmatched card. Highest-ranking quads beat lower-ranking quads; if both are the same the highest-ranking kicker wins, otherwise there is a tie.
* Full house: Three matching cards of the same rank, and two matching cards of another. The hand with the highest-ranking triplet wins; if both are the same, the highest-ranking pair wins, otherwise there is a tie.
* Flush: Five cards of the same suit. Two flushes are compared by the ranks of their cards; the highest-ranking card wins, in the event of a tie the second highest-ranking card wins, and so on until a difference is found. If both flushes have cards of the same rank, they tie.
* Straight: Five cards in sequence. The Ace may be used either high or low. Two straights are ranked by their highest cards; if their highest cards are equal, they tie.
* Three of a kind: Three cards of the same rank, plus two unmatched cards. The highest-ranking triplet wins; if they are the same, the kickers are compared to break the tie.
* Two pair: Two cards with matched rank, plus two more cards of matched rank, plus one unmatched card. Two-pair hands are ranked on the highest pair, then the lowest pair, and finally the kicker.
* One pair: Two cards with matched rank, plus three more unmatched cards. The highest-ranking pair wins, with the three kickers used to break ties.
* High card: Five unmatched cards. Hands are ranked on their highest card, followed by their second-highest card, and so on.