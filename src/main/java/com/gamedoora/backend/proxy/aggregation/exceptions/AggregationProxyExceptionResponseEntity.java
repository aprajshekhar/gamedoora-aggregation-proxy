package com.gamedoora.backend.proxy.aggregation.exceptions;

import lombok.Builder;

@Builder
public class AggregationProxyExceptionResponseEntity {
    private String status;
    private String message;
    private String details;
}
