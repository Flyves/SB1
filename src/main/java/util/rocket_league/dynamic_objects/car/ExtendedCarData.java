package util.rocket_league.dynamic_objects.car;


import util.rocket_league.team.TeamType;

public class ExtendedCarData extends CarData {

    public final int playerIndex;
    public final boolean isSupersonic;

    /**
     * 0 for blue team, 1 for orange team.
     */
    public final TeamType team;
    public final boolean hasWheelContact;

    public final boolean isBot;

    public ExtendedCarData(rlbot.flat.PlayerInfo playerInfo, int playerIndex, float elapsedSeconds) {
        super(playerInfo, elapsedSeconds);
        this.playerIndex = playerIndex;
        this.isBot = playerInfo.isBot();
        this.team = playerInfo.team() == 0 ? TeamType.BLUE : TeamType.ORANGE;

        this.hasWheelContact = playerInfo.hasWheelContact();
        this.isSupersonic = playerInfo.isSupersonic();
    }
}
