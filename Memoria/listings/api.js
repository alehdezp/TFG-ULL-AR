var express = require('express');
var router = new express.Router();

//Modelo que maneja la peticion a la base de datos
var ullSites = require('../models/ullSites');

//Seleccionamos los metodos que puede responder 
//Al ser una aplicacion sencilla solo necesitamos manejar peticiones get
ullSites.methods(['get']);
//Indicamos al router la url que gestionara las peticiones
ullSites.register(router, '/ull-sites');


module.exports = router;
