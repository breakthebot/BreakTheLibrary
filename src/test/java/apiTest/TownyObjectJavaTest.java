package apiTest;

import org.breakthebot.breakthelibrary.api.TownyAPI;
import org.breakthebot.breakthelibrary.models.Nation;
import org.breakthebot.breakthelibrary.models.Reference;
import org.breakthebot.breakthelibrary.models.Resident;
import org.breakthebot.breakthelibrary.models.Town;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TownyObjectJavaTest {

    @ValueSource(strings = {"Paris", "Giza", "Cairo"})
    @ParameterizedTest
    void testTowns(String name) {
        TownyAPI.INSTANCE.getTownJava(name).thenAccept( (t) -> {
            Town town = t.getOrNull();

            assertNotNull(town);
            assertInstanceOf(Town.class, town);
            assertEquals(name, town.getName());
        });
    }

    @ValueSource(strings = {"charis_k", "Veyronity", "JR1258"})
    @ParameterizedTest()
    void testResidents(String name) {
        TownyAPI.INSTANCE.getPlayerJava(name).thenAccept((p) -> {
            Resident player = p.getOrNull();

            assertNotNull(player);
            assertInstanceOf(Resident.class, player);
            assertEquals(player.getName(), name);
        });
    }

    @ValueSource(strings = {"France", "Egypt", "Germany"})
    @ParameterizedTest
    void testNations(String name) {
        TownyAPI.INSTANCE.getNationJava(name).thenAccept((n) -> {
            Nation nation = n.getOrNull();

            assertNotNull(nation);
            assertInstanceOf(Nation.class, nation);
            assertEquals(nation.getName(), name);
        });
    }

    @ValueSource(strings = {"charis_k", "Veyronity", "JR1258"})
    @ParameterizedTest
    void testD2D(String name) {
        TownyAPI.INSTANCE.getPlayerDiscordJava(name).thenAccept((d) -> {
            String discordName = d.getOrNull();
            assertNotNull(discordName);
            assertInstanceOf(String.class, discordName);
        });
    }

    @Test
    void testGenericApi() throws Exception {
        List<Reference> players = TownyAPI.INSTANCE.getAllPlayersJava()
                .get()
                .getOrNull();

        List<Reference> towns = TownyAPI.INSTANCE.getAllTownsJava()
                .get()
                .getOrNull();

        List<Reference> nations = TownyAPI.INSTANCE.getAllNationsJava()
                .get()
                .getOrNull();

        assertNotNull(players);
        assertNotNull(towns);
        assertNotNull(nations);

        assertInstanceOf(List.class, players);
        assertInstanceOf(List.class, towns);
        assertInstanceOf(List.class, nations);
    }

}
