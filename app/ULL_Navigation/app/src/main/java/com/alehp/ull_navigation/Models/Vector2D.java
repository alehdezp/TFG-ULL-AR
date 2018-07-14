package com.alehp.ull_navigation.Models;

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

//    public double getAngleGrad(Vector2D v2) {
//        double dx = v2.getX() - getX();
//        double dy = v2.getY() - getY();
//
//        double radian = Math.atan2(dy, dx);
//        double degrees = Math.toDegrees(radian);
//
//        return degrees;
//    }


    public double getAngleRad(Vector2D v2) {
        double dx = v2.getX() - getX();
        double dy = v2.getY() - getY();

        double radian = Math.atan2(dy, dx);


        return radian;
    }




    public double getDistance(Vector2D v2) {


        double x1 = getX() * 10000000/90;
        double x2 = v2.getX() * 10000000/90;
        double y1 = getY() * 111111.1;
        double y2 = v2.getY() * 111111.1;

        double dx = x1 - x2;
        double dy = y1 - y2;
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
