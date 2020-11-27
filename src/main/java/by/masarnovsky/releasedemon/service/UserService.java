package by.masarnovsky.releasedemon.service;

import by.masarnovsky.releasedemon.entity.User;

import java.util.List;

public interface UserService {
    User findByLogin(String login);

    User findById(Integer id);

    List<User> findAllTelegramUsers();
}
