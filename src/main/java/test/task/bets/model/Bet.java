package test.task.bets.model;

import lombok.Data;

@Data
public class Bet {
    private Short dice1;
    private Short dice2;
    private Float stake;
    private Float winnings;
}
