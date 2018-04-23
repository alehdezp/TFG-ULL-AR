public class ScanFragment extends Fragment implements BeaconConsumer {

    private BeaconManager beaconManager;
    protected int functionality;
    private static final Region myregion = new Region("All", null,null,null);
    //...
    @Override
    public void onBeaconServiceConnect() {
        //...
        beaconManager.setRangeNotifier(new RangeNotifier() {

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                String message = "Comprobando regiones";
                if (region.getUniqueId().equals("All")) {
                    if (beacons != null && beacons.iterator().hasNext()) {
                        //...
                        else if(functionality==5){
                            if (beacons.size() >= 3) {
                                int count = 0;
                                String[] ids = new String[beacons.size()];
                                double[] dist = new double[beacons.size()];
                                for (Beacon selBeacon : beacons) {
                                    ids[count] = selBeacon.getBluetoothAddress();
                                    dist[count] = selBeacon.getDistance();
                                    count++;
                                }
                                try {
                                    checkPerimeter(beacons,ids, dist, BeaconAttInfo.getAreas(), staticVars.SCALEETSII, BeaconAttInfo.getImage());
                                } catch (Exception e) {
                                }
                            }
                        }
                        //...
                    }
                }
            }
        });
    }
    //...
}
    