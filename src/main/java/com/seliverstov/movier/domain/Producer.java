package com.seliverstov.movier.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "producer")
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "producer_id", unique = true)
    private Long id;

    @Column(name = "producer_name")
    @NotNull
    private String name;

    @Column(name = "producer_surname")
    @NotNull
    private String surname;

    @Column(name = "producer_country")
    private String country;

    @Column(name = "producer_date")
    private String date;

    @OneToMany(mappedBy = "producers")
    private Set<Movie> movies = new HashSet<>();

    @ManyToMany(mappedBy = "producers")
    private List<Genres> genres = new ArrayList<>();
}
