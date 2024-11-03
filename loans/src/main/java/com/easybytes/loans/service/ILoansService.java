package com.easybytes.loans.service;

import com.easybytes.loans.dto.LoansDto;

public interface ILoansService {

    /**
     *  To create the loans for customer
     * @param mobileNumber
     */
    void createLoan(String mobileNumber);

    /**
     * Fetch Loan from Database
     * @param mobileNumber
     * @return
     */
    LoansDto fetchLoan(String mobileNumber);

    /**
     * Update the existing loan details
     * @param loansDto
     * @return
     */
    boolean updateLoanDetails(LoansDto loansDto);

    /**
     * Delete the existing loan details on record
     * @param mobileNumber
     * @return
     */
    boolean deleteLoanDetails(String mobileNumber);
}
