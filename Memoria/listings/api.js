var express = require('express');
var router = new express.Router();
// Modelo que maneja la peticion a la base de datos
var ullSites = require('../models/ullSites');
// Se selecciona los metodos que puede responder 
// Al ser una aplicacion sencilla solo se necesitan manejar peticiones get
ullSites.methods(['get']);
// Se indica al router la url que gestionara las peticiones
ullSites.register(router, '/ull-sites');
module.exports = router; //Exportamos este modulo