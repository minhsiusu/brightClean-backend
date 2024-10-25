package com.example.brightClean.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.example.brightClean.domain.User;
import com.example.brightClean.exception.NotFoundException;
import com.example.brightClean.repository.UserRepository;
import com.example.brightClean.service.UserService;

import io.micrometer.common.lang.NonNull;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(@NonNull User user) {
        // if(userRepository.findByName(user.getName()) !=null ){
        // throw new UnsupportedOperationException("有相同的名字");
        // }
        // if(userRepository.findByEmail(user.getEmail()) !=null ){
        // throw new UnsupportedOperationException("有相同的信箱");
        // }
        // if(userRepository.findByAccount(user.getAccount()) !=null ){
        // throw new UnsupportedOperationException("有相同的帳號");
        // }
        // if(userRepository.findByCellPhone(user.getCellPhone()) !=null ){
        // throw new UnsupportedOperationException("有相同的手機號碼");
        // }
        return userRepository.save(user);
    }

    @Override
    public void update(User user) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getByCellPhone(String cellphone) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByCellPhone'");
    }

    @Override
    public Optional<User> getByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    @Override
    public User getByAccountOfNonNull(String account) {
        return getByAccount(account)
                .orElseThrow(() -> new NotFoundException("The name does not exist").setErrorData(account));
    }

    @Override
    public boolean passwordMatch(@org.springframework.lang.NonNull User user, @Nullable String plainPassword) {
        // TODO Auto-generated method stub
        return user.getPassword().equals(plainPassword);
    }

    public User findUserById(Integer id) throws Exception {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new Exception("Error: User not found with id: " + id);
    }
}
