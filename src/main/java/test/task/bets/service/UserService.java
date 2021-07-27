package test.task.bets.service;

import test.task.bets.model.Bet;
import test.task.bets.model.User;

public interface UserService {
    User add(User user);

    Bet bet(Long userId, Float stake);
}
