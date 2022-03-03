package lab1;

import javafx.animation.AnimationTimer;

public class FixedStepAnimationTicker extends AnimationTimer {
    private static final long NANOS_PER_SECOND = 1000000000;
    private final long stepDuration;
    private final Runnable performStep;

    private long lastStep = -1;

    public FixedStepAnimationTicker(long stepDuration, Runnable performStep) {
        this.stepDuration = stepDuration;
        this.performStep = performStep;
    }

    public static FixedStepAnimationTicker ofStepsPerSecond(int steps, Runnable performStep) { // how to get FRAMES PER SECOND hah
        return new FixedStepAnimationTicker(NANOS_PER_SECOND / steps, performStep);
    }

    @Override
    public void handle(long now) { // makes timer for our loop
        if (lastStep == -1) { //first step
            performStep(now);
        }
        else if (now - lastStep >= stepDuration) { // if system time - lastStep is greater then step just do the step
            performStep(now);
        }
    }

    private void performStep(long now) {
        lastStep = now;
        performStep.run(); // Runnable object, it this project this is Lambda function
    }
}
