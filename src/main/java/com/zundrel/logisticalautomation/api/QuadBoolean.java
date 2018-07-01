package com.zundrel.logisticalautomation.api;

public class QuadBoolean {
    private boolean one;
    private boolean two;
    private boolean three;
    private boolean four;

    public QuadBoolean(boolean one, boolean two, boolean three, boolean four) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
    }

    public boolean getOne() {
        return one;
    }

    public boolean getTwo() {
        return two;
    }

    public boolean getThree() {
        return three;
    }

    public boolean getFour() {
        return four;
    }

    public boolean compareBooleans(boolean one, boolean two, boolean three, boolean four) {
        return this.one == one && this.two == two && this.three == three && this.four == four;
    }

    public boolean compareBooleans(QuadBoolean quadBoolean) {
        return compareBooleans(quadBoolean.one, quadBoolean.two, quadBoolean.three, quadBoolean.four);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QuadBoolean) {
            QuadBoolean quadBoolean = (QuadBoolean) obj;

            compareBooleans(quadBoolean);
        }
        return false;
    }
}
