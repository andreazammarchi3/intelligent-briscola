+turn(B, N) <-
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

+!play_turn(B, N) : B = true & N = 2 <-
    .wait(2500);
    .print("Waiting for my turn...").

+!play_card_as_first : hand(Card1, Card2, Card3) & briscola_suit(BriscolaSuit) <-
    //.print("Trying to find the lowest card with ", Card1, ", ", Card2, ", ", Card3, ", ", BriscolaSuit);
    briscola.utils.jason.get_lowest_card(Card1, Card2, Card3, BriscolaSuit, LowestCard);
    briscola.utils.jason.get_hand_card_position_by_id(Card1, Card2, Card3, LowestCard, CardToPlay);
    play_card(CardToPlay).

+!play_card_as_second : hand(Card1, Card2, Card3) & played_card(PlayedCard) & briscola_suit(BriscolaSuit) <-
    //.print("Trying to find the winning card with ", Card1, ", ", Card2, ", ", Card3, ", ", BriscolaSuit, ", ", PlayedCard);
    briscola.utils.jason.get_winning_card(Card1, Card2, Card3, BriscolaSuit, PlayedCard, WinningCard);
    briscola.utils.jason.get_hand_card_position_by_id(Card1, Card2, Card3, WinningCard, CardToPlay);
    .print("Playing ", CardToPlay);
    play_card(CardToPlay).

-!play_turn(B, N) <-
    // do nothing
    .print("Waiting for my turn...").
