package io.github.wazdmike.domain.entities;

public class Student extends User{
    private SubscriptionPlan subscriptionPlan;

    public Student(String name, String email, SubscriptionPlan subscriptionPlan){
        super(name, email);
        this.subscriptionPlan = subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public boolean canEnroll(int currentActiveEnrollments) {
        return currentActiveEnrollments < subscriptionPlan.getEnrollmentLimit();
    }
}
