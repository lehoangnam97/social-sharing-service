package com.namle.socialsharing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
public class GenerateShareDataReferralRequestDTO {
    private String referralKey;
    private String userName;
}
