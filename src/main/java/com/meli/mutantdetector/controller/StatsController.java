package com.meli.mutantdetector.controller;

import com.meli.mutantdetector.dto.StatsDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

    @RequestMapping(value = "/stats", method = RequestMethod.GET, produces = "application/json")
    public StatsDTO getMutantStatistics() {
        return new StatsDTO(10, 5, 2);
    }

}
