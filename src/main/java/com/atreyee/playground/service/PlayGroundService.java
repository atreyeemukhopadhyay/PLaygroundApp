package com.atreyee.playground.service;

import com.atreyee.playground.model.Kid;

import java.util.Map;

public interface PlayGroundService {
    int addKid(Kid kid, String playSite);
    Map<String,String> getUtilizationReport();
    int getTotalVisitorCount();
    Map<String,String> getHistoryReport();
}
