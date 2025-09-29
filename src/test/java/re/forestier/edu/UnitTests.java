package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.UpdatePlayer;
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

        player playerDWARF = new player("Jules", "Jules le Sage", "DWARF", 100, new ArrayList<>());
        assertThat(playerDWARF.playerName, is("Jules"));
        assertThat(playerDWARF.Avatar_name, is("Jules le Sage"));

        player playerARCHER = new player("Elf", "Elf la belle", "ARCHER", 100, new ArrayList<>());
        assertThat(playerARCHER.playerName, is("Elf"));
        assertThat(playerARCHER.Avatar_name, is("Elf la belle"));
    }

    @Test
    @DisplayName("Test creation of a new player failed")
    void testPlayerCreationFailed() {
        player player = new player("Florian", "Grognak le barbare", "SKELETTON", 100, new ArrayList<>());

        assertThat(player.playerName, is(nullValue()));
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
    @DisplayName("Remove money")
    void testRemoveMoney() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        assertEquals(100, player.money);
        player.removeMoney(50);
        assertEquals(50, player.money);
    }

    @Test
    @DisplayName("Add Money")
    void testAddMoney() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        assertEquals(100, player.money);
        player.addMoney(100);
        assertEquals(200, player.money);
    }

    @Test
    @DisplayName("Retrieve level")
    void testRetrieveLevel() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        assertThat(player.getXp(), is(0));
        assertThat(player.retrieveLevel(), is(1));
        UpdatePlayer.addXp(player, 9);
        assertThat(player.getXp(), is(9));
        assertThat(player.retrieveLevel(), is(1));

        UpdatePlayer.addXp(player, 1);
        assertThat(player.getXp(), is(10));
        assertThat(player.retrieveLevel(), is(2));
        UpdatePlayer.addXp(player, 16);
        assertThat(player.getXp(), is(26));
        assertThat(player.retrieveLevel(), is(2));

        UpdatePlayer.addXp(player, 1);
        assertThat(player.getXp(), is(27));
        assertThat(player.retrieveLevel(), is(3));
        UpdatePlayer.addXp(player, 29);
        assertThat(player.getXp(), is(56));
        assertThat(player.retrieveLevel(), is(3));

        UpdatePlayer.addXp(player, 1);
        assertThat(player.getXp(), is(57));
        assertThat(player.retrieveLevel(), is(4));
        UpdatePlayer.addXp(player, 53);
        assertThat(player.getXp(), is(110));
        assertThat(player.retrieveLevel(), is(4));

        UpdatePlayer.addXp(player, 1);
        assertThat(player.getXp(), is(111));
        assertThat(player.retrieveLevel(), is(5));
        UpdatePlayer.addXp(player, 20);
        assertThat(player.getXp(), is(131));
        assertThat(player.retrieveLevel(), is(5));
    }
}
