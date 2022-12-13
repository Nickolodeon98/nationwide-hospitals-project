package com.practice.springbootdocker.domain.entity;

import com.practice.springbootdocker.domain.dto.HospitalResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Table(name="nation_wide_hospitals")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String openServiceName;
    private int openLocalGovernmentCode;
    private String managementNumber;
    private LocalDateTime licenseDate;
    private int businessStatus;
    private int businessStatusCode;
    private String phone;
    private String fullAddress;
    private String roadNameAddress;
    private String hospitalName;
    private String businessTypeName;
    private int healthcareProviderCount;
    private int patientRoomCount;
    private int totalNumberOfBeds;
    private float totalAreaSize;

    @OneToMany(mappedBy = "hospitalToReview")
    @Builder.Default
    private List<Review> review = new ArrayList<>();

    public Hospital(String openServiceName, int openLocalGovernmentCode, String managementNumber, LocalDateTime licenseDate,
                    int businessStatus, int businessStatusCode, String phone, String fullAddress, String roadNameAddress,
                    String hospitalName, String businessTypeName, int healthcareProviderCount, int patientRoomCount,
                    int totalNumberOfBeds, float totalAreaSize) {
        this.openServiceName = openServiceName;
        this.openLocalGovernmentCode = openLocalGovernmentCode;
        this.managementNumber = managementNumber;
        this.licenseDate = licenseDate;
        this.businessStatus = businessStatus;
        this.businessStatusCode = businessStatusCode;
        this.phone = phone;
        this.fullAddress = fullAddress;
        this.roadNameAddress = roadNameAddress;
        this.hospitalName = hospitalName;
        this.businessTypeName = businessTypeName;
        this.healthcareProviderCount = healthcareProviderCount;
        this.patientRoomCount = patientRoomCount;
        this.totalNumberOfBeds = totalNumberOfBeds;
        this.totalAreaSize = totalAreaSize;
    }

    /* 엔티티의 DTO 화 - 필요한 이유: DB 의 데이터를 화면에 JSON 형태로 전송해주어야 할 경우가 있기 때문
     * 궁금증: static 사용 이유는? 가독성을 위함이다. Hospital 객체를 생성해줄 필요 없이 Hospital.of가 가능하기 때문. */
    public static HospitalResponse of(Hospital hospital) {
        return new HospitalResponse(hospital.getId(), hospital.getRoadNameAddress(), hospital.getHospitalName(),
                hospital.getPatientRoomCount(), hospital.getTotalNumberOfBeds(), hospital.getBusinessTypeName(),
                hospital.getTotalAreaSize());
    }
}
