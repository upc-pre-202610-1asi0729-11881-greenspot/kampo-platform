package com.acme.kampo.platform.report.application.internal.eventhandlers;

import com.acme.kampo.platform.report.domain.model.events.ReportGeneratedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReportGeneratedEventHandler {

    @EventListener
    public void on(ReportGeneratedEvent event) {
        log.info("Report generated event received: reportId={}", event.getReportId());
    }
}