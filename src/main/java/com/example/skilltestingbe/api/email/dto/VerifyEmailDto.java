package com.example.skilltestingbe.api.email.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyEmailDto {

    private Boolean isSuccess;
}
