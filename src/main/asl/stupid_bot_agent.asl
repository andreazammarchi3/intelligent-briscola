/* Stupid bot that plays a dumb strategy:
- As first player, play a random card
- As second player, play a random card */

/* Percepts
- hand(List of cards) - Perceived from the environment
- turn(BotTurn, NPlayedCards) - Indicates if it's the bot's turn and the number of cards played in the round
- bot_level(Level) - The bot's level of intelligence */

/* Percept of the hand cards */
+hand(List) : bot_level(stupid) <- 
    .print("Current hand: ", List).

/* Percept of the turn. If it's the bot's turn, play the turn. */
+turn(BotTurn, NPlayedCards) : bot_level(stupid) & BotTurn = true <- 
    !play_turn(NPlayedCards).

/* Play the turn as first.*/
+!play_turn(NPlayedCards) : NPlayedCards = 0 <- 
    .wait(2500);
    .print("My turn! Playing as first.");
    !play_rand_card.

/* Play the turn as second. */
+!play_turn(NPlayedCards) : NPlayedCards = 1 <- 
    .print("My turn! Playing as second.");
    !play_rand_card.

/* Play a random card from the hand. */
+!play_rand_card : hand(List) & not List = [] <- 
    .length(List, Length);
    briscola.utils.jason.rand_int(0, Length - 1, Index);
    play_card(Index).

/* If there are no cards in the hand, print a message. */
-!play_rand_card <- 
    .print("No cards in hand!").
