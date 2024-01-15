package com.jalizadeh.todocial.web.model.gym;

import com.jalizadeh.todocial.web.model.User;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
public class GymPlan {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    //on Gym homepage, I need to have quick access to Plan's details
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "gym_plan_introduction")
    private GymPlanIntroduction gymPlanIntroduction;

    @OneToMany(mappedBy = "plan", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<GymDay> days = new ArrayList<>();

    @Min(1)
    private int numberOfWeeks;

    @Min(1)
    @Max(7)
    private int numberOfDays;

    @Min(0)
    private long completedDays;

    private boolean active;

    /*
     * Lombok generates getters based on the field names. In the case of a boolean field named isPublic,
     * Lombok generates a getter method named isPublic(). However, when using EL (Expression Language) in JSP,
     * it expects the getter method to follow the JavaBeans convention, and for boolean fields,
     * the convention is to have a getter method named getPropertyName().
     */
    @Getter(AccessLevel.NONE)
    private boolean isPublic;

    private Date startDate;

    private Date completeDate;

    @Min(0)
    @Max(100)
    protected int progress;

    public boolean getIsPublic() {
        return isPublic;
    }
}
