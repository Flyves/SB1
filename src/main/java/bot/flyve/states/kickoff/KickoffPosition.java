package bot.flyve.states.kickoff;

import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.team.TeamType;

import java.util.List;

public enum KickoffPosition {
    FAR_LEFT,
    MIDDLE_LEFT,
    MIDDLE,
    MIDDLE_RIGHT,
    FAR_RIGHT;

    public static KickoffPosition findPosition(final ExtendedCarData carData) {
        if(carData.team == TeamType.BLUE) {
            if (carData.position.x > 1000) return KickoffPosition.FAR_RIGHT;
            if(carData.position.x < -1000) return KickoffPosition.FAR_LEFT;
            if(carData.position.x > 50) return KickoffPosition.MIDDLE_RIGHT;
            if(carData.position.x < -50) return KickoffPosition.MIDDLE_LEFT;
            return KickoffPosition.MIDDLE;
        }
        if (carData.position.x > 1000) return KickoffPosition.FAR_LEFT;
        if(carData.position.x < -1000) return KickoffPosition.FAR_RIGHT;
        if(carData.position.x > 50) return KickoffPosition.MIDDLE_LEFT;
        if(carData.position.x < -50) return KickoffPosition.MIDDLE_RIGHT;
        return KickoffPosition.MIDDLE;
    }

    public static boolean hasToGoToKickoff(final DataPacket input) {
        final KickoffPosition kickoffPosition = KickoffPosition.findPosition(input.car);
        if(input.teammates.size() == 0) return true;
        else if(kickoffPosition == KickoffPosition.FAR_LEFT) return true;
        else if(input.teammates.stream()
                .map(KickoffPosition::findPosition)
                .noneMatch(kickoffPosition1 -> kickoffPosition1.equals(KickoffPosition.FAR_LEFT))
                && kickoffPosition == KickoffPosition.FAR_RIGHT) return true;
        else if(input.carsFromSameTeam.stream()
                .map(KickoffPosition::findPosition)
                .noneMatch(kickoffPosition1 -> kickoffPosition1.equals(KickoffPosition.FAR_LEFT)
                        || kickoffPosition1.equals(KickoffPosition.FAR_RIGHT))
                && kickoffPosition == KickoffPosition.MIDDLE_LEFT) return true;
        return input.carsFromSameTeam.stream()
                    .map(KickoffPosition::findPosition)
                    .noneMatch(kickoffPosition1 -> kickoffPosition1.equals(KickoffPosition.FAR_LEFT)
                            || kickoffPosition1.equals(KickoffPosition.FAR_RIGHT)
                            || kickoffPosition1.equals(KickoffPosition.MIDDLE_LEFT))
                    && kickoffPosition == KickoffPosition.MIDDLE_RIGHT;
    }

    public static boolean kickoffFinished(DataPacket input) {
        return input.ball.velocity.magnitudeSquared() > 1 || input.ball.position.flatten().magnitudeSquared() > 1;
    }
}
