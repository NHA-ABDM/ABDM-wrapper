package com.nha.abdm.wrapper.hrp.discoveryLinking.responses;

import com.nha.abdm.wrapper.hrp.CommonHelpers.CustomError;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.helpers.PatientWithCareContexts;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Data
@Component
public class InitResponse implements Serializable {

    private static final long serialVersionUID = 165269402517398406L;

    public CustomError error;

    public String requestId;

    public String transactionId;

    public PatientWithCareContexts patient;
}
