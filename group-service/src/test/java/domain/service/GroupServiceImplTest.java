package domain.service;

import domain.service.GroupServiceImpl;
import domain.model.Group;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import eu.drus.jpa.unit.api.JpaUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class GroupServiceImplTest {
    // dependency injections
    @Spy
    @PersistenceContext(unitName = "GroupPUTest") // name is the same as in persistence.xml file. Test database !
    EntityManager em;
    @InjectMocks
    private GroupServiceImpl groupServiceImpl;

    @Test
    void testGetAllGroups() {
        // Count the number of groups after adding new groups and compare it to previous # of groups + number of groups added
        int size = initDataStore();
        assertEquals(size, groupServiceImpl.getAllGroups().size());
    }

    private List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();
        long numberOfNewGrp = Math.round((Math.random() * 10)) + 5;
        for (int i = 0; i < numberOfNewGrp; i++) {
            groups.add(getRandomGroup());
        }
        return groups;
    }

    private int initDataStore() {
        int size = groupServiceImpl.getAllGroups().size();
        List<Group> newGroups = getGroups();
        for (Group g : newGroups) {
            em.persist(g);
        }
        return size + newGroups.size();
    }

    private Group getRandomGroup() {
        Group g = new Group();
        g.setName(UUID.randomUUID().toString());  // random name
        return g;
    }
}