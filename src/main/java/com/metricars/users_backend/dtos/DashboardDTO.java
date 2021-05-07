package com.metricars.users_backend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class DashboardDTO {
    private List<AssetDTO> assets;
}
