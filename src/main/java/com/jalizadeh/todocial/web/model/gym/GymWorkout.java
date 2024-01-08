package com.jalizadeh.todocial.web.model.gym;

import com.jalizadeh.todocial.web.model.gym.types.GymEquipment;
import com.jalizadeh.todocial.web.model.gym.types.GymMuscleCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class GymWorkout {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String photo;

    private String description;

    @ElementCollection(targetClass = GymMuscleCategory.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "gym_workout_muscle", joinColumns = @JoinColumn(name = "workout_id"))
    @Column(name = "muscle_category")
    private Set<GymMuscleCategory> muscleCategory;

    @ElementCollection(targetClass = GymEquipment.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "gym_workout_equipment", joinColumns = @JoinColumn(name = "workout_id"))
    @Column(name = "equipment")
    private Set<GymEquipment> equipment;

    //used for custom item label in the page's Options tag
    public String getFullLabel(){
        StringBuilder fullLabel = new StringBuilder();

        // Append muscle categories
        if (muscleCategory != null && !muscleCategory.isEmpty()) {
            fullLabel.append("[ ");
            fullLabel.append(muscleCategory.stream()
                    .map(GymMuscleCategory::name)
                    .collect(Collectors.joining(", ")));
            fullLabel.append(" ]");
        }

        fullLabel.append(" ").append(name);

        return fullLabel.toString();
    }


}
