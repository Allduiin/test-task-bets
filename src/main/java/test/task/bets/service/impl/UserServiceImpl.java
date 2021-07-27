package test.task.bets.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.task.bets.model.Bet;
import test.task.bets.model.User;
import test.task.bets.repository.UserRepository;
import test.task.bets.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Short MAX_WIN_DICE = 1;
    private static final Float MAX_WIN = 30f;
    private static final Float MIDDLE_WIN = 7f;
    private final UserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public Bet bet(Long userId, Float stake) {
        Bet bet = new Bet();
        bet.setStake(stake);
        Short[] dices = getDices();
        assert dices != null;
        bet.setDice1(dices[0]);
        bet.setDice2(dices[1]);
        Float winnings = (dices[0].equals(dices[1])) ? (stake
                * ((dices[0].equals(MAX_WIN_DICE)) ? MAX_WIN: MIDDLE_WIN)): -stake;
        bet.setWinnings(winnings);
        return bet;
    }

    private Short[] getDices() {
        return null;
    }
}
