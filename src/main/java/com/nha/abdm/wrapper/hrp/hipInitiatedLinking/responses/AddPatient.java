package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;

import lombok.Data;

@Data
public class AddPatient {
    public String name;

    public String abhaAddress;

    public String patientReference;
    public String gender;
    public String dateOfBirth;
    public String display;
}
