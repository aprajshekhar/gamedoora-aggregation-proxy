package com.gamedoora.backend.proxy.aggregation.enrichment.clients;

import com.gamedoora.model.dto.SkillsDTO;
import com.gamedoora.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RetriableUsersServicesClient {
 private UserServicesClient userServicesClient;
  private RetryTemplate retryTemplate;
    public UserDTO getRolesForUserByEmail(String email){
        return getRetryTemplate().execute(retryContext -> {
           return getUserServicesClient().getUserByEmail(email);
        });
    }


    public RetryTemplate getRetryTemplate() {
        return retryTemplate;
    }

    @Autowired
    public void setRetryTemplate(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    public UserServicesClient getUserServicesClient() {
        return userServicesClient;
    }

    @Autowired
    public void setUserServicesClient(UserServicesClient userServicesClient) {
        this.userServicesClient = userServicesClient;
    }
}
