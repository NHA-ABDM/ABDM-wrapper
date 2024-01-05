package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.helpers;

import com.nha.abdm.wrapper.hrp.common.CareContextBuilder;
import lombok.Data;

import java.util.List;

@Data
public class PatientWithCareContext {
    private List<CareContextBuilder> careContexts;
}
