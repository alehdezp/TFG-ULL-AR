    private double recalculeAng(double angleRad) {
        double aux = rotateRad(angleRad); // Rota -pi/2
        aux = invertAng(aux);             // Se invierte el angulo 
        return aux;                       // Se devuelve el resultado
}   