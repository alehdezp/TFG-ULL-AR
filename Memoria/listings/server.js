var express = require('express'); // Servidor
var mongoose = require('mongoose');// Para conectar con BD
var bodyParser = require('body-parser');// Manejar peticiones JSON

var server = express(); // Se instancia el servidor
server.use(bodyParser.urlencoded({extended: false}));
server.use(bodyParser.json());

// Se usa el fichero que se encuentra e ./routes/api.js
api = require('./routes/api');

// Se realiza la conexion con la base de datos con la url correctamente
// guardada en PROD_MONGODB
mongoose.connect(process.env.PROD_MONGODB, function (error) {
      if (error) console.error(error);
      else console.log('mongo connected');
  });

server.get('/', function (req, res) { // Ruta raiz del servidor
  res.send('ULL-AR server');// Respuesta por defecto
})

// Se le dice al servidor que el archivo /routes/api.js se encargue de las 
// solicitudes que se reciben de /api
server.use('/api', api);

// Se pone el servidor a escuchar
server.listen(process.env.PORT || 3000, function(){
  console.log("Express server listening on port %d in %s mode", this.address().port, server.settings.env);
});

