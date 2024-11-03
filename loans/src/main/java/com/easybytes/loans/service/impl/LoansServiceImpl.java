package com.easybytes.loans.service.impl;

import com.easybytes.loans.constants.LoansConstants;
import com.easybytes.loans.dto.LoansDto;
import com.easybytes.loans.entity.Loans;
import com.easybytes.loans.exception.LoanAlreadyExistsException;
import com.easybytes.loans.exception.ResourceNotFoundException;
import com.easybytes.loans.mappper.LoansMapper;
import com.easybytes.loans.repository.LoansRepository;
import com.easybytes.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
public class LoansServiceImpl implements ILoansService {

    LoansRepository loansRepository;

    /**
     * To create the loans for customer
     *
     * @param mobileNumber
     */
    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> loans = loansRepository.findByMobileNumber(mobileNumber);
        if(loans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        Loans createdLoan = generateLoan(mobileNumber);
        loansRepository.save(createdLoan);
    }


    private Loans generateLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(String.valueOf(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT - newLoan.getAmountPaid());
        return newLoan;
    }

    /**
     * Fetch Loan from Database
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loan,new LoansDto());
    }

    /**
     * Update the existing loan details
     *
     * @param loansDto
     * @return
     */
    @Override
    public boolean updateLoanDetails(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan","LoanNumber",loansDto.getLoanNumber())
        );
        LoansMapper.mapToLoans(loansDto,loans);
        loansRepository.save(loans);
        return true;
    }

    /**
     * Delete the existing loan details on record
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteLoanDetails(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "MobileNumber", mobileNumber)
        );
        loansRepository.delete(loans);
        return true;
    }


}
