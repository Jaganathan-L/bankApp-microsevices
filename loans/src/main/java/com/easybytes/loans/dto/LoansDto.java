package com.easybytes.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Loans",
        description = " Schema to hold Loans information"
)
public class LoansDto {

    @NotEmpty(message = "mobile Number should not be empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    @Schema(
            description = "MobileNumber of the Loan customer",
            example = "7895643209"
    )
    private String mobileNumber;

    @NotEmpty(message = "Loan Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{12})",message = "LoanNumber must be 12 digits")
    @Schema(
            description = "Laon Number of Eazy Bank account", example = "3454433243"
    )
    private String loanNumber;

    @NotEmpty(message = "LoanType should not be empty")
    @Schema(
            description = "Type of Loan",
            example = "Home_Loan"
    )
    private String loanType;

    @Positive(message = "Total Loan always should be positive")
    @Schema(
            description = "Total loan amount",
            example = "100000"
    )
    private int totalLoan;

    @Schema(
            description = "Amount paid for thr loan",
            example = "10000"
    )
    @PositiveOrZero(message = "Amount Paid always should  be positive or zero")
    private int amountPaid;
    @Schema(
            description = "Outstanding loan amount for thr loan",
            example = "90000"
    )
    @PositiveOrZero(message = "Outstanding Loan amount always should be positive or zero")
    private int outstandingAmount;
}
