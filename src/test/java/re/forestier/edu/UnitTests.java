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
    @DisplayName("Get 0 money")
    void testZeroMoney() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 50, new ArrayList<>());

        assertEquals(50, player.money);
        player.removeMoney(50);
        assertEquals(0, player.money);
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
    @DisplayName("Level Up AddXp")
    void testLevelUpAddXp() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        assertThat(player.getXp(), is(0));
        assertThat(player.retrieveLevel(), is(1));
        assertFalse(UpdatePlayer.addXp(player, 9));
        assertThat(player.getXp(), is(9));
        assertThat(player.retrieveLevel(), is(1));
        assertTrue(UpdatePlayer.addXp(player, 9));
        assertThat(player.getXp(), is(18));
        assertThat(player.retrieveLevel(), is(2));

        assertEquals(2, player.abilities.get("INT"));
        assertEquals(1, player.abilities.get("DEF"));
        assertEquals(3, player.abilities.get("ATK"));
        assertEquals(3, player.abilities.get("CHA"));

        assertTrue(UpdatePlayer.addXp(player, 20));
        assertThat(player.getXp(), is(38));
        assertThat(player.retrieveLevel(), is(3));

        assertEquals(2, player.abilities.get("INT"));
        assertEquals(1, player.abilities.get("DEF"));
        assertEquals(5, player.abilities.get("ATK"));
        assertEquals(1, player.abilities.get("ALC"));
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

    @Test
    @DisplayName("Test adding object when level up")
    void testAddObject() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        assertTrue(player.inventory.isEmpty());
        assertThat(player.retrieveLevel(), is(1));
        UpdatePlayer.addXp(player, 20);
        assertThat(player.retrieveLevel(), is(2));
        assertThat(player.inventory.size(), is(1));
    }

    @Test
    @DisplayName("Test end of round")
    void testMajFinTour() {
        player playerAdventurer = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        player playerDWARF = new player("Jules", "Jules le Sage", "DWARF", 100, new ArrayList<>());
        player playerARCHER = new player("Elf", "Elf la belle", "ARCHER", 100, new ArrayList<>());

        playerAdventurer.healthpoints = 50;
        playerAdventurer.currenthealthpoints = 0;
        UpdatePlayer.majFinDeTour(playerAdventurer);
        assertEquals(0, playerAdventurer.currenthealthpoints);

        playerAdventurer.currenthealthpoints = 30;
        UpdatePlayer.majFinDeTour(playerAdventurer);
        assertEquals(30, playerAdventurer.currenthealthpoints);

        playerAdventurer.currenthealthpoints = 100;
        UpdatePlayer.majFinDeTour(playerAdventurer);
        assertEquals(playerAdventurer.healthpoints, playerAdventurer.currenthealthpoints);

        playerAdventurer.currenthealthpoints = 24;
        UpdatePlayer.majFinDeTour(playerAdventurer);
        assertEquals(25, playerAdventurer.currenthealthpoints);

        UpdatePlayer.addXp(playerAdventurer, 30);
        playerAdventurer.currenthealthpoints = 1;
        playerAdventurer.healthpoints = 4;
        assertEquals(3, playerAdventurer.retrieveLevel());
        UpdatePlayer.majFinDeTour(playerAdventurer);
        assertEquals(3, playerAdventurer.currenthealthpoints);

        playerDWARF.currenthealthpoints = 10;
        playerDWARF.healthpoints = 50;
        UpdatePlayer.majFinDeTour(playerDWARF);
        assertEquals(11, playerDWARF.currenthealthpoints);

        playerDWARF.inventory.add("Holy Elixir");
        UpdatePlayer.majFinDeTour(playerDWARF);
        assertEquals(13, playerDWARF.currenthealthpoints);

        playerARCHER.currenthealthpoints = 10;
        playerARCHER.healthpoints = 50;
        UpdatePlayer.majFinDeTour(playerARCHER);
        assertEquals(11, playerARCHER.currenthealthpoints);

        playerARCHER.inventory.add("Magic Bow");
        UpdatePlayer.majFinDeTour(playerARCHER);
        assertEquals(12, playerARCHER.currenthealthpoints);
        UpdatePlayer.majFinDeTour(playerARCHER);
        assertEquals(13, playerARCHER.currenthealthpoints);
        UpdatePlayer.majFinDeTour(playerARCHER);
        assertEquals(14, playerARCHER.currenthealthpoints);
        UpdatePlayer.majFinDeTour(playerARCHER);
        assertEquals(15, playerARCHER.currenthealthpoints);
        UpdatePlayer.majFinDeTour(playerARCHER);
        assertEquals(17, playerARCHER.currenthealthpoints);
        UpdatePlayer.majFinDeTour(playerARCHER);
        assertEquals(19, playerARCHER.currenthealthpoints);
    }
}
