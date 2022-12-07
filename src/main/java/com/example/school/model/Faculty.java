package com.example.school.model;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
public class Faculty {
    @Id
    @GeneratedValue
    private long id;

    @Size(min = 2, max = 30)
    private String name;

    @Size(min = 2, max = 30)
    private String color;

    @OneToMany(mappedBy = "faculty")
    private Set<Student> students;

    public Faculty() {
    }

    public Faculty(long id, String name, String color, Set<Student> students) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.students = students;
    }
    public Faculty(long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.students = Collections.emptySet();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}