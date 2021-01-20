import game.Player;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class MovementTest {

    Player player, playerAtEdgeOfMap, playerInHospital,
            playerAtDoorOfHospital;
    @Before
    public void setUp() {
        player = new Player("test", null, 9, 9);
        playerAtEdgeOfMap = new Player("edge_player", null, 11, 0);
        playerInHospital = new Player("hospital_player", null, 1, 3);
        playerAtDoorOfHospital = new Player("door_player", null, 8, 3);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void playerWillMove() {
        if(player.canMove('W'))
            player.moveOtherCharacters('W');
        assertEquals(8, player.getLine());
        assertEquals(9, player.getColumn());
    }

    @Test
    public void playerWillNotMoveIntoWall() {
        if(player.canMove('A'))
            player.moveOtherCharacters('A');
        assertEquals(9, player.getLine());
        assertEquals(8, player.getColumn());

        if(player.canMove('A'))
            player.moveOtherCharacters('A');
        assertEquals(9, player.getLine());
        assertEquals(8, player.getColumn());
    }

    @Test
    public void playerWillNotMoveOutsideMap() {
        if(playerAtEdgeOfMap.canMove('A'))
            playerAtEdgeOfMap.moveOtherCharacters('A');
        assertEquals(11, playerAtEdgeOfMap.getLine());
        assertEquals(0, playerAtEdgeOfMap.getColumn());
    }

    @Test
    public void playerWithOneMoveCanOnlyMoveOnce() {
        player.setMovesLeft(1);

        if (player.canMove('D'))
            player.moveInDirection('D');
        assertEquals(9, player.getLine());
        assertEquals(10, player.getColumn());

        if (player.canMove('D'))
            player.moveInDirection('D');
        assertEquals(9, player.getLine());
        assertEquals(10, player.getColumn());

    }

    @Test
    public void playerEnteringRoomWillHaveThatRoomCode() {
        playerInHospital.setMovesLeft(2);

        if (playerInHospital.canMove('D'))
            playerInHospital.moveInDirection('D');
        assertEquals(2, playerInHospital.getRoom());
    }

    @Test
    public void playerExitingRoomWillNotHaveThatRoomCode() {
        playerAtDoorOfHospital.setMovesLeft(2);
        if (playerAtDoorOfHospital.canMove('D'))
            playerAtDoorOfHospital.moveInDirection('D');
        assertEquals(0, playerAtDoorOfHospital.getRoom());
    }
}
