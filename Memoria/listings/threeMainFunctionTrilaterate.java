    public Point calculatePosition(double[][] positions, double[] distances ) {

        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(positions, distances);
        NonLinearLeastSquaresSolver nlSolver = new NonLinearLeastSquaresSolver(trilaterationFunction, new org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer());

        LeastSquaresOptimizer.Optimum optimum = nlSolver.solve();
        // the center
        double[] centroid = optimum.getPoint().toArray();
        return new Point(centroid[0],centroid[1]);
    }

    public void drawPointInPosition(final double posX, final double posY, final double radius, final List<Area> areas, final int imageResource) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BitmapFactory myFactory= new BitmapFactory();
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inScaled = false;
                opt.inMutable = true;

                Bitmap bitmap = myFactory.decodeResource(getResources(), imageResource,opt);
                //...
                Canvas canvas = new Canvas(bitmap);
                for (Area area: areas) {
                    canvas.drawRect(new Rect(area.getLeft(),area.getTop(),area.getRight(),area.getBottom()), paintZone);
                }
                canvas.drawCircle((float) posX, (float) posY, (float) radius, paint);

                //...
                ImageView imageView = (ImageView) getActivity().findViewById(R.id.location);
                imageView.setAdjustViewBounds(true);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
