package com.jalizadeh.todocial.web.model.gym;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jalizadeh.todocial.web.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
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

    @Min(0)
    @Max(100)
    protected int progress;

    private boolean active;

    /*
     * Lombok generates getters based on the field names. In the case of a boolean field named isPublic,
     * Lombok generates a getter method named isPublic(). However, when using EL (Expression Language) in JSP,
     * it expects the getter method to follow the JavaBeans convention, and for boolean fields,
     * the convention is to have a getter method named getPropertyName().
     */
    @Getter(AccessLevel.NONE)
    private boolean isPublic;

    @Getter(AccessLevel.NONE)
    private boolean isForSale;

    private int price;

    private Date startDate;

    private Date completeDate;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    private Date createdAt;

    private Date updatedAt;


    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean getIsForSale() {
        return isForSale;
    }

    public void setIsForSale(boolean forSale) {
        isForSale = forSale;
    }
}
