var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');

var app = express();
app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());

api = require('./routes/api');

//Realizamos la conexion con la base de datos con la url correctamente
//guardada en PROD_MONGODB
mongoose.connect(process.env.PROD_MONGODB, function (error) {
      if (error) console.error(error);
      else console.log('mongo connected');
  });

app.get('/', function (req, res) { //Ruta inicial del servidor
  res.send('ULL-Navigation server');//Respuesta por defecto
})

//Dejamos que el archivo /routes/api.js se encargue de las 
//solicitudes que recibimos de /api
app.use('/api', api);

//Ponemos el servidor a escuchar
app.listen(process.env.PORT || 3000, function(){
  console.log("Express server listening on port %d in %s mode", this.address().port, app.settings.env);
});

