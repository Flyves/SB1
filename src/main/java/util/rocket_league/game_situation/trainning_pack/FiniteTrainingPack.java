package util.rocket_league.game_situation.trainning_pack;

import util.rocket_league.game_situation.GameSituation;

public class FiniteTrainingPack extends TrainingPack {

    public FiniteTrainingPack() {
        super();
    }

    @Override
    public boolean hasNext() {
        return getNextGameSituationIndex() < getGameSituationList().size();
    }

    @Override
    public boolean hasBeenCompleted() {
        GameSituation currentGameSituation = getGameSituationList().get(getNextGameSituationIndex()-1);
        return (!hasNext()) && currentGameSituation.isGameStateElapsed();
    }
}
