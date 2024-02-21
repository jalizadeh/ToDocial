package com.jalizadeh.todocial.model.eventline;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jalizadeh.todocial.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "eventline")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private String title;

    private String text;

    private int likes;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;
}
