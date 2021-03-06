package by.masarnovsky.releasedemon.service.impl;

import by.masarnovsky.releasedemon.entity.User;
import by.masarnovsky.releasedemon.repository.UserRepository;
import by.masarnovsky.releasedemon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public User findById(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public List<User> findAllTelegramUsers() {
        return repository.findAllByTelegramIdNotNull();
    }
}
