package com.atreyee.playground.configuration;

import com.atreyee.playground.model.PlaySite;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class PlaySiteConfiguration {
    @Value("${DoubleSwingNumber}")
    private int swingInstallationNumber;

    @Value("${DoubleSwingRideDuration}")
    private int swingRideTime;

    @Value("${SlideNumber}")
    private int slideInstallationNumber;

    @Value("${SlideDuration}")
    private int slideRideTime;

    @Value("${CarouselNumber}")
    private int carouselInstallationNumber;

    @Value("${CarouselRideDuration}")
    private int carouselRideTime;

    @Value("${CarouselCapacity}")
    private int carouselCapacity;

    @Value("${PitBallNumber}")
    private int pitBallInstallationNumber;

    @Value("${PitBallDuration}")
    private int pitBallRideTime;

    @Value("${PitBallCapacity}")
    private int pitBallCapacity;

    @Value("${WorkingHoursPerDay}")
    private int workingHours;

    @Bean
    @Qualifier("playSites")
    public List<PlaySite> playSites(){
        List<PlaySite> playSites = new ArrayList<>();

        if(swingInstallationNumber!=0) {
            PlaySite doubleSwing = new PlaySite("doubleswing", 2, swingInstallationNumber, swingRideTime,workingHours);
            playSites.add(doubleSwing);
        }

        if(carouselInstallationNumber!=0) {
            PlaySite carousel = new PlaySite("carousel", carouselCapacity, carouselInstallationNumber, carouselRideTime,workingHours);
            playSites.add(carousel);
        }

        if(slideInstallationNumber!=0) {
            PlaySite slide = new PlaySite("slide", 1, slideInstallationNumber, slideRideTime,workingHours);
            playSites.add(slide);
        }

        if(pitBallInstallationNumber!=0) {
            PlaySite pitBall = new PlaySite("pitBall", pitBallCapacity, pitBallInstallationNumber, pitBallRideTime,workingHours);
            playSites.add(pitBall);
        }

        return playSites;
    }
}
