played(false).

+turn(X) <- !play_turn(X).

+!play_turn(X) : played(false) & X = true <-
    .print("It's my turn. Playing a card.");
    -+played(true);
    play_card(0).

+!play_turn(X) : played(false) & X = false <-
    .print("Waiting for my turn...");
    -+played(false).

+!play_turn(X) : played(true) <-
    .print("I've already played this turn.");
    -+played(true).





