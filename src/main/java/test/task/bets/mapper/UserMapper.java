package test.task.bets.mapper;

import org.springframework.stereotype.Component;
import test.task.bets.model.User;
import test.task.bets.model.dto.UserDto;

@Component
public class UserMapper {
    public User getUserFromUserDto(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        return user;
    }
}
