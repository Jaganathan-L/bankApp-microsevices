package com.easybytes.loans.controller;

import com.easybytes.loans.constants.LoansConstants;
import com.easybytes.loans.dto.ErrorResponseDto;
import com.easybytes.loans.dto.LoansContactInfoDto;
import com.easybytes.loans.dto.LoansDto;
import com.easybytes.loans.dto.ResponseDto;
import com.easybytes.loans.service.impl.LoansServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "CRUD REST API for Loans in EasyBank",
        description = "CRUD REST APIs in EasyBank to CREATE, UPDATE, FETCH and DELETE Loan details"
)
public class LoansController {

    private static final Logger logger = LoggerFactory.getLogger(LoansController.class);

    private final LoansServiceImpl loansService;

    public LoansController(LoansServiceImpl loansService){
        this.loansService = loansService;
    }

    @Value("${build.version}")
    private String buildInfo;

    @Autowired
    private Environment environment;

    @Autowired
    private LoansContactInfoDto loansContactInfoDto;

    @Operation(
            summary = "Create  loan REST API",
            description = "REST API to create new loan inside EasyBank"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description="HTTP Status Created"
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
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan( @RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber){

        loansService.createLoan(mobileNumber);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201,LoansConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch  loan details REST API",
            description = "REST API to fetch  loan details inside EasyBank"
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
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoan(@RequestHeader("easybank-correlation-id") String correlationId,
            @RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber){

        logger.debug("fetchLoanDetails start");
        LoansDto loansDto = loansService.fetchLoan(mobileNumber);
        logger.debug("fetchLoanDetails end");
        return ResponseEntity.status(HttpStatus.OK)
                .body(loansDto);
    }

    @Operation(
            summary = "Update loan REST API",
            description = "REST API to  update loan details inside EasyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @PutMapping("update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto){
        boolean isUpdated = loansService.updateLoanDetails(loansDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete loan REST API",
            description = "REST API to delete loan record inside EasyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLonaDetails( @RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber){
        boolean isDeleted = loansService.deleteLoanDetails(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Fetch Build details of Application",
            description = "REST API to fetch build Info of Application"
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
    @GetMapping("/build-info")
    public ResponseEntity<String> fetchBuildInfo(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildInfo);
    }

    @Operation(
            summary = "Fetch JAVA version details",
            description = "REST API to fetch java version of system"
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
    @GetMapping("/java-version")
    public ResponseEntity<String> fetchJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Fetch contact details",
            description = "REST API to fetch contact of service"
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
    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> fetchContactInfo(){
        logger.debug("Invoked Loans Contact-Info APi");
        return ResponseEntity.status(HttpStatus.OK)
                .body(loansContactInfoDto);
    }
}
