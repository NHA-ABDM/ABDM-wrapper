package com.nha.abdm.wrapper.hrp.CommonHelpers;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CareContextBuilder {
    public String referenceNumber;
    public String display;
    public boolean isLinked;
}
