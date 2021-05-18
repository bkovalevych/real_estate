package com.course.work.buy_and_sale_house.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class District implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    private boolean hidden;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public District() {
        hidden = false;
    }

    public District(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object objDistrict) {
        if (this == objDistrict) {
            return true;
        }
        if (objDistrict == null || getClass() != objDistrict.getClass()) {
            return false;
        }
        District district = (District) objDistrict;
        return Objects.equals(name, district.name);
    }



    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
