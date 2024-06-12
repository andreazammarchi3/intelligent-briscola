+turn(BotTurn, NPlayedCards) : match_started(stupid) <-
    !play_turn(BotTurn, NPlayedCards).

+!play_turn(BotTurn, NPlayedCards) : BotTurn = false <-
    .print("Waiting for my turn...").

+!play_turn(BotTurn, NPlayedCards) : BotTurn = true & NPlayedCards = 0 <-
    .wait(2500);
    .print("My turn! Playing as first.");
    !play_rand_card.

+!play_turn(BotTurn, NPlayedCards) : BotTurn = true & NPlayedCards = 1 <-
    .print("My turn! Playing as second.");
    !play_rand_card.

+!play_rand_card : hand(Card0, Card1, Card2) & not Card1 = -1 & not Card2 = -1 <-
    briscola.utils.jason.rand_int(0, 2, I);
    play_card(I).

+!play_rand_card : hand(Card0, Card1, Card2) & not Card1 = -1 & Card2 = -1 <-
    briscola.utils.jason.rand_int(0, 1, I);
    play_card(I).

+!play_rand_card : hand(Card0, Card1, Card2) <-
    play_card(0).

-!play_rand_card <-
    .print("No cards in hand!").

    
