package com.atreyee.playground.service;

import com.atreyee.playground.configuration.PlayGroundConfiguration;
import com.atreyee.playground.model.Kid;
import com.atreyee.playground.model.PlaySite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Qualifier("playgroundService")
public class PlayGroundServiceImpl implements PlayGroundService{
    private PlayGroundConfiguration playGround;

    @Autowired
    public PlayGroundServiceImpl( @Qualifier("playGround") PlayGroundConfiguration playGround){
        this.playGround = playGround;
    }

    public int addKid(Kid kid,String playSite){
        return playGround.getPlaySite(playSite).addToQueue(kid);
    }

    public Map<String,String> getUtilizationReport(){
        Map<String,String> report = new HashMap<>();
            playGround.getPlaySites().forEach(playSite -> {
                report.put(playSite.getTypeOfPlaySite(), String.format("%.2f", playSite.getUtilization()) + "%");
            });
       return report;
    }

    public int getTotalVisitorCount(){
        return playGround.getPlaySites().stream().mapToInt(PlaySite::getTotalNumberOfKidsPlayed).sum();

    }

    public Map<String,String> getHistoryReport(){
        Map<String,String> report = new HashMap<>();
        playGround.getPlaySites().forEach(playSite -> {
            report.put(playSite.getTypeOfPlaySite(),playSite.getPlaySiteHistoryReport());
        });
        return report;
    }
}
