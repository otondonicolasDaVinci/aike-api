package com.tesis.aike.service.impl;

import com.tesis.aike.service.TourService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TourServiceImpl implements TourService {

    private final Map<String, List<String>> toursByCabin = new HashMap<>();
    private final List<String> defaultTours = List.of(
            "Recorrido por el bosque y mirador",
            "Visita guiada al lago cercano"
    );

    public TourServiceImpl() {
        toursByCabin.put("Caba\u00f1a 1", List.of(
                "Senderismo en el parque nacional",
                "Excursi\u00f3n en kayak por el r\u00edo"
        ));
        toursByCabin.put("Caba\u00f1a 2", List.of(
                "Cabalgata por los valles",
                "Tour fotogr\u00e1fico al amanecer"
        ));
    }

    @Override
    public List<String> getToursForCabin(String cabinName) {
        return toursByCabin.getOrDefault(cabinName, defaultTours);
    }
}
