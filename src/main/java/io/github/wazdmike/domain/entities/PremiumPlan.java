package io.github.wazdmike.domain.entities;

public class PremiumPlan implements SubscriptionPlan{
    @Override
    public int getEnrollmentLimit(){
        return Integer.MAX_VALUE;
    }
}
