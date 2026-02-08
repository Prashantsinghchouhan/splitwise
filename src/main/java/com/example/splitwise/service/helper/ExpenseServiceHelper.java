package com.example.splitwise.service.helper;

import com.example.splitwise.api.model.requestdto.AddExpenseRequest;
import com.example.splitwise.exception.validation.UserNotInGroupException;
import com.example.splitwise.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpenseServiceHelper {
    private MembershipRepository membershipRepository;

    public void validateAddExpenseRequest(AddExpenseRequest request){
        validateUserPresentInGroup(request);
    }

    public void validateUserPresentInGroup(AddExpenseRequest request){
        request.getParticipants().stream()
                .filter(participant -> !membershipRepository.existsByGroupIdAndUserId(request.getGroupId(), participant))
                .findFirst()
                .ifPresent(participant ->{
                    throw UserNotInGroupException.userNotInGroup(participant, request.getGroupId());
                });
    }
}
