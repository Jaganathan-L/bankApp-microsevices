package com.easybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Loans and Cards information"
)
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the customer",
            example = "EasyBytes"
    )
    @NotEmpty(message = "Name cannot be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email of the customer",
            example = "EasyBytes@email.com"
    )
    @NotEmpty(message = "Email address cannot be a null or empty")
    @Email(message = "Email address should be valid value")
    private String email;

    @Schema(
            description = "MobileNumber of the customer",
            example = "7895643209"
    )
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be 10 digits")
    @NotEmpty
    private String mobileNumber;

    @Schema(
            description = "Accounts information of the customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Loans information of the customer"
    )
    private LoansDto loansDto;

    @Schema(
            description = "Cards information of the customer"
    )
    private CardsDto cardsDto;
}
