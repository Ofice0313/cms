package com.mcts.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CustomError {

    private Instant timeStamp;
    private Integer status;
    private String error;
    private String path;
}
