package com.easybytes.accounts.service;

import com.easybytes.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {

    /**
     *
     * @param mobileNumber
     * @return - Customer details
     */
    CustomerDetailsDto fetCustomerDetails(String mobileNumber, String correlationId);
}
