package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    void testHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post( "/api?post_input_text=case")).andReturn(); // add "case"
        mockMvc.perform(MockMvcRequestBuilders.post( "/delete?post_text=case")).andReturn(); // delete "case"
        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL)) // confirm "case" is deleted
                .andExpect(content().string(not(containsString("case"))));


    }

    @Test
    void testDelete() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post( "/api?post_input_text=Case")).andReturn(); // add "Case" to check case sensitivity
            mockMvc.perform(MockMvcRequestBuilders.post( "/delete?post_text=case")).andReturn();    // delete "case" which is the wrong case
            mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))      // check for "case" and throw an error if found
                .andExpect(content().string(not(containsString("Case"))));


     }
}
