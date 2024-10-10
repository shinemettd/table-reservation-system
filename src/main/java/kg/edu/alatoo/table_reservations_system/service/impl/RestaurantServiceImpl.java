package kg.edu.alatoo.table_reservations_system.service.impl;

import kg.edu.alatoo.table_reservations_system.mapper.RestaurantMapper;
import kg.edu.alatoo.table_reservations_system.payload.RestaurantDTO;
import kg.edu.alatoo.table_reservations_system.repository.RestaurantRepository;
import kg.edu.alatoo.table_reservations_system.service.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestaurantServiceImpl implements RestaurantService {

    RestaurantRepository repository;

    RestaurantMapper mapper;

    @Override
    public Set<RestaurantDTO> getAllAvailable() {
        return repository.findAllNotDeleted().orElseThrow()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public RestaurantDTO getById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow());
    }

    @Override
    public Set<RestaurantDTO> searchByName(String name) {
        return repository.findAllByLikeNameNotDeleted(name).orElseThrow()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public RestaurantDTO getByName(String name) {
        return mapper.toDto(repository.findByNameAndDeletionDateIsNull(name).orElseThrow());
    }

    @Override
    public RestaurantDTO create(RestaurantDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public RestaurantDTO edit(RestaurantDTO dto) {
        var restaurant = repository.findById(dto.id()).orElseThrow();
        restaurant = mapper.toEntity(dto);
        return mapper.toDto(restaurant);
    }

    @Override
    @Transactional
    public void softDeleteById(Long id) {
        var r = repository.findById(id).orElseThrow();
        r.setDeletionDate(LocalDateTime.now());
    }
}
