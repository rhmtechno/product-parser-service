package com.java.parser.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

    @CreationTimestamp
    @Column(nullable = false, name = "CREATED_DATE")
    private Date createdDate;


    @UpdateTimestamp
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;


    @Version
    private Long version;
}
