package repository;

import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.repository.GymPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GymRepositoryTest {

    @Autowired
    GymPlanRepository planRepository;

    private GymPlan plan;

    private final String PLAN_TITLE = "test plan";

    @BeforeEach
    public void prepareTestData(){
        plan = new GymPlan();
        plan.setTitle(PLAN_TITLE);
        plan.setActive(false);
    }

    @Test
    public void GymPlan_savePlan_returnsSavedPlan(){
        //Arrange

        //Act
        GymPlan savedPlan = planRepository.save(plan);

        //Assert
        assertNotNull(savedPlan);
        assertTrue(savedPlan.getId() > 0);
        assertEquals(savedPlan.getTitle(), PLAN_TITLE);
    }
}
