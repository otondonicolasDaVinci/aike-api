package com.tesis.aike.service.impl;

import com.tesis.aike.helper.ConstantValues;
import com.tesis.aike.helper.mapper.ReservationMapper;
import com.tesis.aike.model.dto.CabinDTO;
import com.tesis.aike.model.dto.ReservationDTO;
import com.tesis.aike.model.dto.UserDTO;
import com.tesis.aike.model.entity.ReservationsEntity;
import com.tesis.aike.repository.CabinRepository;
import com.tesis.aike.repository.ReservationsRepository;
import com.tesis.aike.repository.UsersRepository;
import com.tesis.aike.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {
    private final ReservationsRepository reservationsRepository;
    private final UsersRepository usersRepository;
    private final CabinRepository cabinRepository;
    private final ReservationMapper mapper;

    @Autowired
    public ReservationServiceImpl(ReservationsRepository reservationsRepository,
                                  ReservationMapper mapper,
                                  UsersRepository usersRepository,
                                  CabinRepository cabinRepository) {
        this.reservationsRepository = reservationsRepository;
        this.mapper = mapper;
        this.usersRepository = usersRepository;
        this.cabinRepository = cabinRepository;
    }

    public List<ReservationDTO> findAll() {
        return reservationsRepository.findAll().stream().map(this::toDTOFull).toList();
    }

    @Override
    public void updateStatus(Long id, String status) {
        ReservationsEntity entity = reservationsRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, ConstantValues.ReservationService.NOT_FOUND));
        entity.setStatus(status);
        reservationsRepository.save(entity);
    }


    public ReservationDTO create(ReservationDTO dto) {
        if (dto.getCabin() == null || dto.getCabin().getId() == null ||
                dto.getUser() == null || dto.getUser().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ConstantValues.ReservationService.INVALID_DATA);
        }

        validateAvailability(dto.getCabin().getId(), dto.getStartDate(), dto.getEndDate());
        ReservationsEntity saved = reservationsRepository.save(mapper.toEntity(dto));
        return toDTOFull(saved);
    }

    public ReservationDTO update(Long id, ReservationDTO dto) {
        ReservationsEntity entity = reservationsRepository.findById(Math.toIntExact(id)).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantValues.ReservationService.NOT_FOUND));
        validateAvailability(entity.getCabinId(), dto.getStartDate(), dto.getEndDate());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setStatus(dto.getStatus());
        return toDTOFull(entity);
    }

    public void delete(Long id) {
        reservationsRepository.deleteById(Math.toIntExact(id));
    }

    public ReservationDTO findById(Long id) {
        return toDTOFull(reservationsRepository.findById(Math.toIntExact(id)).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantValues.ReservationService.NOT_FOUND)));
    }

    public List<ReservationDTO> findByUserId(Long userId) {
        return reservationsRepository.findByUserId(userId).stream().map(this::toDTOFull).toList();
    }

    private void validateAvailability(Long cabinId, LocalDate start, LocalDate end) {
        boolean overlaps = reservationsRepository.existsByCabinIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                cabinId, end, start);
        if (overlaps) throw new ResponseStatusException(
                HttpStatus.CONFLICT, ConstantValues.ReservationService.CABIN_NOT_AVAILABLE);
    }

    private ReservationDTO toDTOFull(ReservationsEntity entity) {
        ReservationDTO dto = mapper.toDTO(entity);

        usersRepository.findById(Math.toIntExact(entity.getUserId())).ifPresent(u -> {
            UserDTO user = new UserDTO();
            user.setId(u.getId());
            user.setName(u.getName());
            user.setDni(u.getDni());
            dto.setUser(user);
        });

        cabinRepository.findById(Math.toIntExact(entity.getCabinId())).ifPresent(c -> {
            CabinDTO cabin = new CabinDTO();
            cabin.setId(c.getId());
            cabin.setName(c.getName());
            dto.setCabin(cabin);
        });

        return dto;
    }

    @Override
    public boolean hasActiveReservation(Long userId) {
        return reservationsRepository.existsActiveReservationByUserId(userId);
    }

}