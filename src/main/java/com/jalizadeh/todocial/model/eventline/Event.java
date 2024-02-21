package com.jalizadeh.todocial.model.eventline;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jalizadeh.todocial.model.user.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "eventline")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //used Java.Utils Date class for better organization of same-day events
    private Date date;

    private String title;

    private String text;

    private int likes;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @Getter(AccessLevel.NONE)
    private boolean isPublic;

    public boolean getIsPublic() {
        return isPublic;
    }

}
