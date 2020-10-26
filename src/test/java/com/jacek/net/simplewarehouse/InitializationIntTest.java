package com.jacek.net.simplewarehouse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.jacek.net.simplewarehouse.entities.DataSource;
import com.jacek.net.simplewarehouse.repositories.DataSourceRepository;

/**
 * @author Jacek Niepsuj
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InitializationIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Test
    @Transactional
    void registrationWorksThroughAllLayers() throws Exception {
        // given

        // when
        mockMvc.perform(post("/init"))
                .andExpect(status().isOk());
        List<DataSource> dataSources = dataSourceRepository.findAll();

        // then
        assertThat(dataSources.size(), equalTo(3));
    }
}
