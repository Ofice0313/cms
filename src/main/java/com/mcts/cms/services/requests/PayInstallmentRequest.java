package com.mcts.cms.services.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayInstallmentRequest {

    private LocalDate paymentDate;
    private String observation;

}
