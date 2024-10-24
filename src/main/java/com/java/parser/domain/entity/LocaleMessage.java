package com.java.parser.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "LOCALE_MESSAGE")
public class LocaleMessage implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOCALE")
    private String locale;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "VERSION")
    private String version;

}
