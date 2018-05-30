package com.meli.mutantdetector.mapper;

import com.meli.mutantdetector.dto.StatsDTO;
import com.meli.mutantdetector.model.Stats;

public class StatisticsMapper {

    public static StatsDTO map(Stats statistic) {
        return new StatsDTO(statistic.getMutantsCount(), statistic.getHumansCount(), statistic.getRatio());
    }
}
