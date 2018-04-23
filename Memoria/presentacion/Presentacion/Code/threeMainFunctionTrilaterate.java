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
            ImageView imageView = (ImageView) getActivity().findViewById(R.id.location);
            imageView.setImageBitmap(bitmap);
        }
    });
}
