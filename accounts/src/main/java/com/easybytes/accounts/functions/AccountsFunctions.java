package com.easybytes.accounts.functions;

import com.easybytes.accounts.service.IAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountsFunctions {

    private static final Logger log = LoggerFactory.getLogger(AccountsFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(IAccountsService accountsService){
        return accountNum -> {
            log.info("Updating Communication status for account Number : {}", accountNum.toString());
            accountsService.updateCommunicationStatus(accountNum);
        };
    }
}
