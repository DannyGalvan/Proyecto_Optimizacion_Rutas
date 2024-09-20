# Proyecto de base para realizar un api con springboot

Este proyecto demuestra cómo crear una API utilizando SpringBoot, implementando varias practicas y teconologicas modernas.

1. Autenticacion JWT con WebSecurity
2. Inyeccion de dependencias utilizando las notaciones @Services
3. Conexion a PostgreSql con JPA e Hibernate
4. Auto mapedo de entidades con mapstruct
5. Uso de Base de datos  PostgreSql Code First aplicando cambios con Hibernate.ddl-auto
6. BCryptPasswordEncoder para el encriptado de las contraseñas (es parte de spring security)
7. Uso de @Configuration y @Bean para crear el usuario y el rol iniciales

# Pasos para ejecutar el proyecto

1. Clonar el repositorio
2. Restaurar los paquetes del proyecto
3. Crear el application-dev.properties en la carpeta /src/main/resources con base a las propiedades que pide el application-example.properties
4. En el archivo /src/main/java/com/scaffolding/initialize/config/InitialUserConfig se puede configurar los datos del usuario y rol iniciales.
4. Compilar el proyecto para generar las clases de mapstruct
5. Ejecutar el proyecto

# optimizacion-rutas-app
