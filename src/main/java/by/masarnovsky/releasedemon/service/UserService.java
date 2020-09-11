package by.masarnovsky.releasedemon.service;

import by.masarnovsky.releasedemon.entity.User;

public interface UserService {
    User findByLogin(String login);

    User findById(Integer id);
}
