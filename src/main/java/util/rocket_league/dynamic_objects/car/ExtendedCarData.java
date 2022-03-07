package util.rocket_league.dynamic_objects.car;


import rlbot.flat.PlayerInfo;
import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.rocket_league.team.TeamType;

import java.util.List;
import java.util.stream.Collectors;

public class ExtendedCarData extends CarData {

    public final int playerIndex;
    public final boolean isSupersonic;

    public final Vector3 acceleration;
    public final Vector3 angularAcceleration;

    /**
     * 0 for blue team, 1 for orange team.
     */
    public final TeamType team;
    public final boolean hasWheelContact;
    public final boolean hasSecondJump;
    public final boolean hasUsedFirstJump;

    public final boolean isBot;

    public ExtendedCarData(
            final PlayerInfo playerInfo,
            final int playerIndex,
            final float elapsedSeconds,
            final DataPacket partiallyLoadedInput) {
        super(playerInfo, elapsedSeconds);
        this.playerIndex = playerIndex;
        this.isBot = playerInfo.isBot();
        this.team = playerInfo.team() == 0 ? TeamType.BLUE : TeamType.ORANGE;

        this.acceleration = computeAcceleration(playerIndex, partiallyLoadedInput);
        this.angularAcceleration = computeAngularAcceleration(playerIndex, partiallyLoadedInput);

        this.hasWheelContact = playerInfo.hasWheelContact();
        this.hasUsedFirstJump = playerInfo.jumped();
        this.hasSecondJump = canStillSecondJump(playerInfo, partiallyLoadedInput);
        this.isSupersonic = playerInfo.isSupersonic();
    }

    private Vector3 computeAcceleration(int playerIndex, DataPacket partiallyLoadedInput) {
        final Vector3 acceleration;
        if(partiallyLoadedInput.pastInputs.size() > 0) {
            acceleration = velocity.minus(partiallyLoadedInput.pastInputs.get(partiallyLoadedInput.pastInputs.size()-1).allCars.get(playerIndex).velocity)
                    .scaled(Constants.BOT_REFRESH_RATE);
        }
        else {
            acceleration = new Vector3();
        }
        return acceleration;
    }

    private Vector3 computeAngularAcceleration(int playerIndex, DataPacket partiallyLoadedInput) {
        final Vector3 angularAcceleration;
        if(partiallyLoadedInput.pastInputs.size() > 0) {
            angularAcceleration = angularVelocity.minus(partiallyLoadedInput.pastInputs.get(partiallyLoadedInput.pastInputs.size()-1).allCars.get(playerIndex).angularVelocity)
                    .scaled(Constants.BOT_REFRESH_RATE);
        }
        else {
            angularAcceleration = new Vector3();
        }
        return angularAcceleration;
    }

    private boolean canStillSecondJump(final PlayerInfo playerInfo, final DataPacket input) {
        if(playerInfo.doubleJumped()) return false; // you can't jump again if you jumped already
        if(!playerInfo.jumped() && !playerInfo.hasWheelContact()) return true;  // fell off the celling, got bumped, etc.
        if(playerInfo.jumped() && !playerInfo.hasWheelContact()) {
            final int amountOfFramesSinceFirstJump = findAmountOfFramesSinceFirstJump(input);
            return amountOfFramesSinceFirstJump < Constants.BOT_REFRESH_RATE * 1.25 + findSecondJumpTimeBonus(amountOfFramesSinceFirstJump, input);
        }
        return true;
    }

    private int findAmountOfFramesSinceFirstJump(final DataPacket input) {
        int amountOfFramesSinceFirstJump = 0;
        final List<ExtendedCarData> pastThis = input.pastInputs.stream()
                .map(input1 -> input1.allCars.get(playerIndex))
                .collect(Collectors.toList());

        for(int i = 0; i < pastThis.size(); i++) {
            if(!pastThis.get(pastThis.size()-1-i).hasUsedFirstJump) {
                amountOfFramesSinceFirstJump = i+1;
                break;
            }
        }

        return amountOfFramesSinceFirstJump;
    }

    private double findSecondJumpTimeBonus(final int amountOfFramesSinceFirstJump, final DataPacket input) {
        int amountOfFramesOfHoldingJump = 0;
        final List<ControlsOutput> pastOutputs = input.pastOutputs.stream()
                .map(outputList -> outputList.get(playerIndex))
                .collect(Collectors.toList());

        for(int i = 0; i < amountOfFramesSinceFirstJump-1; i++) {
            if(!pastOutputs.get(pastOutputs.size()-amountOfFramesSinceFirstJump+i).isJumping) {
                amountOfFramesOfHoldingJump = i;
                break;
            }
        }

        return Math.min(amountOfFramesOfHoldingJump * Constants.BOT_REFRESH_RATE, 0.2);
    }
}
