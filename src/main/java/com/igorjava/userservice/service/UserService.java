package com.igorjava.userservice.service;

import com.igorjava.userservice.entity.User;
import com.igorjava.userservice.repository.UserRepository;
import com.igorjava.userservice.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User create(User user) {
        return repo.save(user);
    }

    public User getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User update(Long id, User updated) {
        User exist = getById(id);
        exist.setFirstName(updated.getFirstName());
        exist.setLastName(updated.getLastName());
        exist.setBirthDate(updated.getBirthDate());
        exist.setEmail(updated.getEmail());
        return repo.save(exist);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("User not found with id " + id);
        repo.deleteById(id);
    }
}
