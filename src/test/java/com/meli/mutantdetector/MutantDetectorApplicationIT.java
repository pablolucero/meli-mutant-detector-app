package com.meli.mutantdetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.meli.mutantdetector.dto.DnaDTO;
import com.meli.mutantdetector.model.DnaResult;
import com.meli.mutantdetector.repository.DnaResultRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MutantDetectorApplicationIT {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private static final MediaType TEXT_PLAIN_UTF8 = new MediaType(MediaType.TEXT_PLAIN.getType(),
            MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("utf8"));
    private final List<String> testDnaHuman = Arrays.asList("ATGTGT", "TAGTGC", "TTATGT", "AGACGG", "GAGTCA", "TCACTG");
    private final List<String> testDnaMutant = Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
    @Autowired
    private DnaResultRepository dnaResultRepository;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testMutantAndStatsEndpointsPlusPersistence() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/stats"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count_mutant_dna").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.count_human_dna").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ratio").value(0))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();

        DnaDTO mutantDnaDTO = new DnaDTO(testDnaMutant);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String mutantRequestJson = ow.writeValueAsString(mutantDnaDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
                .header("Content-Type", "application/json")
                .content(mutantRequestJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TEXT_PLAIN_UTF8))
                .andExpect(MockMvcResultMatchers.content().string("OK"));

        // wait a 0.1 seconds since the persistence is an async event
        Thread.sleep(100);

        List<DnaResult> allDnas = dnaResultRepository.findAll();
        assertEquals(1, allDnas.size());

        DnaDTO humanDnaDTO = new DnaDTO(testDnaHuman);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String humanRequestJson = ow.writeValueAsString(humanDnaDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
                .header("Content-Type", "application/json")
                .content(humanRequestJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        Thread.sleep(100);

        allDnas = dnaResultRepository.findAll();
        assertEquals(2, allDnas.size());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/stats"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count_mutant_dna").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.count_human_dna").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ratio").value(0.5))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
    }

}
