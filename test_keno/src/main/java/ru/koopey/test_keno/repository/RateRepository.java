package ru.koopey.test_keno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.koopey.test_keno.entity.Rate;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    Rate getRateByUuid(String uuid);
    List<Rate> getRatesByPlayerIdOrderByRoundDesc(String playerId);
}
