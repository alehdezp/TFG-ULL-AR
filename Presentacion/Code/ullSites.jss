// Se declara el variable restful para manejar las peticiones
var restful = require('node-restful');
 // Se usa mongoose para conectarse a la BD
var mongoose = restful.mongoose;
// Estructura de las instalaciones de la ull contenidos en la base de datos
var ullSitesSchema = new mongoose.Schema({
    id: String,
    name: String,
    position: { 
        lat: String,
        long: String
    },
    desc: String,
    imageLink: String,
    canFind: [{
        id: String,
        link: String
    }]
})
// Se devuelve el modelo para poder utilizarlo en otros ficheros
// Este modelo se conectara con coleccion de la base de datos "ull_sites"
module.exports = restful.model('ull_sites', ullSitesSchema);

