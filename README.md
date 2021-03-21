# MARVEL WIKI APP
### Descripción
Esta aplicación permite navegar por un listado de los personajes de Marvel Comic y acceder al detalle para ver su descripción. Para obtener la información se ha hecho uso de [Marvel API](https://developer.marvel.com/docs).

El proyecto esta formado por dos modulos. El modulo "app" el cual contiene las vistas y recursos de la aplicación y el modulo "marvelapi" el cual hace de capa de conexión con la api.

La aplicación tiene una arquitectura MVVM y hace uso de observables para reaccionar a los eventos que se producen de las llamadas http.

### Uso de cliente api

El modulo marvelapi nos permite abstraer la capa de conexión del resto de la aplicación, de este modo si quisieramos podriamos reutilizar los endpoint implementados y usarlos en otra aplicación exportando el modulo.

Además, permite implementar nuevos endpoint de forma sencilla en tres pasos.

1. Añadir la cadena del endpoint en la clase EndpointsDefinition.kt. Ejemplo:

`const val MARVEL_CHARACTERS = "$HOST/public/characters"`

2. Añadir la definición del método en la interfaz MarvelApi.kt. Ejemplo:

`fun getCharacterList(offset:Int, limit:Int):Observable<ResponseHolder>`

3. Por último en la clase MarvelClient.kt la cual hereda de MarvelApi.kt sobrescribimos el método que hemos definido e implementamos la llamada haciendo uso de los métodos de conexión de la clase Networking.kt. Ejemplo:

`
override fun getCharacterList(offset: Int, limit: Int):Observable<ResponseHolder> {
val url = "${EndpointsDefinition.MARVEL_CHARACTERS}"
return networking.getRequest(url, null, ResponseHolder::class.java)
    }
`
### Configuración

Antes de compilar el proyecto para que funcione la conexión con el servicio es necesario añadir tus claves publicas y privadas en las constantes PUBLIC_KEY y PRIVATE_KEY que se encuentran en la clase MarvelClient.kt. Para obtener estas claves puede consultar la [documentación Marvel API](https://developer.marvel.com/documentation/getting_started).
