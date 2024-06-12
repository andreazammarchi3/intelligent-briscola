+turn(B, N) : match_started(stupid) <-
    !play_turn(B, N).

+!play_turn(B, N) : B = false <-
    .print("Waiting for my turn...").

+!play_turn(B, N) : B = true & N = 0 <-
    .wait(2500);
    .print("My turn! Playing as first.");
    play_card(0).

+!play_turn(B, N) : B = true & N = 1 <-
    .print("My turn! Playing as second.");
    play_card(0).

+!play_turn(B, N) : B = true & N = 2 <-
    .wait(2500);
    .print("Waiting for my turn...").

-!play_turn(B, N) <-
    // do nothing
    .print("Waiting for my turn...").
