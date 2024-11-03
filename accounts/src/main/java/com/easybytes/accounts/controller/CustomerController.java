package com.easybytes.accounts.controller;

import com.easybytes.accounts.dto.CustomerDetailsDto;
import com.easybytes.accounts.dto.ErrorResponseDto;
import com.easybytes.accounts.service.ICustomersService;
import com.easybytes.accounts.service.impl.CustomersServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "REST API for Customers in EasyBank",
        description = "CRUD REST APIs in EasyBank to Fetch account customers details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@AllArgsConstructor
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private ICustomersService customersService;


    @Operation(
            summary = "Fetch Account Customer details Details REST API",
            description = "REST API to fetch Customer details based on a mobile number"
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("easybank-correlation-id") String correlationId,
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be 10 digits") String mobileNumber){

        logger.debug("fetchCustomerDetails method start");
        CustomerDetailsDto customerDetails = customersService.fetCustomerDetails(mobileNumber, correlationId);
        logger.debug("fetchCustomerDetails method end");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerDetails);
    }

    }
