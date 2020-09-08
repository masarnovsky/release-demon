package by.masarnovsky.releasedemon.service.impl;

import by.masarnovsky.releasedemon.entity.User;
import by.masarnovsky.releasedemon.repoitory.UserRepository;
import by.masarnovsky.releasedemon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public User findByLogin(String login) {
        return repository.findByLogin(login);
    }
}
