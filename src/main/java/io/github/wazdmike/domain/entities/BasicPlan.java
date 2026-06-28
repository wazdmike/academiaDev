package io.github.wazdmike.domain.entities;

public class BasicPlan implements SubscriptionPlan{
    @Override
    public int getEnrollmentLimit(){
        return 3;
    }
}
