// Metodo que calcula el angulo que se obtiene al transformar el rectangulo formado por este punto y el punto v2 a coordenadas polares
public double getAngleRad(Vector2D v2) {
    double dx = v2.getX() - getX(); // Se calculan las distancias en el eje x e y
    double dy = v2.getY() - getY(); // se obtiene el rectangulo formado por estos dos puntos
    double radian = Math.atan2(dy, dx); // Se realiza la arcotangente para calcular el angulo del rectangulo formado
    return radian; // Se devuelve el resultado
}
