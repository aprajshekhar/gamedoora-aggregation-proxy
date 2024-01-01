package com.gamedoora.backend.proxy.aggregation.assembler;

import com.gamedoora.backend.proxy.aggregation.routes.UserProfileRoute;
import com.gamedoora.model.dto.GdUser;
import com.gamedoora.model.dto.RoleDTO;
import com.gamedoora.model.dto.SkillsDTO;
import com.gamedoora.model.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserDataAggregatorAssembler {
    private ProducerTemplate producerTemplate;

    @Autowired
    private CamelContext camelContext;
    private UserProfileRoute userProfileRoute;
    public GdUser getUserByEmail(String email){

        try {
            UserDTO userDTO = getProducerTemplate().requestBody("direct:userQuery", email, UserDTO.class);
            List<RoleDTO> roleDTOList = getProducerTemplate().requestBody("direct:userRoleQuery", email, ArrayList.class);
            List<SkillsDTO> skillsDTOList = getProducerTemplate().requestBody("direct:userSkillsQuery", email, ArrayList.class);
            return GdUser
                    .builder()
                    .skills(skillsDTOList)
                    .user(userDTO)
                    .roles(roleDTOList)
                    .build();
        } catch (CamelExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public ProducerTemplate getProducerTemplate() {
        return producerTemplate;
    }
    @Autowired
    public void setProducerTemplate(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }
}
