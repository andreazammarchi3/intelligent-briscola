// bot_agent.asl

!start.
!turn(false).

// Initial goals
+!start <-
    .print("Bot started. Listening for game updates.").

+!turn(false) <-
    .print("Waiting for my turn.").

-!turn(false) <-
    .print("It's my turn.");
    !decide_action.

+!turn(true) <-
    .print("It's my turn.");
    !decide_action.


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
    !play(Card).

// Rules for playing a card
+!play(Card) <-
    .print("Playing card: ", Card);
    .send("environment", tell, play_card(Card));
    .print("Played card and waiting for next turn.").

// Handling updated hand information when received
+hand(UpdatedHand) <-
    .print("Updated hand received: ", UpdatedHand);
    !update_hand(UpdatedHand).

// Rules for updating hand information
+!update_hand(HandString) <-
    .substring(HandString, "hand(myName,", ")");
    briscola.utils.jason.split(_, ",", Cards);
    .print("Updated hand: ", Cards);
    -+hand(Cards[0], Cards[1], Cards[2]).