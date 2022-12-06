package com.example.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class Avatar {
    @Id
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;

    @Lob
    @JsonIgnore
    private byte[] data;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private Student student;
}
