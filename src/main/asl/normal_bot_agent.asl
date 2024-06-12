+turn(BotTurn, NPlayedCards) : match_started(normal) <-
    !play_turn(BotTurn, NPlayedCards).

+!play_turn(BotTurn, NPlayedCards) : BotTurn = false <-
    .print("Waiting for my turn...").

+!play_turn(BotTurn, NPlayedCards) : BotTurn = true & NPlayedCards = 0 <-
    .wait(2500);
    .print("My turn! Playing as first.");
    !play_card_as_first.

+!play_turn(BotTurn, NPlayedCards) : BotTurn = true & NPlayedCards = 1 <-
    .print("My turn! Playing as second.");
    !play_card_as_second.

+!play_card_as_first : hand(Card0, Card1, Card2) & briscola_suit(BriscolaSuit) <-
    briscola.utils.jason.play_lowest_card(Card0, Card1, Card2, BriscolaSuit, LowestCard);
    play_card(LowestCard).

+!play_card_as_second : hand(Card0, Card1, Card2) & played_card(PlayedCard) & briscola_suit(BriscolaSuit) <-
    briscola.utils.jason.play_highest_card(Card0, Card1, Card2, BriscolaSuit, PlayedCard, HighestCard);
    play_card(HighestCard).
