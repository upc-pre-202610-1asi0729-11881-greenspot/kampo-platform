package com.acme.kampo.platform.season.interfaces.acl;

public interface SeasonContextFacade {

    Long createSeason(Long fieldId, String cropName, java.time.LocalDate startAt);

    Long fetchSeasonById(Long id);
}
