#  🌐Servidor Web en Java  

### Arquitectura Empresarial  
**Autora:** Emily Noreña Cardozo  
**Fecha:** 18 de agosto de 2025  

---

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

## ¿Cómo ejecutar?
Para correr el proyecto localmente, debes tener instalado:
1. Java (versión 17)
