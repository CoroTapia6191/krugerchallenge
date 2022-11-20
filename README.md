# krugerchallenge
Back end developer challenge
-Proyecto realizado en el ambiente de Spring Tool Suite 4 de Eclipse.

Para el despliegue primero se debe ejecutar el script “ScriptDatabae” en la base local postgresql, las tres primeras líneas crearán la nueva base de datos y el usuario para acceder a la base de datos.

Consultar documentación en ../swagger-ui/index.html

Los web services desplegados están protegidos por roles y JWT, para poder consumir inicialmente se debe llamar al siguiente método post sin parámetros: http://localhost:8080/personservice/newAdmin , el cual genera un usuario con rol ADMINISTRADOR (username=9999999999 y password=9999999999);

Con el usuario indicado se puede autenticar en el método post http://localhost:8080/userservice/login y se obtiene el token con rol ADMINISTRADOR para consumir los servicios desplegados.

Al crear una persona se crean los usuarios solicitados con username y password = cedula ingresada y con rol EMPLEADO.

En caso de requerir modificar el rol de la persona creada puede utilizar el servicio /userservice/addRoleToUser (consultar documentación)

