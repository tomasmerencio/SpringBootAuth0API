package com.metricars.users_backend.dtos;

import lombok.Data;

@Data
public class AssetDTO {
    private Long id;
    private String AssetType;
    private String ticker;
    private String description;
}
