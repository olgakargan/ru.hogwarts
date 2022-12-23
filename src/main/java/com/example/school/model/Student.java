package com.example.school.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Min(9)
    @Max(65)
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    private Faculty faculty;

    @OneToOne(mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Avatar avatar;

    public void setAvatar(Avatar avatar) {
        if (avatar == null) {
            if (this.avatar != null) {
                this.avatar.setStudent(null);
            }
        } else {
            avatar.setStudent(this);
        }
        this.avatar = avatar;
    }
}