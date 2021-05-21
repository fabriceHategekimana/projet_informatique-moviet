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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(JpaUnit.class) // see documentation of dadrus jpa unit, junit 5
@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {
    /*
    Recall that tests are not always run with the same order as in the code..

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
        int random_choice = (int) (Math.random() * groups.size());
        int id = groups.get(random_choice).getId(); // get the id through Java object ! (list of groups)

        Group grp= groupServiceImpl.getGroup(id); // get the specific group through the business service
        assertEquals(groups.get(random_choice).getId(), grp.getId()); // check the ids
        assertEquals(groups.get(random_choice).getName(), grp.getName());
    }

    @Test
    void testGetNonExistantGroup() {
        assertNull(groupServiceImpl.getGroup(Integer.MAX_VALUE)); // check if null (when we get a non existant group)
    }

    @Test
    void testCreateGroup() { // TODO: test Group input entered in createGroup in Impl
        Group group = getRandomGroup();
        Group returned_group = groupServiceImpl.createGroup(group);
        assertNotEquals(0, returned_group.getId()); // check if id not 0 (meaning that the id was incremented and initialized)
    }

    @Test
    void testCreateNoNameGroup() {
        Group group = getRandomGroupNoName();
        Group returned_group = groupServiceImpl.createGroup(group);
        assertNull(returned_group); // check if null because trying to create group without name
    }

    @Test
    void testCreateNullGroup(){
        Group grp = null;
        assertThrows(NullPointerException.class, ()-> groupServiceImpl.createGroup(grp)); // due to @NotNull annotation in Impl
    }


    @Test
    void testCreateWithIdGroup() {
        initDataStore();  // create new groups
        List<Group> groups = groupServiceImpl.getAllGroups(); // get list of groups through the business service
        int random_choice = (int) (Math.random() * groups.size());
        Group group = groups.get(random_choice);
        assertNull(groupServiceImpl.createGroup(group)); // check if null because trying to create group with an id
    }


    @Test
    void testUpdateGroup() { // TODO: test Group input entered in updateGroup in Impl
        // create a group and modify its name
        groupServiceImpl.createGroup(getRandomGroup());
        List<Group> groups = groupServiceImpl.getAllGroups(); // get list of groups through the business service
        Group group = groups.get(groups.size() - 1);  // get last group

        assertNotNull(group);
        int id = group.getId();
        group.setName("XXX");
        groupServiceImpl.updateGroup(group);
        group = groupServiceImpl.getGroup(id);
        assertEquals("XXX", group.getName());
    }

    @Test
    void testUpdateNonExistantGroup() {
        initDataStore();  // create new groups
        List<Group> groups = groupServiceImpl.getAllGroups(); // get list of groups through the business service
        // delete the last one
        Group old_group = groups.get(groups.size() - 1);  // get last group
        assertNotNull(old_group);
        int id = old_group.getId();
        groupServiceImpl.deleteGroup(id);
        Group group = groupServiceImpl.getGroup(id);
        assertNull(group); // check that the group disappeared

        // old_group was deleted, try to update it
        assertNull(groupServiceImpl.updateGroup(old_group)); // due to @NotNull annotation in Impl
    }

    @Test
    void testUpdateNullGroup() {
        Group grp = null;
        assertThrows(NullPointerException.class, ()-> groupServiceImpl.updateGroup(grp)); // due to @NotNull annotation in Impl
    }

    @Test
    void testDeleteGroup() {
        // create a group and then delete one of the groups
        groupServiceImpl.createGroup(getRandomGroup());
        List<Group> groups = groupServiceImpl.getAllGroups(); // get list of groups through the business service
        Group group = groups.get(groups.size() - 1);  // get last group

        assertNotNull(group);
        int id = group.getId();
        groupServiceImpl.deleteGroup(id);
        group = groupServiceImpl.getGroup(id);
        assertNull(group); // check that the group disappeared
    }

    @Test
    void testDeleteNonExistantGroup() {
        assertNull(groupServiceImpl.deleteGroup(Integer.MAX_VALUE));  // check that we "cannot delete" non existant group
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

    private Group getRandomGroupNoName() {
        Group g = new Group();
        return g;
    }
}