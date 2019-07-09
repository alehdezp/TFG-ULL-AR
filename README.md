# TFG-ULL-AR

Trabajo de fin de grado del alumno Alejandro Hernández Padrón de la Universidad de La Laguna.

Desarrollo de una aplicación en Java a mediante el IDE de Android Estudio.

## Guía de uso de estas tecnologías en relación con el proyecto ULL-AR

### Descargar repositorio de la aplicación

Para descargar el repositorio de ULL-AR se necesitará tener instalado GitHub. Con GitHub instalado, se ejecuta el siguiente comando en consola para descargar el repositorio:


    $ git clone https://github.com/alehdezp/TFG-ULL-AR 


En caso de que no se disponga de GitHub se puede descargar directamente desde el repositorio, seleccionando el bóton "Clone or download" y luego se selecciona "Download as ZIP". Tras esto se descargará un archivo comprimido con todo el repositorio de ULL-AR.


### Compilar el proyecto en Android Studio

Para compilar el proyecto es necesario tener instalado el IDE de Android Studio en el ordenador que se desee compilar el proyecto.

Una vez iniciado Android Studio, se indica que se quiere abrir un proyecto y se busca en la carpeta en la que se encuentra el repositorio de ULL-AR y se abre el proyecto que se encuentra en la ruta "app/ULL\_Navigation". 

La primera vez que se abre el proyecto, en el caso de que no se haya hecho automáticamente, se ha de seleccionar en la barra superior la opción de "Build" y luego "Make Project". 
Esto creára y configurará el proyecto para que se pueda empezar a trabajar con él.

Para poder instalar la aplicación que genera el proyecto en un dispositivo Android hay dos opciones. 
La primera es generar un archivo ".apk". Para generar este archivo se ha de seleccionar en la barra superior la opción "Build", luego "Build Bundle(s) / APK(s)" y por último "Build APK". 
Esto generará un fichero app-debug.apk en la ruta "ULL_Navigation/app/build/outputs/apk/debug", este fichero hay que colocarlo dentro del dispositivo Android en el que se quiera instalar la aplicación y se ejecuta.

La seguna opción es ejecutar e instalar directamente la aplicación desde Android Studio. 
Para ello hay que tener activado en el dispositivo Android la opción de depuración por USB. 
Esta opción solo estará disponible cuando se tenga activado el modo desarrollador en el dispositivo Android. 
En función de la marca y modelo del dispositivo la forma en la que se activa este modo varía. 
Con la opción activada solo hay que conectar el dispositivo al ordenador y seleccionar en el móvil el modo de "Transeferencia de archivos", a continuación en Android Studio se selecciona la opción "Run" y luego "Run 'app'". 
Con estos pasos ya instalará la aplicación en el dispositivo y se solicitarán permisos en el dispositivo móvil para confiar en el desarrollador e instalar la aplicación ULL-AR. 


### Compilar la memoria del proyecto

Para compilar el proyecto en LaTeX en un máquina con Linux es necesario instalar "TeX Live" y "make". Con el siguiente comando se instalarán ambos:

    $ sudo apt install texlive-full make 

Dentro de la carpeta "Memoria/" del repositorio, se abrirá una consola y se ejecutará el siguiente comando el cual genera el archivo con la presente memoria memoria-TFG-Alejandro.pdf.

    $ make


