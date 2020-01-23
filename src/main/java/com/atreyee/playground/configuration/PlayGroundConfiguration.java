package com.atreyee.playground.configuration;

import com.atreyee.playground.exception.PlaySiteException;
import com.atreyee.playground.model.Kid;
import com.atreyee.playground.model.PlaySite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Qualifier("playGround")
public class PlayGroundConfiguration {
    private List<PlaySite> playSites;

    @Autowired
    public PlayGroundConfiguration(@Qualifier("playSites") List<PlaySite> playSites){
        this.playSites = playSites;
    }

    public List<PlaySite> getPlaySites() {
        return playSites;
    }

    public PlaySite getPlaySite(String playSiteName) {
        for (PlaySite playSite : this.playSites) {
            if (playSite.getTypeOfPlaySite().equalsIgnoreCase(playSiteName)) {
                return playSite;
            }
        }
        return null;
    }

    @PostConstruct
    public void startPlaySites() {
        ExecutorService executor = Executors.newFixedThreadPool(playSites.size());
        try {
            //initial call to add kids to playsite
            this.addChildrenToPlaySite();

            List<Callable<String>> callables = new ArrayList<>();
            playSites.forEach(playsite -> {
                Callable<String> callable = () -> {
                    playsite.startPlay();
                    return "Working our is over. Closing playsite";
                };
                callables.add(callable);
            });
            executor.invokeAll(callables);

        } catch (InterruptedException ex) {
            throw new PlaySiteException(ex.getMessage());
        }
        executor.shutdown();
    }

    public void addChildrenToPlaySite(){
        Random randomNumber = new Random();
        for (int i = 0; i <25; i++) {
            int index = i;
            boolean isVip = false;
            if(index%5==0){
                isVip = true;
            }
            boolean finalIsVip = isVip;
            playSites.forEach((playSite -> {
                Kid playKid = new Kid(index + "", index, randomNumber.nextInt() , finalIsVip);
                playSite.addToQueue(playKid);

            }));
        }
    }
}
