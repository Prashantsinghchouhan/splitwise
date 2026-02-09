package com.example.splitwise.service;

import com.example.splitwise.api.model.requestdto.users.UserPatchRequest;
import com.example.splitwise.api.model.requestdto.users.UserPutRequest;
import com.example.splitwise.api.model.requestdto.users.UserRequest;
import com.example.splitwise.api.model.responsedto.UserResponse;
import com.example.splitwise.entity.User;
import com.example.splitwise.exception.conflict.ConcurrentUpdateException;
import com.example.splitwise.exception.notfound.UserNotFoundException;
import com.example.splitwise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest userRequest){
        return UserResponse.from(userRepository.save(User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .middleName(userRequest.getMiddleName())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .build()));
    }

    @Transactional
    public void deleteUser(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.userNotFound(userId));
        try{
            user.setDeleted((byte) 1);
            user.setDeletedAt(Instant.now());
            userRepository.saveAndFlush(user);
        }catch (OptimisticLockingFailureException ex){
            throw ConcurrentUpdateException.optimisticLockException();
        }
    }

    @Transactional
    public UserResponse updateUser(UserPatchRequest request, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.userNotFound(userId));
        try {
            if (request.getFirstName() != null) {
                user.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null) {
                user.setLastName(request.getLastName());
            }
            if (request.getMiddleName() != null) {
                user.setMiddleName(request.getMiddleName());
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
            if (request.getPhoneNumber() != null) {
                user.setPhoneNumber(request.getPhoneNumber());
            }
            userRepository.saveAndFlush(user);
            return UserResponse.from(user);
        } catch (OptimisticLockingFailureException ex) {
            throw ConcurrentUpdateException.optimisticLockException();
        }
    }

    @Transactional
    public UserResponse putUser(UserPutRequest request, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.userNotFound(userId));
        try {
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setMiddleName(request.getMiddleName());
            userRepository.saveAndFlush(user);
            //EntityManager.flush() !!
            return UserResponse.from(user);
        } catch (OptimisticLockingFailureException ex) {
            throw ConcurrentUpdateException.optimisticLockException();
        }
    }

    public UserResponse getUser(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> UserNotFoundException.userNotFound(userId));
        return UserResponse.from(user);
    }
}
