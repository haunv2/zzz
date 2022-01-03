package com.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "video")
@Setter
@Getter
@ToString
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 500)
    private String title;

    @Column(name = "titleJp", length = 500)
    private String titleJp;

    @Column(name = "poster")
    private String poster;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "views")
    private Integer views;

    @Column(name = "active")
    private Integer active;

    @Column(name = "alternateTtitle", length = 500)
    private String alternateTtitle;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "season", length = 6)
    private String season;

    @Column(name = "year")
    private Integer year;

    @Column(name = "releaseDate", length = 100)
    private String releaseDate;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "idAnilist")
    private Long idAnilist;

    @Column(name = "dateCreate")
    private Timestamp dateCreate;

    @Column(name = "imageBanner", length = 500)
    private String imageBanner;

    @Column(name = "dateUpdate")
    private Timestamp dateUpdate;

    @Column(name = "trailer", length = 500)
    private String trailer;

}