package com.kaisquare.vca.models;

/**
 * Author:  Aye Maung
 *
 */
public class NormalizedPoint {
    private Double x;
    private Double y;

    public NormalizedPoint(Double x, Double y) throws Exception {
        if (x < 0 || x > 1) {
            throw new Exception("Value must be within [0,1] range");
        }

        if (y < 0 || y > 1) {
            throw new Exception("Value must be within [0,1] range");
        }

        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

}

