// An agent to play Briscola
// This bot plays against a human in Briscola, interacting directly with a Kotlin environment

/* Beliefs and Plans */

// Percepts
// hand(List of cards) - Each card is now "id_suit_rank_value"
// turn(BotTurn, NPlayedCards) - Indicates if it's the bot's turn and the number of cards played
// bot_level(Level) - The bot's level of intelligence
// played_card(Card) - The card played by the opponent
// briscola_suit(Suit) - The suit of the briscola card

// Reacting to the game state
+hand(List) : bot_level(normal) <- 
    .print("Current hand: ", List).

+turn(BotTurn, NPlayedCards) : bot_level(normal) & BotTurn = true <- 
    !play_turn(NPlayedCards).

// Decide on the action based on the number of played cards
+!play_turn(NPlayedCards) : NPlayedCards = 0 & hand(Cards) & not .empty(Cards) <- 
    .wait(2500);
    .print("My turn! Playing as first.");
    // get first the less value cards in hand with a different suit from the briscola. If only briscola cards, get all of them.
    !get_less_value_cards(Cards, LessValueCards);
    // then if there are cards with same lowest value, play the one with the lowest rank.
    !get_lowest_rank_card(LessValueCards, LowestCard);
    !play_a_card(LowestCard).

// Get the card with the lowest value, if possible its suit is not the same as the briscola suit
+!get_less_value_cards(List, LessValueCards) : not .empty(List) & briscola_suit(BriscolaSuit) <- 
    // Collect all non-Briscola cards
    .findall(card(Id, Suit, Rank, Value), 
             (.member(card(Id, Suit, Rank, Value), List) & not Suit = BriscolaSuit), 
             NonBriscolaCards);
    // Check if there are any non-Briscola cards collected
    if (.empty(NonBriscolaCards)) {
        // Determine the card(s) with the lowest value
        .findall(Value, .member(card(_, _, _, Value), List), Values);
        .min(Values, MinValue);
        .findall(card(Id, Suit, Rank, MinValue), .member(card(Id, Suit, Rank, MinValue), List), LessValueCards);
    } else {
        // Determine the card(s) with the lowest value
        .findall(Value, .member(card(_, _, _, Value), NonBriscolaCards), Values);
        .min(Values, MinValue);
        .findall(card(Id, Suit, Rank, MinValue), .member(card(Id, Suit, Rank, MinValue), NonBriscolaCards), LessValueCards);
    }.

// Choose between the cards with the lowest value the one with the lowest rank
+!get_lowest_rank_card(LessValueCards, LowestCard) : .length(LessValueCards, Length) & Length > 1 <-
    .findall(Rank, .member(card(_, _, Rank, _), LessValueCards), Ranks);
    .min(Ranks, MinRank);
    .findall(card(Id, Suit, MinRank, Value), .member(card(Id, Suit, MinRank, _), LessValueCards) & card(Id, Suit, MinRank, _) = Card, [LowestCard|_]).

+!get_lowest_rank_card(LessValueCards, LowestCard) <-
    .nth(0, LessValueCards, LowestCard).




+!play_turn(NPlayedCards) : NPlayedCards = 1 & played_card(PlayedCard) & hand(Cards) & briscola_suit(BriscolaSuit) <- 
    .print("My turn! Playing as second. Opponent played: ", PlayedCard);
    // create a list of cards in hand that are higher than the played card
    .findall(card(Id, Suit, Rank, Value), 
        .member(card(Id, Suit, Rank, Value), Cards) & briscola.utils.jason.get_higher_card(PlayedCard, card(Id, Suit, Rank, Value), BriscolaSuit, Result) & Result = true,
        HigherCards);
    .print("Higher cards: ", HigherCards);
    // if there are higher cards, play the one with the lowest value
    if (.empty(HigherCards)) {
        // if there are no higher cards, play the lowest card in hand
        !get_less_value_cards(Cards, LessValueCards);
    } else {
        // get the card with the lowest value from the higher cards
        !get_less_value_cards(HigherCards, LessValueCards);
    };
    !get_lowest_rank_card(LessValueCards, LowestCard);
    !play_a_card(LowestCard).


-!play_turn(NPlayedCards) <- 
    .print("Match ended.").


// Play a specific card - action sent to the environment
+!play_a_card(card(Id, Suit, Rank, Value)) : hand(List) <- 
    .print("Playing card: ", card(Id, Suit, Rank, Value));
    // play_card want the index of the card in the hand
    .nth(Index, List, card(Id, Suit, Rank, Value));
    play_card(Index).

-!play_a_card(card(Id, Suit, Rank, Value)) <- 
    .print("Error: card not found in hand.").

