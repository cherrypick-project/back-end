package com.cherrypick.backend.domain.user;

import com.cherrypick.backend.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore{

    private final UserRepository userRepository;

    public User store(User user){
        return userRepository.save(user);
    }
}
