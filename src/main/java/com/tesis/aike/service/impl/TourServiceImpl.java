package com.tesis.aike.service.impl;

import com.tesis.aike.service.TourService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourServiceImpl implements TourService {

    @Override
    public List<String> getAvailableTours() {
        return List.of(
                "Recorrido al Lago Moquehue: Un espejo de agua de origen glaciario, ideal para kayak y fotografía. Se puede almorzar en el pueblo.",
                "Visita a Villa Pehuenia: Famosa por sus bosques de araucarias milenarias y su circuito gastronómico.",
                "Ascenso al Volcán Batea Mahuida: Ofrece vistas panorámicas increíbles de los lagos Aluminé y Moquehue. En invierno, es un parque de nieve.",
                "Cascada del arroyo Coloco: Un trekking de baja dificultad que lleva a una hermosa cascada oculta entre la vegetación.",
                "Termas de Copahue: Un viaje de día completo para disfrutar de baños termales con propiedades curativas, ubicadas en un entorno volcánico único."
        );
    }
}