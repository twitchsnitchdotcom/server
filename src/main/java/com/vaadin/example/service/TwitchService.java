package com.vaadin.example.service;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TwitchService {

    TwitchClient twitchClient = TwitchClientBuilder.builder()
            .withEnableHelix(true)
            .withTimeout(5000)
            .build();

    @PostConstruct
    public void init(){

    }

    public TwitchClient getTwitchClient(){
        return twitchClient;
    }
}
