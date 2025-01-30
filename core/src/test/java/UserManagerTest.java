import io.ISSProject.game.model.userManagment.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    public void setUp() {
        userManager = UserManager.getInstance();
        userManager.readUsers();
    }

    @AfterEach
    public void tearDown() {
        userManager = null;
    }

    /*
    Dopo la prima creazione del file Json testUser2 è stato già creato,
    quindi per assicurarci che anche i test successivi soddisifino il test,
    rimuoviamo inizialmente il nome dell'utente.
    */
    @Test
    public void testRegisterNewUser_Success() {
        userManager.deleteUser("testUser2");
        boolean result = userManager.registerNewUser(new User("testUser2"));
        userManager.showAllUsers();
        assertTrue(result, "L'utente dovrebbe essere registrato con successo.");
    }

    @Test
    public void testRegisterNewUser_Duplicate() {
        userManager.registerNewUser(new User("duplicateUser"));
        boolean result = userManager.registerNewUser(new User("duplicateUser"));
        assertFalse(result, "Non dovrebbe essere possibile registrare lo stesso utente due volte.");
    }

    @Test
    public void testFindUserByName_UserExists() {
        User existingUser = new User("existingUser");
        userManager.registerNewUser(existingUser);
        User foundUser = userManager.findUserByName(existingUser.getUsername());
        assertNotNull(foundUser, "L'utente dovrebbe esistere nel sistema.");
        assertEquals(existingUser.getUsername(), foundUser.getUsername(), "Lo username dell'utente trovato non corrisponde.");
    }

    @Test
    public void testFindUserByName_UserDoesNotExist() {
        User foundUser = userManager.findUserByName("nonExistentUser");
        assertNull(foundUser, "Non dovrebbe essere trovato alcun utente inesistente.");
    }

    @Test
    public void testDeleteUser_UserExists() {
        User userToDelete = new User("userToDelete");
        userManager.registerNewUser(userToDelete);
        boolean deleted = userManager.deleteUser(userToDelete.getUsername());
        assertTrue(deleted, "L'utente dovrebbe essere eliminato con successo.");
        assertNull(userManager.findUserByName(userToDelete.getUsername()), "L'utente non dovrebbe più esistere dopo l'eliminazione.");
    }

    @Test
    public void testDeleteUser_UserDoesNotExist() {
        boolean deleted = userManager.deleteUser("nonExistentUser");
        assertFalse(deleted, "Non dovrebbe essere possibile eliminare un utente inesistente.");
    }

    @Test
    public void testUpdateUserData_Success() {
        String oldUsername = "oldUser";
        String newUsername = "newUser";
        userManager.registerNewUser(new User(oldUsername));
        User user = userManager.findUserByName(oldUsername);
        boolean updated = userManager.updateUserData(user, newUsername);
        assertTrue(updated, "L'aggiornamento dell'utente dovrebbe avere successo.");
        assertNull(userManager.findUserByName(oldUsername), "Il vecchio username non dovrebbe più esistere.");
        assertNotNull(userManager.findUserByName(newUsername), "Il nuovo username dovrebbe esistere nel sistema.");
    }

    @Test
    public void testUpdateUserData_UserDoesNotExist() {
        User nonExistentUser = new User("nonExistentUser");
        boolean updated = userManager.updateUserData(nonExistentUser, "newUsername");
        assertFalse(updated, "Non dovrebbe essere possibile aggiornare un utente inesistente.");
    }

    @Test
    public void testSaveAndReadUsers() {
        String username = "persistedUser";
        userManager.registerNewUser(new User(username));
        userManager.saveUsers();

        // Crea una nuova istanza per simulare una nuova sessione
        UserManager newManagerInstance = UserManager.getInstance();
        newManagerInstance.readUsers();

        User foundUser = newManagerInstance.findUserByName(username);
        assertNotNull(foundUser, "L'utente dovrebbe essere presente dopo il salvataggio e la lettura.");
        assertEquals(username, foundUser.getUsername(), "Lo username dell'utente salvato non corrisponde.");
    }

    @Test
    public void testLoginUser_Success() {
        String username = "loginUser";
        userManager.registerNewUser(new User(username));
        boolean loginResult = userManager.loginUser(username);
        assertTrue(loginResult, "L'accesso dovrebbe avere successo per un utente registrato.");
    }

    @Test
    public void testLoginUser_UserDoesNotExist() {
        boolean loginResult = userManager.loginUser("nonExistentUser");
        assertFalse(loginResult, "L'accesso non dovrebbe essere possibile per un utente inesistente.");
    }
}
