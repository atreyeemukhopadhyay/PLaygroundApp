package com.atreyee.playground;

import com.atreyee.playground.controller.PlayGroundController;
import com.atreyee.playground.model.Kid;
import com.atreyee.playground.service.PlayGroundService;
import com.atreyee.playground.service.PlayGroundServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@WebMvcTest(PlayGroundController.class)
public class PlaygroundControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlayGroundServiceImpl playGroundService;

    @Test
    public void getValidUtilizationReport() throws Exception {
        Map<String,String> utilizationReport = new HashMap<String,String>();
        utilizationReport.put("slide","50%");
        utilizationReport.put("doubleswing","80%");
        utilizationReport.put("carousel","90%");
        utilizationReport.put("pitball","20%");

        given(playGroundService.getUtilizationReport()).willReturn(utilizationReport);
        MockHttpServletResponse response = mvc.perform(get("/playground/utilization")).andReturn().getResponse();
        ObjectMapper mapper = new ObjectMapper();

        assertEquals(200,response.getStatus());
        assertEquals(mapper.writeValueAsString(utilizationReport),response.getContentAsString());
    }

    @Test
    public void getUtilizationReport_Error() throws Exception {
        given(playGroundService.getUtilizationReport()).willThrow(new ArithmeticException("total capacity od playsite is zero"));
        mvc.perform(get("/playground/utilization")).andExpect(status().isInternalServerError());
    }

    @Test
    public void getHistoryReportSuccess() throws Exception {
        Map<String,String> historyReport = new HashMap<String,String>();
        historyReport.put("slide","1.00");
        historyReport.put("doubleswing","0.5");
        historyReport.put("carousel","2.00");
        historyReport.put("pitball","3.00");

        given(playGroundService.getHistoryReport()).willReturn(historyReport);
        MockHttpServletResponse response = mvc.perform(get("/playground/history")).andReturn().getResponse();

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(200,response.getStatus());
        assertEquals(mapper.writeValueAsString(historyReport),response.getContentAsString());
    }

    @Test
    public void getHistoryReportFailure() throws Exception {
        given(playGroundService.getHistoryReport()).willThrow(new ArithmeticException("total capacity od playsite is zero"));
        mvc.perform(get("/playground/history")).andExpect(status().isInternalServerError());
    }

    @Test
    public void getVisitorCountReportSuccess() throws Exception {
        int visitorCount = 100;

        given(playGroundService.getTotalVisitorCount()).willReturn(visitorCount);
        MockHttpServletResponse response = mvc.perform(get("/playground/visitorCount")).andReturn().getResponse();

        assertEquals(200,response.getStatus());
        assertEquals(100,Integer.parseInt(response.getContentAsString()));
    }

    @Test
    public void getVisitorCountReportError() throws Exception {
        given(playGroundService.getTotalVisitorCount()).willThrow(new ArithmeticException("total capacity od playsite is zero"));
        mvc.perform(get("/playground/visitorCount")).andExpect(status().isInternalServerError());
    }

    @Test
    public void addKidToPlaySiteQueueSuccess() throws Exception {
        Kid newKid = new Kid("TesKid",5,123456789,false);

        given(playGroundService.addKid(newKid,"slide")).willReturn(0);
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response = mvc.perform(post("/playground/addKid/slide").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(newKid))).andReturn().getResponse();

        assertEquals(200,response.getStatus());
        assertEquals("Kid is added to slide queue",response.getContentAsString());
    }
}
