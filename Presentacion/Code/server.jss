//Librerias
var express = require('express'); // Servidor
var mongoose = require('mongoose'); // Para conectar con MongoDB
// Metodos para manejar el contenido de las respuestas a la peticiones
var bodyParser = require('body-parser');
//Configuracion del servidor
var server = express(); // Se instancia el servidor
server.use(bodyParser.urlencoded({extended: false})); //Formato Querystring
server.use(bodyParser.json());  //Manejo de peticiones JSON
// Se usa el fichero que se encuentra en ./routes/api.js
// Se realiza la conexion con la base de datos con la url correctamente
// guardada en PROD_MONGODB
mongoose.connect(process.env.PROD_MONGODB, function (error) { ... });
server.get('/', function (req, res) { ... }) // Ruta raiz del servidor
// Se le indica al servidor que el archivo /routes/api.js se encargue de las 
// solicitudes que se reciben la url /api
api = require('./routes/api');
server.use('/api', api);
// Se ejecuta el servidor en el puerto de produccion o en el 3000
server.listen(process.env.PORT || 3000, function() { ... });
