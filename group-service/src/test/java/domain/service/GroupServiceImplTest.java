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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(JpaUnit.class) // see documentation of dadrus jpa unit, junit 5
@ExtendWith(MockitoExtension.class)
public class GroupServiceImplTest {
    /*
    Create random groups and :
    - check if can get all by counting how many
    - check if can get a particular group by getting all groups and comparing a random group with a group obtained
    through getGroup(id) (id obtained from the random chosen group)

    */
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

    @Test
    void testGetGroup() {
        initDataStore();  // create new groups
        List<Group> groups = groupServiceImpl.getAllGroups(); // get list of groups through the business service
        int random_choice = (int) (Math.random() * groups.size()); // between
        int id = groups.get(random_choice).getId(); // get the id through Java object ! (list of groups)

        Group grp= groupServiceImpl.getGroup(id); // get the specific group through the business service
        assertEquals(groups.get(random_choice).getId(), grp.getId()); // check the ids
        assertEquals(groups.get(random_choice).getName(), grp.getName());
    }

    @Test
    void testGetNonExistantGroup() {
        initDataStore();  // create new groups
        List<Group> groups = groupServiceImpl.getAllGroups(); // get list of groups through the business service
        Group grp= groupServiceImpl.getGroup(groups.size() + 1); // get non existant group
        assertNull(grp); // check if null
    }

    /*
    @Test
    void testCreateGroup() { // TODO: test Group input entered in createGroup in Impl

        initDataStore();  // create new groups
        List<Group> groups = groupServiceImpl.getAllGroups(); // get list of groups through the business service
        int random_choice = (int) (Math.random() * groups.size()); // between
        int id = groups.get(random_choice).getId(); // get the id through Java object ! (list of groups)

        Group grp= groupServiceImpl.getGroup(id); // get the specific group through the business service
        assertEquals(groups.get(random_choice).getId(), grp.getId()); // check the ids
        assertEquals(groups.get(random_choice).getName(), grp.getName());
    }
    */

    @Test
    void testCreateGroup() {
        Group group = getRandomGroup();
        Group returned_group = groupServiceImpl.createGroup(group);
        assertNotNull(returned_group.getId()); // check if id not null
    }

    @Test
    void testCreateNoNameGroup() {
        Group group = getRandomGroupNoName();
        Group returned_group = groupServiceImpl.createGroup(group);
        assertNull(returned_group); // check if null because trying to create group without name
    }

    /*
    @Test
    void testCreateWithIdGroup() {
        Group group = getRandomGroupNoName();
        Group returned_group = groupServiceImpl.createGroup(group);
        assertNull(returned_group); // check if null because trying to create group without name
    }
    */

    /*
    @Test
    void testCreateDuplicate() {
        Instrument instrument = getRandomInstrument();
        instrumentService.create(instrument);
        assertThrows(IllegalArgumentException.class, () -> {
            instrumentService.create(instrument);
        });
    }
    */

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

    private Group getRandomGroupNoName() {
        Group g = new Group();
        return g;
    }
}