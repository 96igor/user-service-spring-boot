package com.igorjava.userservice.service;

import com.igorjava.userservice.entity.User;
import com.igorjava.userservice.exception.NotFoundException;
import com.igorjava.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    UserRepository repo = Mockito.mock(UserRepository.class);
    UserService service = new UserService(repo);

    @Test
    void createAndGet() {
        User u = new User("Ivan","Petrov", LocalDate.of(2000,1,1), "a@b.c");
        when(repo.save(any())).thenAnswer(i -> { User uu = (User)i.getArgument(0); uu.setId(1L); return uu; });

        User saved = service.create(u);
        assertNotNull(saved.getId());

        when(repo.findById(1L)).thenReturn(Optional.of(saved));
        User found = service.getById(1L);
        assertEquals("Ivan", found.getFirstName());
    }

    @Test
    void getByIdNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.getById(99L));
    }
}
