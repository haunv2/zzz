package com.repository;

import com.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findByTitle(String title);

    List<Video> findALLBySeason(String season);

    List<Video> findAllByStatus(String status);

    List<Video> findAllByDurationBetween(Integer min, Integer max);

    List<Video> findAllBySeasonAndStatus(String season, String status);
}