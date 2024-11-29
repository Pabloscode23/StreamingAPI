# StreamingAPI


Universidad Cenfotec

Curso: Programación con Patrones
Profesor: Valentín Secades Méndez

## Grupo 1

Jose Pablo Navarro Rodriguez

Isabella Nassar Míguez

Cristopher Leiton Duran

Fabiola Zuñiga Vargas

Diego Alonso Selva Arrieta

Este proyecto forma parte del curso de Programacion con Patrones.

---
## Dependencias o necesidades

Registrarse en WatchMode para obtener el APIKey:
https://api.watchmode.com/


Se requiere la implementacion de org.json. En el siguiente link se puede encontrar el .jar necesario:
https://mavenlibs.com/jar/file/org.json/json

## Actualizaciones respecto al Caso 2
### Se emplean los proveedores: 
1, Netflix, https://www.netflix.com

2, Disney +, https://www.disneyplus.com/en-cr

3, HBO Max, https://www.max.com/cr/es

4, Amazon Prime, https://www.primevideo.com/offers/nonprimehomepage/ref=dv_web_force_root

### Se emplean los planes:
1,Plan Básico,5.0,1

2,Plan Premium,10.0,1

3,Plan Básico,8.0,2

4,Plan Premium,14.0,2

5,Plan Básico,10.0,3

6,Plan Premium,16.0,3

7,Plan Básico,9.0,4

8,Plan Premium,15.0,4

### Los usuarios quemados son:
1,Katheryn Hudson,khudson@gmail.com,1234

2,Admin,admin,admin2

### Ubicacion de los patrones empleados

- Factory Method: carpeta factory
- Abstract Method: WatchModeAdapterFactory y WatchModeServiceFactory

- Prototype: SearchResult y se implementa en WatchModeService
- Singleton: StreamingServiceManager y carpeta database
- Adapter: StreamingServiceAdapter y WatchModeAdapter
- Composite: Carpeta menu, en MenuUsuario se emplea el patron directamente
- decorator: Carpeta decorator
- Facade: WatchModeService
- Proxy: carpeta proxy e implementacion en iniciarSesion del Main
- Strategy: StreamingService
- Observer: carpeta observer
- State: carpeta state

