package com.example.splitwise.service;

import com.example.splitwise.api.model.requestdto.group.GroupRequest;
import com.example.splitwise.api.model.requestdto.group.UpdateDescription;
import com.example.splitwise.api.model.requestdto.group.UpdateName;
import com.example.splitwise.api.model.responsedto.groups.GroupResponse;
import com.example.splitwise.entity.Group;
import com.example.splitwise.entity.GroupMembership;
import com.example.splitwise.exception.conflict.ConcurrentUpdateException;
import com.example.splitwise.exception.notfound.GroupNotFoundException;
import com.example.splitwise.exception.notfound.UserNotFoundException;
import com.example.splitwise.repository.GroupMembershipRepository;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    private final UserRepository userRepository;

    @Transactional
    public GroupResponse createGroup(GroupRequest request) {
        if (!userRepository.existsById(request.getCreatedBy())) {
            throw UserNotFoundException.userNotFound(request.getCreatedBy());
        }
        Group group = groupRepository.save(
                Group.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .createdByUserId(request.getCreatedBy())
                        .build()
        );
        groupMembershipRepository.save(
                GroupMembership.builder()
                        .groupId(group.getId())
                        .userId(request.getCreatedBy())
                        .admin(true)
                        .build()
        );
        return GroupResponse.from(group);
    }

    @ReadOnlyProperty
    public GroupResponse getGroup(String groupId){
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> GroupNotFoundException.groupNotFound(groupId));
        return GroupResponse.from(group);
    }

    @Transactional
    public GroupResponse updateDescription(UpdateDescription request){
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> GroupNotFoundException.groupNotFound(request.getGroupId()));
        try{
            group.setDescription(request.getDescription());
            groupRepository.saveAndFlush(group);
            return GroupResponse.from(group);
        }catch (OptimisticLockingFailureException ex){
            throw ConcurrentUpdateException.optimisticLockException();
        }
    }

    @Transactional
    public GroupResponse updateName(UpdateName request){
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> GroupNotFoundException.groupNotFound(request.getGroupId()));
        try{
            group.setName(request.getName());
            groupRepository.saveAndFlush(group);
            return GroupResponse.from(group);
        }catch (OptimisticLockingFailureException ex){
            throw ConcurrentUpdateException.optimisticLockException();
        }
    }
}
