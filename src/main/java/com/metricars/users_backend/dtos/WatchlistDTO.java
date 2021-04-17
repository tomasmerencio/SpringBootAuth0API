package com.metricars.users_backend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class WatchlistDTO {
    private Long id;
    private String name;
    private List<AssetDTO> assets;
}
