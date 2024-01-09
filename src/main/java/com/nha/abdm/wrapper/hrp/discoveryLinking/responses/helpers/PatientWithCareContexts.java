package com.nha.abdm.wrapper.hrp.discoveryLinking.responses.helpers;

import com.nha.abdm.wrapper.hrp.CommonHelpers.CareContextBuilder;
import lombok.Data;
import java.util.List;

@Data
public class PatientWithCareContexts {

    private String id;

    private String referenceNumber;

    private List<CareContextBuilder> careContexts;
}