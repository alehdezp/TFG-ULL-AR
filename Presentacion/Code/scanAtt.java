public class ScanFragment extends Fragment implements BeaconConsumer {
  public void onBeaconServiceConnect() {
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        if (region.getUniqueId().equals("All")) {
          if (beacons != null && beacons.iterator().hasNext()) {
            //...
            else if(functionality==5){
              if (beacons.size() >= 3) {
                for (Beacon selBeacon : beacons) {
                    ids[count] = selBeacon.getBluetoothAddress();
                    dist[count] = selBeacon.getDistance();
                    count++;
                }
                checkPerimeter(beacons,ids, dist, BeaconAttInfo.getAreas(), staticVars.SCALEETSII, BeaconAttInfo.getImage());
    