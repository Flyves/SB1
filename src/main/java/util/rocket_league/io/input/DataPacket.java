package util.rocket_league.io.input;

import rlbot.flat.GameTickPacket;
import util.data_structure.bvh.Bvh;
import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.dynamic_objects.boost.BoostPadManager;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.rocket_league.io.output.OutputFinder;
import util.rocket_league.playfield.Standard;
import util.rocket_league.team.TeamType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DataPacket {
    public static final Bvh BVH_MAP = new Bvh(Standard.FILE_LOCATION);
    public final Vector3 gravityVector;
    public final int botIndex;
    public final List<ExtendedCarData> allCars;
    public final List<ExtendedCarData> humanCars;
    public final List<ExtendedCarData> teammates;
    public final List<ExtendedCarData> carsFromSameTeam;
    public final List<ExtendedCarData> opponents;
    public final ExtendedCarData car;
    public final TeamType team;
    public final BallData ball;
    public final List<DataPacket> pastInputs;
    public final List<List<ControlsOutput>> pastOutputs;

    public static final int PAST_INPUTS_MAX_SIZE = (int)(Constants.BOT_REFRESH_RATE * 5);
    public static final int PAST_OUTPUTS_MAX_SIZE = (int)(Constants.BOT_REFRESH_RATE * 5);

    private static final AtomicBoolean dataLoaded = new AtomicBoolean(false);
    private static final AtomicInteger botCountForDataLoading = new AtomicInteger(0);

    public DataPacket(DataPacketParameters dataPacketParameters) {
        // past inputs
        this.pastInputs = dataPacketParameters.pastInputs;

        // past outputs
        this.pastOutputs = dataPacketParameters.pastOutputs;

        this.gravityVector = new Vector3(0, 0, dataPacketParameters.request.gameInfo().worldGravityZ());
        this.botIndex = dataPacketParameters.playerIndex;
        this.allCars = new ArrayList<>();
        this.teammates = new ArrayList<>();
        this.carsFromSameTeam = new ArrayList<>();
        this.opponents = new ArrayList<>();
        this.humanCars = new ArrayList<>();
        loadCars(dataPacketParameters.request);
        this.car = allCars.get(botIndex);
        this.team = this.car.team;
        teammates.addAll(allCars.stream()
                .filter(carData -> carData.team == this.car.team)
                .filter(carData -> carData.playerIndex != this.car.playerIndex)
                .collect(Collectors.toList()));
        carsFromSameTeam.addAll(allCars.stream()
                .filter(carData -> carData.team == this.car.team)
                .collect(Collectors.toList()));
        opponents.addAll(allCars.stream()
                .filter(carData -> carData.team != this.car.team)
                .collect(Collectors.toList()));
        this.ball = new BallData(dataPacketParameters.request.ball());
        handleDataLoading(dataPacketParameters.request);

        // compute new past input
        pastInputs.add(this);
        if(pastInputs.size() > PAST_INPUTS_MAX_SIZE) pastInputs.remove(0);

        // compute new past output
        pastOutputs.add(findOutputListOfAllCars(this));
        if(pastOutputs.size() > PAST_OUTPUTS_MAX_SIZE) pastOutputs.remove(0);
    }

    private void loadCars(GameTickPacket request) {
        for(int i = 0; i < request.playersLength(); i++) {
            final rlbot.flat.PlayerInfo playerInfo = request.players(i);
            final float elapsedSeconds = request.gameInfo().secondsElapsed();
            allCars.add(new ExtendedCarData(playerInfo, i, elapsedSeconds, this));
        }
        humanCars.addAll(allCars.stream()
                .filter(carData -> !carData.isBot)
                .collect(Collectors.toList()));
    }

    private void handleDataLoading(final GameTickPacket request) {
        synchronized(BoostPadManager.boostPads) {
            synchronized(BoostPadManager.boostPadGraphWrapper) {
                synchronized(BoostPadManager.bigBoosts) {
                    synchronized(BoostPadManager.smallBoosts) {
                        loadData(request);
                    }
                }
            }
        }
    }

    private void loadData(GameTickPacket request) {
        BoostPadManager.loadGameTickPacket(request);
    }

    private List<ControlsOutput> findOutputListOfAllCars(final DataPacket input) {
        return input.allCars.stream()
                .map(car -> OutputFinder.findOutputFromCarPhysics(car, this))
                .collect(Collectors.toList());
    }
}
