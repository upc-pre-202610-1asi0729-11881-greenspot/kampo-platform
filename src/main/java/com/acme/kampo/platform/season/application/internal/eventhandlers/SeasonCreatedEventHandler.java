package com.acme.kampo.platform.season.application.internal.eventhandlers;

import com.acme.kampo.platform.season.domain.model.events.SeasonCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SeasonCreatedEventHandler {
    @EventListener
    public void on (SeasonCreatedEvent event){
        log.info("Season created event received: seasonId={}", event.getSeasonId());
    }
}
