package com.meli.mla.configuration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CouponDTO {

    private String[] itemsIds;
    private Double amount;
}
