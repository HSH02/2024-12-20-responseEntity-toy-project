package com.mysite.responseentitytoyproject.domain.user.service;

import com.mysite.responseentitytoyproject.domain.user.dto.request.UserCreateRequest;
import com.mysite.responseentitytoyproject.domain.user.dto.response.UserResponse;
import com.mysite.responseentitytoyproject.domain.user.entity.User;
import com.mysite.responseentitytoyproject.domain.user.repository.UserRepository;
import com.mysite.responseentitytoyproject.global.error.exception.DuplicateEmailException;
import com.mysite.responseentitytoyproject.global.error.exception.UserNotFoundException;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateRequestException("이미 존재하는 이메일입니다.");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        return new UserResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(Long id, UserCreateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        // 다른 사용자와 이메일 중복 검증
        if (!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        return new UserResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
        userRepository.deleteById(id);
    }

}

