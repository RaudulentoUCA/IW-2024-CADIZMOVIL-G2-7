package es.uca.iw.news;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "creation_date")
    private LocalDate creation_date;

    @Lob
    @Column(name = "image")
    private byte[] imageData;
}
