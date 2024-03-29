package com.example.bank.api;

import com.example.bank.constant.ApiUrl;
import com.example.bank.dto.UserResponseDTO;
import com.example.bank.model.Account;
import com.example.bank.repository.AccountRepository;
import com.example.bank.request.DestinationAccountRequest;
import com.example.bank.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class FetchingDestinationUserApi extends CommonApi<UserInfoResponse, DestinationAccountRequest>{

    private final AccountRepository accountRepository;

    @Override
    public UserInfoResponse execute(DestinationAccountRequest request) {

        UserInfoResponse userInfoResponse = new UserInfoResponse();
        RestTemplate restTemplate = new RestTemplate();

        String destinationAccountNumber = request.getDestinationAccount();

        Account accountDestination = accountRepository.getUserIdByAccountNumber(destinationAccountNumber);
        if(accountDestination == null){
            return userInfoResponse;
        }
        String urlRequest = ApiUrl.AUTH_SERVICE_HOST.getUrl() + "user/" +accountDestination.getUserId();

        UserResponseDTO userResponse = restTemplate.getForObject(urlRequest, UserResponseDTO.class);
        userResponse.setAccountId(accountDestination.getAccountId());
        userInfoResponse.setUserResponseDTO(userResponse);
        return userInfoResponse;
    }
}
