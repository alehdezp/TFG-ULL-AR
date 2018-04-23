 public Point calculatePosition(double[][] positions, double[] distances ) {
        //Call trilateration algorithm
        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(positions, distances);
        //Use non linear least squares solver using levenberg-marquardt method.
        NonLinearLeastSquaresSolver nlSolver = new NonLinearLeastSquaresSolver(trilaterationFunction, new org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer());

        LeastSquaresOptimizer.Optimum optimum = nlSolver.solve();
        // The result is the center of the position of the User, given in X Y coordinates of a Point.
        double[] centroid = optimum.getPoint().toArray();
        return new Point(centroid[0],centroid[1]);
}