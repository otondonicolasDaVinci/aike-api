package com.tesis.aike.service; // Asegúrate de usar el paquete correcto para tus servicios

import com.tesis.aike.model.entity.ReservationsEntity; // Asegúrate de que la ruta a tu entidad sea correcta
import com.tesis.aike.repository.ReservationsRepository; // Asegúrate de que la ruta a tu repositorio sea correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationsService {

    @Autowired
    private ReservationsRepository reservationsRepository;

    public List<ReservationsEntity> obtenerTodasLasReservas() {
        return reservationsRepository.findAll();
    }

    public Optional<ReservationsEntity> obtenerReservaPorId(int id) {
        return reservationsRepository.findById(id);
    }

    public List<ReservationsEntity> obtenerReservasPorUsuario(int userId) {
        return reservationsRepository.findByUserId(userId);
    }

    public List<ReservationsEntity> obtenerReservasPorCabina(int cabinId) {
        return reservationsRepository.findByCabinId(cabinId);
    }

    public List<ReservationsEntity> obtenerReservasEnRangoDeFechas(Date startDate, Date endDate) {
        return reservationsRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(startDate, endDate);
    }

    public List<ReservationsEntity> obtenerReservasPorEstado(String status) {
        return reservationsRepository.findByStatus(status);
    }

    public List<ReservationsEntity> obtenerReservasPorFechaInicio(Date startDate) {
        return reservationsRepository.findByStartDate(startDate);
    }

    public List<ReservationsEntity> obtenerReservasPorFechaFin(Date endDate) {
        return reservationsRepository.findByEndDate(endDate);
    }

    public ReservationsEntity guardarReserva(ReservationsEntity reservation) {
        return reservationsRepository.save(reservation);
    }

    public void eliminarReserva(int id) {
        reservationsRepository.deleteById(id);
    }

    // Puedes agregar más métodos de lógica de negocio relacionados con las reservas aquí
}