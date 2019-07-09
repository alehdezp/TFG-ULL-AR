public double calculateCone(double dist) {
    // Si es una instalacion "cercana" del dispositivo
    if (dist <= NEAR_VALUE) {
        // Se calcula el valor del coneValue restandole al valor maximo las distancia a la instalacion por la constante SCALE_CONE_NEAR que permite que esta se escale gradualmente.
        return MAX_CONE_GRADS_NEAR - dist * SCALE_CONE_NEAR;
    }else { // Si es "lejana"
        // Se calcula el valor del coneValue restandole al valor maximo las distancia a la instalacion por la constante SCALE_CONE_FAR que permite que esta se escale gradualmente en instalaciones lejanas.
        double auxCone = MAX_CONE_GRADS_FAR - dist * SCALE_CONE_FAR;
        if (auxCone < MIN_CONE_GRADS) {
            return MIN_CONE_GRADS;
        } else {
            return auxCone;
        }
    }
}