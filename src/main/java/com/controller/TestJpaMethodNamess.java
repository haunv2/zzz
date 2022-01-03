package com.controller;

import com.model.ResponseData;
import com.model.Video;
import com.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/video")
public class TestJpaMethodNamess {

    @Autowired
    VideoRepository repo;

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getVideoByTitle(@PathVariable(value = "title") String title) {
        Video v = repo.findByTitle(title);

        return ResponseEntity.ok(new ResponseData(v, null, null));
    }

    @GetMapping("/season/{season}")
    public ResponseEntity<?> getAllBySeason(@PathVariable(value="season") String season) {
        List<Video> v = repo.findALLBySeason(season);

        return ResponseEntity.ok(new ResponseData(v, null, null));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getVideoByStatus(@PathVariable(value="status") String status) {
        List<Video> v = repo.findAllByStatus(status);

        return ResponseEntity.ok(new ResponseData(v, null, null));
    }

    @GetMapping("/duration/")
    public ResponseEntity<?> getVideoByDurationBetween(@RequestParam(value = "min") Integer minDuration, @RequestParam(value = "min") Integer maxDuration) {
        List<Video> v = repo.findAllByDurationBetween(minDuration, maxDuration);
        return ResponseEntity.ok(new ResponseData(v, null, null));
    }

    @GetMapping("/seasonStatus")
    public ResponseEntity<?> getAllBySeasonAndStatus(@RequestParam(value = "title") String title, @RequestParam(value = "status") String status) {
        List<Video> v = repo.findAllBySeasonAndStatus(title, status);
        return ResponseEntity.ok(new ResponseData(v, null, null));
    }
}
