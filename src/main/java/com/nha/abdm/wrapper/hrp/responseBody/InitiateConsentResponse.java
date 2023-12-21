package com.nha.abdm.wrapper.hrp.responseBody;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InitiateConsentResponse {
    String requestId;
    String timestamp;
    public Consent consent;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Consent getConsent() {
        return consent;
    }

    public void setConsent(Consent consent) {
        this.consent = consent;
    }

    public class Consent{
        public List<String> hiTypes=new ArrayList();

        public List<String> getHiTypes() {
            return hiTypes;
        }

        public void setHiTypes(List<String> hiTypes) {
            this.hiTypes = hiTypes;
        }

        public Purpose getPurpose() {
            return purpose;
        }

        public void setPurpose(Purpose purpose) {
            this.purpose = purpose;
        }

        public Patient getPatient() {
            return patient;
        }

        public void setPatient(Patient patient) {
            this.patient = patient;
        }

        public Hiu getHiu() {
            return hiu;
        }

        public void setHiu(Hiu hiu) {
            this.hiu = hiu;
        }

        public Requester getRequester() {
            return requester;
        }

        public void setRequester(Requester requester) {
            this.requester = requester;
        }

        public Permission getPermission() {
            return permission;
        }

        public void setPermission(Permission permission) {
            this.permission = permission;
        }

        public Purpose purpose;
        public Patient patient;
        public Hiu hiu;
        public Requester requester;
        public Permission permission;
        public class Purpose{
            String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            String code;

        }
        public class Patient{
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            String id;

        }
        public class Hiu{
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            String id;

        }
        public class Requester{
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Identifier getIdentifier() {
                return identifier;
            }

            public void setIdentifier(Identifier identifier) {
                this.identifier = identifier;
            }

            public String name;
            public Identifier identifier;
            public class Identifier{
                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getSystem() {
                    return system;
                }

                public void setSystem(String system) {
                    this.system = system;
                }

                public String type;
                 public String value;
                 public String system;

            }
        }
        public class Permission {

            public String accessMode;
            public DateRange dateRange;

            public DateRange getDateRange() {
                return dateRange;
            }

            public void setDateRange(DateRange dateRange) {
                this.dateRange = dateRange;
            }

            public class DateRange{
                public String getFrom() {
                    return from;
                }

                public void setFrom(String from) {
                    this.from = from;
                }

                public String getTo() {
                    return to;
                }

                public void setTo(String to) {
                    this.to = to;
                }

                public String from;
                public String to;

            }

            public String getAccessMode() {
                return accessMode;
            }

            public void setAccessMode(String accessMode) {
                this.accessMode = accessMode;
            }

            public String getDataEraseAt() {
                return dataEraseAt;
            }

            public void setDataEraseAt(String dataEraseAt) {
                this.dataEraseAt = dataEraseAt;
            }

            public String dataEraseAt;
            public Frequency frequency;

            public Frequency getFrequency() {
                return frequency;
            }

            public void setFrequency(Frequency frequency) {
                this.frequency = frequency;
            }

            public class Frequency {
                public String unit;
                public String value;

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getRepeats() {return repeats;}

                public void setRepeats(String repeats) {this.repeats = repeats;}

                public String repeats;

            }
        }

    }

}
