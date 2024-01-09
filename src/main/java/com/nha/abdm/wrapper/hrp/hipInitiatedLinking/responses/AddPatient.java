package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;

import lombok.Data;
import lombok.NonNull;

@Data
@NonNull
public class AddPatient {
    public String name;
    public String abhaAddress;
    public String patientReference;
    public String gender;
    public String dateOfBirth;
    public String display;
    public String patientMobile;
}
