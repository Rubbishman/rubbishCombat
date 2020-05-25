package com.rubbishman.rubbishcombat;

import com.google.common.collect.ImmutableList;
import com.rubbishman.rubbishRedux.external.RubbishContainer;
import com.rubbishman.rubbishRedux.external.operational.action.multistageAction.Stage.Stage;
import com.rubbishman.rubbishRedux.external.setup.RubbishContainerCreator;
import com.rubbishman.rubbishRedux.external.setup.RubbishContainerOptions;
import com.rubbishman.rubbishRedux.external.setup_extra.TickSystem;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.ActionTrack;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.ActionTrackListener;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.stage.StageWrap;
import com.rubbishman.rubbishRedux.internal.timekeeper.TimeKeeper;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.reducer.CombatReducer;
import com.rubbishman.rubbishcombat.stages.ArmorStage;
import com.rubbishman.rubbishcombat.stages.DirectDamageStage;
import com.rubbishman.rubbishcombat.stages.DodgeStage;
import com.rubbishman.rubbishcombat.ticksystem.ArmorRegenSystem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RubbishCombat {
    public RubbishContainer rubbish;
    private ScheduledExecutorService executor;

    public Stage armor;
    public Stage dodge;
    public Stage direct;

    public RubbishCombat() {
        RubbishContainerOptions options = setupOptions();

        rubbish = RubbishContainerCreator.getRubbishContainer(options);

         executor = Executors.newSingleThreadScheduledExecutor();
    }

    public RubbishCombat(Runnable afterTick, ActionTrackListener listener) {
        RubbishContainerOptions options = setupOptions();

        options.addListener(direct, listener); // Hard coded a bit ... :S
        options.registerTickSystem(new TickSystem() {
            @Override
            public void beforeDispatchStarted(ActionTrack actionQueue, Long nowTime) {

            }

            @Override
            public void afterDispatchFinished() {
                afterTick.run();
            }
        });

        rubbish = RubbishContainerCreator.getRubbishContainer(options);

        executor = Executors.newSingleThreadScheduledExecutor();
    }

    public RubbishCombat(TimeKeeper timeKeeper) {
        RubbishContainerOptions options = setupOptions();

        options.setTimeKeeper(timeKeeper);

        rubbish = RubbishContainerCreator.getRubbishContainer(options);
    }

    private RubbishContainerOptions setupOptions() {
        RubbishContainerOptions options = new RubbishContainerOptions();

        try {
            armor = options.createStage("armor");
            dodge = options.createStage("dodge");
            direct = options.createStage("direct");

            options.setStageProcessor(Damage.class,
                    ImmutableList.of(
                            new StageWrap(armor, new ArmorStage()),
                            new StageWrap(dodge, new DodgeStage()),
                            new StageWrap(direct, new DirectDamageStage())
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        options.registerTickSystem(new ArmorRegenSystem());

        options
                .setReducer(new CombatReducer());

        return options;
    }

     public void startTimer() {
         Runnable runner = new Runnable() {
             @Override
             public void run() {
                 rubbish.performActions();
             }
         };

         executor.scheduleAtFixedRate(runner, 0, 30, TimeUnit.MILLISECONDS);
     }

}
