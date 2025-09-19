package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.player;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class UnitTests {

    @Test
    @DisplayName("Test création of a new player")
    void testPlayerName() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        assertThat(player.playerName, is("Florian"));
        assertThat(player.Avatar_name, is("Grognak le barbare"));
        assertThat(player.getAvatarClass(), is("ADVENTURER"));
        assertThat(player.money, is(100));
        assertTrue(player.inventory.isEmpty());
    }

    @Test
    @DisplayName("Impossible to have negative money")
    void testNegativeMoney() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        try {
            player.removeMoney(200);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    @Test
    @DisplayName("Add Money")
    void testAddMoney() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        assertEquals(100, player.money);
        player.addMoney(100);
        assertEquals(200, player.money);
    }

}
