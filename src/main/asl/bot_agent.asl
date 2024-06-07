// bot_agent.asl

!start.

// Initial goals
+!start <-
    .print("Bot started. Listening for game updates.").

// Reacting to turn information
+turn(myName, true) <-
    .print("It's my turn.");
    !decide_action.

+turn(myName, false) <-
    .print("Waiting for my turn.").

// Decision making when it's bot's turn
+!decide_action <-
    .print("Deciding what to do.");
    ?hand(Card1, Card2, Card3);
    .print("My hand: ", Card1, ", ", Card2, ", ", Card3);
    !play_card.

// Rules for choosing a card to play
+!play_card <-
    .random(0, 2, N);
    .nth([Card1, Card2, Card3], N, Card);
    .print("Choosing card: ", Card);
    play(Card).

// Rules for playing a card
+play(Card) <-
    .print("Playing card: ", Card);
    .send("environment", tell, play_card(Card));
    .print("Played card and waiting for next turn.").

// Handling updated hand information
+hand(UpdatedHand) <-
    .retract(hand(_));
    +hand(UpdatedHand);
    .print("Updated hand: ", UpdatedHand).

// Utility to parse and update hand based on perception
+!update_hand(HandString) <-
    .substring(HandString, "hand(myName,", ")");
    .split(_, ",", Cards);
    -+hand(Cards[0], Cards[1], Cards[2]);
    .print("Updated hand: ", Cards[0], ", ", Cards[1], ", ", Cards[2]).
