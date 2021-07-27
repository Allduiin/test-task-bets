package test.task.bets.controllers;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.task.bets.mapper.UserMapper;
import test.task.bets.model.Bet;
import test.task.bets.model.dto.UserDto;
import test.task.bets.service.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("snakeeyes")
public class UserController {
    private static final List<Float> STAKES = List.of(1f, 2f, 10f);
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/add")
    public void add(UserDto userDto) {
        userService.add(userMapper.getUserFromUserDto(userDto));
    }

    @GetMapping("play/{id}")
    public Bet play(@PathVariable Long id, @RequestParam Float stake) {
        if (!STAKES.contains(stake)) {
            throw new RuntimeException("Unexcited stake");
        }
        return userService.bet(id, stake);
    }
}
