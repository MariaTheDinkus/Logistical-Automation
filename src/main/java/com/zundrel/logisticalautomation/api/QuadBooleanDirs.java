package com.zundrel.logisticalautomation.api;

public enum QuadBooleanDirs {
    ONE(0, new QuadBoolean(true, true, true, true)),
    TWO(1, new QuadBoolean(true, true, true, false)),
    THREE(2, new QuadBoolean(true, true, false, true)),
    FOUR(3, new QuadBoolean(true, true, false, false)),
    FIVE(4, new QuadBoolean(true, false, true, true)),
    SIX(5, new QuadBoolean(true, false, true, false)),
    SEVEN(6, new QuadBoolean(true, false, false, true)),
    EIGHT(7, new QuadBoolean(true, false, false, false)),
    NINE(8, new QuadBoolean(false, true, true, true)),
    TEN(9, new QuadBoolean(false, true, true, false)),
    ELEVEN(10, new QuadBoolean(false, true, false, true)),
    TWELVE(11, new QuadBoolean(false, true, false, false)),
    THIRTEEN(12, new QuadBoolean(false, false, true, true)),
    FOURTEEN(13, new QuadBoolean(false, false, true, false)),
    FIFTEEN(14, new QuadBoolean(false, false, false, true)),
    SIXTEEN(15, new QuadBoolean(false, false, false, false));

    private int meta;
    private QuadBoolean quadBoolean;

    QuadBooleanDirs(int meta, QuadBoolean quadBoolean) {
        this.meta = meta;
        this.quadBoolean = quadBoolean;
    }

    public int getMeta() {
        return meta;
    }

    public QuadBoolean getQuadBoolean() {
        return quadBoolean;
    }

    public static QuadBoolean get(int index) {
        for (QuadBooleanDirs quadBooleanDirs : values()) {
            if (quadBooleanDirs.getMeta() == index) {
                return quadBooleanDirs.getQuadBoolean();
            }
        }
        return null;
    }

    public static QuadBoolean findQuadBoolean(boolean one, boolean two, boolean three, boolean four) {
        QuadBoolean quadBoolean = new QuadBoolean(one, two, three, four);

        for (QuadBooleanDirs quadBooleanDirs : values()) {
            if (quadBooleanDirs.quadBoolean.compareBooleans(quadBoolean)) {
                return quadBooleanDirs.quadBoolean;
            }
        }
        return null;
    }

    public static QuadBoolean findQuadBoolean(QuadBoolean quadBoolean) {
        for (QuadBooleanDirs quadBooleanDirs : values()) {
            if (quadBooleanDirs.quadBoolean.compareBooleans(quadBoolean)) {
                return quadBooleanDirs.quadBoolean;
            }
        }
        return null;
    }

    public static int findQuadMeta(boolean one, boolean two, boolean three, boolean four) {
        QuadBoolean quadBoolean = new QuadBoolean(one, two, three, four);

        for (QuadBooleanDirs quadBooleanDirs : values()) {
            if (quadBooleanDirs.getQuadBoolean().compareBooleans(quadBoolean)) {
                return quadBooleanDirs.getMeta();
            }
        }
        return 0;
    }

    public static int findQuadMeta(QuadBoolean quadBoolean) {
        for (QuadBooleanDirs quadBooleanDirs : values()) {
            if (quadBooleanDirs.getQuadBoolean().compareBooleans(quadBoolean)) {
                return quadBooleanDirs.getMeta();
            }
        }
        return 0;
    }
}
