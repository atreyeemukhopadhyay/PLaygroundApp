package com.atreyee.playground;

import com.atreyee.playground.model.Kid;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlayGroundTestConfig.class)
@AutoConfigureMockMvc
class PlaygroundApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getQueryUtilizationReportStatus() throws Exception {
        mvc.perform(get("/playground/utilization")).andExpect(status().isOk());
    }

    @Test
    public void getQueryUtilizationReportContent() throws Exception {
      String response = mvc.perform(get("/playground/utilization")).andReturn().getResponse().getContentAsString();
      assertNotNull(response);
    }

    @Test
    public void getQueryHistoryReportStatus() throws Exception {
        mvc.perform(get("/playground/history")).andExpect(status().isOk());
    }

    @Test
    public void getQueryHistoryReportContent() throws Exception {
        String response = mvc.perform(get("/playground/history")).andReturn().getResponse().getContentAsString();
        assertNotNull(response);
    }

    @Test
    public void getQueryVisitorCountContent() throws Exception {
        String response = mvc.perform(get("/playground/visitorCount")).andReturn().getResponse().getContentAsString();
        assertNotNull(Integer.parseInt(response));
    }

    @Test
    public void getQueryVisitorCountStatus() throws Exception {
        mvc.perform(get("/playground/visitorCount")).andExpect(status().isOk());
    }

    @Test
    public void postQueryAddKidToQueueStatus() throws Exception {
        Kid newKid = new Kid("TesKid",5,123456789,false);
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response = mvc.perform(post("/playground/addKid/slide").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(newKid))).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertTrue(response.getContentAsString().contains("Kid is added to slide queue"));
    }

    @Test
    public void postQueryAddVipKidToQueueStatus() throws Exception {
        Kid newKid = new Kid("VipKid",5,987654321,true);
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response = mvc.perform(post("/playground/addKid/slide").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(newKid))).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertTrue(response.getContentAsString().contains("Kid is added to slide queue"));
    }
}
