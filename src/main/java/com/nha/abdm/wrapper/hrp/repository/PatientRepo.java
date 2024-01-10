package com.nha.abdm.wrapper.hrp.repository;

import com.nha.abdm.wrapper.hrp.mongo.tables.Patients;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends MongoRepository<Patients, String> {
    Patients findByAbhaAddress(String abhaAddress);
    Patients findByPatientReference(String patientReference);
    List<Patients> findByPatientMobile(String patientMobile);
}