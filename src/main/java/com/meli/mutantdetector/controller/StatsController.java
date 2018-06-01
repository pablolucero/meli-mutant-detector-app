package com.meli.mutantdetector.controller;

import com.meli.mutantdetector.dto.StatsDTO;
import com.meli.mutantdetector.mapper.StatisticsMapper;
import com.meli.mutantdetector.service.StatsService;
import com.meli.mutantdetector.service.StatsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

    private StatsService statsService;

    @Autowired
    public StatsController(StatsServiceImpl statsService) {
        this.statsService = statsService;
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET, produces = "application/json")
    public StatsDTO getMutantStatistics() {
        return StatisticsMapper.map(statsService.calculateStats());
    }

}
