#  🌐Servidor Web en Java  

### Arquitectura Empresarial  
**Autora:** Emily Noreña Cardozo  
**Fecha:** 18 de agosto de 2025  


## Taller  
**Diseño y estructuración de aplicaciones distribuidas en Internet**  

---

## Descripción del proyecto  
Este proyecto implementa un servidor web en Java, utilizando únicamente librerías. El servidor cumple con las siguientes características: 

- Atiende múltiples solicitudes seguidas de forma no concurrente.  
- Lee y sirve archivos del disco local (HTML, CSS, JS e IMG).
- Comunicación asíncrona con servicios REST (métodos GET y POST).  

---

## Scaffolding
<pre> 
├───httpserver
│       ApiHandler.java
│       FileHandler.java
│       RequestHandler.java
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
En tu navegador busca http://localhost:35000/index.html


<img width="1907" height="1006" alt="captura 1" src="https://github.com/user-attachments/assets/2e527148-1f25-4f6b-aafc-8a12bbaa3eae" />

