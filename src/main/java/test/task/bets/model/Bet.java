package test.task.bets.model;

import lombok.Data;

@Data
public class Bet {
    Short dice1;
    Short dice2;
    Float stake;
    Float winnings;
}
