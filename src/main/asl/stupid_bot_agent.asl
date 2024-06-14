// An agent to play Briscola
// This bot will play against a human in a Briscola card game, interacting directly with a Kotlin environment

/* Beliefs and Plans */

// Percepts
// hand(List of cards) - Perceived from the environment
// turn(BotTurn, NPlayedCards) - Indicates if it's the bot's turn and the number of cards played in the round
// bot_level(Level) - The bot's level of intelligence

// Agent's reaction to changes in the game state
+hand(List) : bot_level(stupid) <- 
    .print("Current hand: ", List).

+turn(BotTurn, NPlayedCards) : bot_level(stupid) & BotTurn = true <- 
    !play_turn(NPlayedCards).

// Decide on the action based on the number of played cards
+!play_turn(NPlayedCards) : NPlayedCards = 0 <- 
    .wait(2500);
    .print("My turn! Playing as first.");
    !play_rand_card.

+!play_turn(NPlayedCards) : NPlayedCards = 1 <- 
    .print("My turn! Playing as second.");
    !play_rand_card.

// Select and play a random card from the hand
+!play_rand_card : hand(List) & not List = [] <- 
    .length(List, Length);
    briscola.utils.jason.rand_int(0, Length - 1, Index);
    play_card(Index).

-!play_rand_card <- 
    .print("No cards in hand!").
