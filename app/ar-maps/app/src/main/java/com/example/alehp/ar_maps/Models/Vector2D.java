package com.example.alehp.ar_maps.Models;

public class Vector2D {

    private double x;



    private double y;


    public Vector2D() {
        setX(0.0);
        setY(0.0);
    }

    public Vector2D( double x1, double y1 ) {
        setX(x1);
        setY(y1);
    }

    public double getAngleGrad(Vector2D v2) {
        double dx = v2.getX() - getX();
        double dy = v2.getY() - getY();

        double radian = Math.atan2(dy, dx);
        double degrees = Math.toDegrees(radian);

        return degrees;
    }


    public double getAngleRad(Vector2D v2) {
        double dx = v2.getX() - getX();
        double dy = v2.getY() - getY();

        double radian = Math.atan2(dy, dx);

        return radian;
    }




    public double getDistance(Vector2D v2) {

        double dx = (double)(getX() - v2.getX());
        double dy = (double)(getY() - v2.getY());
        return Math.sqrt (dx * dx + dy * dy);
    }

    public void set(double x1, double y1){
        setX(x1);
        setY(y1);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString() {
        return "Vector2D(" + this.x + ", " + this.y + ")";
    }

}
