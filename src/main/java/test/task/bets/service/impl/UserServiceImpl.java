package test.task.bets.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    private static final String GET_DICES_URL =
            "https://www.random.org/integers/?num=2&min=1&max=6&col=2&base=10&format=plain";
    private static final Integer DICES1_PLACE = 0;
    private static final Integer DICES2_PLACE = 2;
    private final UserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public Bet bet(Long userId, Float stake) {
        Bet bet = new Bet();
        bet.setStake(stake);
        Short[] dices;
        try {
            dices = getDices();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException("Problem with getting dices", e);
        }
        bet.setDice1(dices[0]);
        bet.setDice2(dices[1]);
        Float winnings = (dices[0].equals(dices[1])) ? (stake
                * ((dices[0].equals(MAX_WIN_DICE)) ? MAX_WIN : MIDDLE_WIN))
                : -stake;
        bet.setWinnings(winnings);
        User user = userRepository.getById(userId);
        user.setValet(user.getValet() + winnings);
        userRepository.save(user);
        return bet;
    }

    private Short[] getDices() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(GET_DICES_URL))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        String dicesString = response.body();
        return new Short[]{
                (short) (dicesString.charAt(DICES1_PLACE) - 48),
                (short) (dicesString.charAt(DICES2_PLACE) - 48)};
    }
}
