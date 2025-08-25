#  🌐Microframeworks WEB  

### Arquitectura Empresarial  
**Autora:** Emily Noreña Cardozo  
**Fecha:** 24 de agosto de 2025  


## Taller  
**Desarrollo de un Framework Web para Servicios REST y Gestión de Archivos Estáticos**  

---

## Descripción del proyecto  
Este proyecto implementa un servidor web en Java, utilizando únicamente librerías. El servidor cumple con las siguientes características: 

- Atiende múltiples solicitudes seguidas de forma no concurrente.  
- Archivos estáticos (HTML, CSS, JS, imágenes) servidos desde un directorio configurable.
- Comunicación asíncrona con servicios REST (métodos GET y POST).
- Rutas REST dinámicas mediante expresiones lambda.
- Manejo de parámetros de consulta (query params) en las solicitudes.

---

## Scaffolding
<pre> 
├───httpserver
│       ApiHandler.java
│       FileHandler.java
        Response.java
        Request.java
│       RequestHandler.java
        Route.java
        Router.java
│       WebServer.java
        
│
└───public
        img.png
        index.html
        script.js
        styles.css
</pre>

---

## ¿Cómo ejecutar?
Para correr el proyecto localmente, debes tener instalado:
1. Java (versión 17 https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html). Puedes verificar la versión ejecutando en la terminal: <pre> java -version </pre>
2. Maven (https://maven.apache.org/download.cgi). Puedes verificar la versión ejecutando en la terminal: <pre> mvn -v </pre>
3. Git (https://git-scm.com/downloads). Puedes verificar la versión ejecutando en la terminal: <pre> git --version </pre>

Posterior a esto, es necesario clonar el repositorio de la siguiente manera:
<pre> git clone https://github.com/EmilyNorena/Servidor-web-Java.git </pre>

Finalmente, sigue estos pasos:
1. Dirígete a la carpeta que con tiene el archivo pom.xml: <pre>cd httpserver</pre>
2. Construye el proyecto: <pre>mvn clean package</pre>
   La salida debe ser BUILD SUCCESS.
4. Ejecuta la aplicación: <pre> java -cp target/classes com.mycompany.httpserver.WebServer </pre>
   La consola debe mostrar el siguiente mensaje: Server started on port 35000.

---

## ¿Cómo finalizar un proceso que está utilizando el puerto 35000?
### En Windows
1. Identifica el proceso que ocupa el puerto: <pre> netstat -ano | findstr :35000 </pre>
El último número de la línea corresponde al PID del proceso.
   
2. Finaliza el proceso con el PID: <pre> taskkill /PID <PID> /F </pre>

### En Linux
1. Identifica el proceso que ocupa el puerto: <pre> lsof -i :35000 </pre>
2. kill -9 <PID>

---

## ¿Qué debes ver?
En tu navegador busca http://localhost:35000


<img width="1907" height="1006" alt="captura 1" src="https://github.com/user-attachments/assets/2e527148-1f25-4f6b-aafc-8a12bbaa3eae" />



Si buscas un recurso inexistente, verás esta página



<img width="1917" height="1008" alt="image" src="https://github.com/user-attachments/assets/e739b23c-83f0-4494-8e91-52fe42c83200" />



---

## Arquitectura
<img width="860" height="746" alt="image" src="https://github.com/user-attachments/assets/98565b94-b48e-498c-a8dc-3118c373272b" />

1. WebServer: 
   
   - Inicializa el servidor en el puerto 35000.
   - Acepta conexiones entrantes con ServerSocket.
   - Procesa una sola conexión a la vez.
   - Delega la solicitud entrante RequestHandler.
   - Permite detener el servidor de forma controlada.
  
     
2. RequestHandler: 
   
   - Recibe el Socket de un cliente y gestiona su ciclo de vida.
   - Lee y parsea la petición HTTP (método, ruta, headers, query params).
   - Determina si la solicitud es de tipo estática (archivos) o dinámica (API).
   - Genera y enviar la respuesta HTTP al cliente.
  
3. FileHandler

   - Localiza archivos en el directorio /public.
   - Protege contra ataques directory traversal.
   - Determina el Content-Type según la extensión.
   - Sirve contenido texto (HTML, CSS, JS) o binario (imágenes).
   - Maneja errores como 404 Not Found o 403 Forbidden.

4. ApiHandler

   - Procesa peticiones dirigidas a rutas /api/.
   - Enruta según el path y el método HTTP (GET, POST).
   - Lee query params.
   - Genera respuestas JSON.

---

## Diagrama de clases

<img width="1327" height="575" alt="image" src="https://github.com/user-attachments/assets/599d0b5a-7a75-45f1-ab9e-b43e88b52424" />

### Relaciones entre clases
- WebServer -> ServerSocket: La clase WebServer utiliza ServerSocket para escuchar conexiones entrantes de clientes en un puerto específico.
- WebServer -> RequestHandler: Por cada cliente que se conecta, WebServer crea un objeto RequestHandler. RequestHandler depende de WebServer para su creación, pero no forma parte permanente del WebServer.
- RequestHandler -> FileHanlder: RequestHandler utiliza FileHandler para servir archivos estáticos solicitados por el cliente.
- RequestHandler -> ApiHandler: RequestHandler utiliza ApiHandler para procesar solicitudes a endpoints de la API.
  
---

## Pruebas
<img width="1246" height="202" alt="image" src="https://github.com/user-attachments/assets/855b8839-2579-4c1b-a861-4bfbca8dd5e2" />

1. <pre>shouldLoadStaticFileHtml</pre> Verifica que el servidor retorne código 200 (OK) al solicitar un archivo HTML existente (index.html).

2. <pre>notShouldLoadStaticFileHtml</pre> Confirma que el servidor retorne 404 (No encontrado) al solicitar un archivo HTML inexistente.

3. <pre>shouldLoadStaticFileCss </pre> Comprueba que el servidor sirve correctamente archivos CSS existentes (código 200).

4. <pre> notShouldLoadStaticFileCss </pre> Valida el manejo de archivos CSS inexistentes (código 404).

5. <pre> shouldLoadStaticFileJs </pre> Testea la correcta entrega de archivos JavaScript existentes.

6. <pre> notShouldLoadStaticFileJs </pre> Verifica el comportamiento con archivos JS que no existen.

7. <pre> shouldLoadStaticImagePNG </pre> Confirma que el servidor puede servir imágenes PNG existentes.
8. <pre> shouldLoadStaticImageJPG </pre> Similar a la anterior pero para imágenes JPG/JPEG.
9. <pre> notShouldLoadStaticImagePNG </pre> Valida el código 404 para imágenes PNG inexistentes.
10. <pre> notShouldLoadStaticImageJPG </pre> Igual que la anterior pero para formato JPG.
11. <pre> shouldLoadRestGet </pre> Prueba un endpoint REST con método GET, esperando respuesta exitosa (200).
12. <pre>shouldLoadRestPost </pre> Verifica el comportamiento de un endpoint REST con método POST, esperando respuesta exitosa (200).


