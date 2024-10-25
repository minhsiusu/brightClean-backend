package com.example.brightClean.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.example.brightClean.domain.User;

public interface UserService {

    User create(User user);

    Optional<User> getUser(Integer id);

    @NonNull
    Optional<User> getByAccount(@NonNull String account);

    @NonNull
    User getByAccountOfNonNull(@NonNull String account);

    boolean passwordMatch(@NonNull User user, @Nullable String plainPassword);

    List<User> getUsers();

    void update(User user) throws Exception;

    @NonNull
    Optional<User> getByEmail(@NonNull String email);

    @NonNull
    Optional<User> getByCellPhone(@NonNull String cellphone);
}
