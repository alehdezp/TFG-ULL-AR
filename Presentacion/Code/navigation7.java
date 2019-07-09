    for (int i = 0; i < destSites.size(); i++) {  
        // Se calcula la direccion, distancia y valor del cono de cada instalacion a partir de la actual ubicacion del dispositivo
        double dirToSite = recalculeAng(currentPos.getAngleRad(...);
        double distToSite = getDistanceBetween(...);
        double coneValue = calculateCone(distToSite);
        // Se comprueba si el dispositivo esta orientado hacia dentro del cono que se forma en la direccion de la instalacion
        if (isInCone(dirToSite, coneValue)) { ...
            // Se guarda esta instalacion como resultado
            result.add(destSites.get(i)); 
             // Si es la instalacion mas cercana
            if (nearSiteDist > distToSite) {
                nearSiteDist = distToSite; // Actualizamos la distancia
                id = i;  // Se guarda el indice de la instalacion mas cercana
            }
        }     
        if (id != -1) {  // Si se ha encontrado alguna instalacion
            // La instalacion mas cercana la se guarda en la primera posicion
            result.add(0, destSites.get(id)); 
            return result; // Se devuelven las instalaciones
        }
    }