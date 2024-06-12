+turn(B, N) : match_started(normal) <-
    !play_turn(B, N).

+!play_turn(B, N) : B = false <-
    .print("Waiting for my turn...").

+!play_turn(B, N) : B = true & N = 0 <-
    .wait(2500);
    .print("My turn! Playing as first.");
    !play_card_as_first.

+!play_turn(B, N) : B = true & N = 1 <-
    .print("My turn! Playing as second.");
    !play_card_as_second.

+!play_card_as_first : hand(Card1, Card2, Card3) & briscola_suit(BriscolaSuit) <-
    briscola.utils.jason.play_lowest_card(Card1, Card2, Card3, BriscolaSuit, LowestCard);
    play_card(LowestCard).

+!play_card_as_second : hand(Card1, Card2, Card3) & played_card(PlayedCard) & briscola_suit(BriscolaSuit) <-
    briscola.utils.jason.play_highest_card(Card1, Card2, Card3, BriscolaSuit, PlayedCard, HighestCard);
    play_card(HighestCard).
