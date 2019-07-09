// Se accede a los sensores del dispositivo
mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); 
// Se accede al calculo de la matriz de rotacion que proporciona el valor de la brujula magnetica del dispositivo
Sensor compass = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
// Se escuchan a los cambios del sensor para actualizar los calculos
mSensorManager.registerListener(this, compass, SensorManager.SENSOR_DELAY_NORMAL);   