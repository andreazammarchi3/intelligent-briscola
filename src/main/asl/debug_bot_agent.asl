+turn(X) <- !play_turn(X).

+!play_turn(X) : X = false <-
    .print("Waiting for my turn...").

+!play_turn(X) : X = true <-
    .print("My turn! Playing a card.");
    play_card(0).
