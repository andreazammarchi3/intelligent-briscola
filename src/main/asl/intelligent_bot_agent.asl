// Percepts
// hand(List of cards) - Each card is now "id_suit_rank_value"
// turn(BotTurn, NPlayedCards) - Indicates if it's the bot's turn and the number of cards played
// bot_level(Level) - The bot's level of intelligence
// played_card(Card) - The card played by the opponent
// briscola_suit(Suit) - The suit of the briscola card

// Reacting to the game state
+hand(List) : bot_level(intelligent) <-
    .print("Current hand: ", List).

+turn(BotTurn, NPlayedCards) : bot_level(intelligent) & BotTurn = true <-
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
        // if player played a card with 0 value (not briscola), try to win with the same suit
        if (PlayedCard = card(_, PlayedSuit, _, 0)) {
            .findall(card(Id, PlayedSuit, Rank, Value), .member(card(Id, PlayedSuit, Rank, Value), HigherCards), SameSuitHigherCards);
            if (.empty(SameSuitHigherCards)) {
                // if there are no higher cards with the same suit, play the lowest card in hand
                !get_less_value_cards(Cards, LessValueCards);
            } else {
                // if there are higher cards with the same suit, play the one with the lowest value
                !get_less_value_cards(SameSuitHigherCards, LessValueCards);
            }
        } else {
            // if player played a card with a value
            if (PlayedCard = card(_, BriscolaSuit, _, _)) {
                // if the played card is briscola, play a card with 0 value (not briscola), if possible
                !get_less_value_cards(Cards, LessValues);
                !get_lowest_rank_card(LessValues, Lowest);
                if (Lowest = card(_, Suit, _, 0) & not Suit = BriscolaSuit) {
                    !play_a_card(Lowest);
                } else {
                    // if there are no cards with 0 value, play the lowest winning card in hand
                    !get_less_value_cards(HigherCards, LessValueCards);
                }
            } else {
                // if the played card is not briscola, play the lowest winning card in hand
                !get_less_value_cards(HigherCards, LessValueCards);
            }

        }
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

