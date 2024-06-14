/* Normal bot that plays a simple strategy:
- As first player, plays the card with the lowest value, if possible with a different suit from the briscola.
- As second player, plays the card with the lowest value that is winning on the card played by the opponent. 
  If there are no winning cards, plays the lowest card in hand.
*/

/* PERCEPTS
- hand(List of cards) - Perceived from the environment
- turn(BotTurn, NPlayedCards) - Indicates if it's the bot's turn and the number of cards played in the round
- bot_level(Level) - The bot's level of intelligence
- played_card(Card) - The card played by the opponent
- briscola_suit(Suit) - The suit of the briscola card */

/* Percept of the hand cards */
+hand(List) : bot_level(normal) <- 
    .print("Current hand: ", List).

/* Percept of the turn. If it's the bot's turn, play the turn. */
+turn(BotTurn, NPlayedCards) : bot_level(normal) & BotTurn = true <- 
    /* GOAL: Play the turn */
    !play_turn(NPlayedCards).


/* PLANS */
/* Play the turn as first.*/
+!play_turn(NPlayedCards) : NPlayedCards = 0 & hand(Cards) & not .empty(Cards) <- 
    .wait(2500);
    .print("My turn! Playing as first.");
    /* Get first the less value cards in hand with a different suit from the briscola. If only briscola cards, get all of them. */
    !get_less_value_cards(Cards, LessValueCards);
    /* Then if there are cards with same lowest value, play the one with the lowest rank. */
    !get_lowest_rank_card(LessValueCards, LowestCard);
    !play_a_card(LowestCard).

/* Get the card with the lowest value, if possible its suit is not the same as the briscola suit. */
+!get_less_value_cards(List, LessValueCards) : not .empty(List) & briscola_suit(BriscolaSuit) <- 
    /* Collect all non-Briscola cards. */
    .findall(card(Id, Suit, Rank, Value), 
             (.member(card(Id, Suit, Rank, Value), List) & not Suit = BriscolaSuit), 
             NonBriscolaCards);
    /* Check if there are any non-Briscola cards collected. */
    if (.empty(NonBriscolaCards)) {
        /* Determine the card(s) with the lowest value. */
        .findall(Value, .member(card(_, _, _, Value), List), Values);
        .min(Values, MinValue);
        .findall(card(Id, Suit, Rank, MinValue), .member(card(Id, Suit, Rank, MinValue), List), LessValueCards);
    } else {
        /* Determine the card(s) with the lowest value. */
        .findall(Value, .member(card(_, _, _, Value), NonBriscolaCards), Values);
        .min(Values, MinValue);
        .findall(card(Id, Suit, Rank, MinValue), .member(card(Id, Suit, Rank, MinValue), NonBriscolaCards), LessValueCards);
    }.

/* Choose between the cards with the lowest value the one with the lowest rank. */
+!get_lowest_rank_card(LessValueCards, LowestCard) : .length(LessValueCards, Length) & Length > 1 <-
    .findall(Rank, .member(card(_, _, Rank, _), LessValueCards), Ranks);
    .min(Ranks, MinRank);
    .findall(card(Id, Suit, MinRank, Value), .member(card(Id, Suit, MinRank, _), LessValueCards) & card(Id, Suit, MinRank, _) = Card, [LowestCard|_]).

/* If there is only one card with the lowest value, return it. */
+!get_lowest_rank_card(LessValueCards, LowestCard) <-
    .nth(0, LessValueCards, LowestCard).

/* Play the turn as second. */
+!play_turn(NPlayedCards) : NPlayedCards = 1 & played_card(PlayedCard) & hand(Cards) & briscola_suit(BriscolaSuit) <- 
    .print("My turn! Playing as second. Opponent played: ", PlayedCard);
    /* Create a list of cards in hand that are higher than the played card. */
    .findall(card(Id, Suit, Rank, Value), 
        .member(card(Id, Suit, Rank, Value), Cards) & briscola.utils.jason.get_higher_card(PlayedCard, card(Id, Suit, Rank, Value), BriscolaSuit, Result) & Result = true,
        HigherCards);
    .print("Higher cards: ", HigherCards);
    /* If there are higher cards, play the one with the lowest value. */
    if (.empty(HigherCards)) {
        /* If there are no higher cards, play the lowest card in hand. */
        !get_less_value_cards(Cards, LessValueCards);
    } else {
        /* Get the card with the lowest value from the higher cards. */
        !get_less_value_cards(HigherCards, LessValueCards);
    };
    !get_lowest_rank_card(LessValueCards, LowestCard);
    !play_a_card(LowestCard).

/* If failed to play the turn, the match ended. */
-!play_turn(NPlayedCards) <- 
    .print("Match ended.").


/* Play a specific card - action sent to the environment. */
+!play_a_card(card(Id, Suit, Rank, Value)) : hand(List) <- 
    .print("Playing card: ", card(Id, Suit, Rank, Value));
    /* play_card(X) want X as the index of the card in the hand. */
    .nth(Index, List, card(Id, Suit, Rank, Value));
    play_card(Index).

/* If the card to play is not in the hand, print an error message. */
-!play_a_card(card(Id, Suit, Rank, Value)) <- 
    .print("Error: card not found in hand.").

