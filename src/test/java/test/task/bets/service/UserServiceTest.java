package test.task.bets.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import test.task.bets.model.Bet;
import test.task.bets.model.User;
import test.task.bets.repository.UserRepository;
import test.task.bets.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "user_name";
    private static final Double START_VALET = 1000d;
    private static final Float STAKE = 1f;
    private static final Short MIN_DICE = 1;
    private static final Short MAX_DICE = 6;
    private static final Float MAX_WINNINGS = 30f;

    private static UserRepository userRepository;
    private static UserService userService;


    @BeforeEach
    public void initializeUserService() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void addTest() {
        User user = new User();
        user.setName(USER_NAME);

        User result = new User();
        result.setId(USER_ID);
        result.setName(USER_NAME);
        result.setValet(START_VALET);
        when(userRepository.save(user)).thenReturn(result);
        user = userService.add(user);
        assertEquals(START_VALET, user.getValet());
        assertEquals(USER_ID, user.getId());
    }

    @Test
    public void betTest() {
        User user = new User();
        user.setValet(START_VALET);
        when(userRepository.getById(USER_ID)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(Mockito.any());
        Bet bet = userService.bet(USER_ID, STAKE);
        assertTrue(bet.getDice1() < MAX_DICE + 1 || bet.getDice1() > MIN_DICE - 1);
        assertTrue(bet.getDice2() < MAX_DICE + 1 || bet.getDice2() > MIN_DICE - 1);
        assertEquals(STAKE, bet.getStake());
        assertTrue(bet.getWinnings() > -STAKE || bet.getWinnings() < MAX_WINNINGS + 1);
        verify(userRepository).save(user);
    }
}
