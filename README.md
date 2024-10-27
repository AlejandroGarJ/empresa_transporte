# Aplicación de Gestión para Camioneros

Esta aplicación permite la gestión de camioneros, camiones, direcciones, paquetes y rutas. Está diseñada para camioneros y administradores, con roles claramente definidos para cada tipo de usuario.

## Características Principales

- **Login** y autenticación para usuarios con roles de **Camionero** o **Administrador**.
- **CRUD** de entidades: Usuarios, Camioneros, Camiones, Direcciones, Paquetes y Rutas.
- **Servicios** de entrega y recogida de paquetes.

---

## Reglas de Negocio

### Direcciones y Ciudades
- Una **dirección** se puede crear directamente o al crear un nuevo **camionero**.
- La **ciudad** de una dirección debe existir previamente en la base de datos.
- Al dar de baja una dirección, se verifica que no esté asociada a ningún **camionero**.

### Creación y Gestión de Envíos
- Para crear un **camionero_camion** (ruta), debe existir previamente un **camionero** y un **camión**.
- Un **camionero_camion** no se puede dar de baja ni eliminar.
- Si un **camionero_camion** está relacionado con un camionero o camión que es dado de baja, este continuará existiendo.

### Roles de Usuario
1. **Creación de Usuarios**:
   - Solo un **Administrador** puede crear otro administrador.
   - Todos los usuarios se crean con el rol de **Camionero** por defecto, y solo un administrador puede cambiar su rol.
   
2. **Actualización de Entidades**:
   - Un **Administrador** puede actualizar cualquier entidad por su ID.
   - Un **Camionero** solo puede actualizar entidades que le pertenezcan.
   - **Ciudad** y **Dirección** son entidades inmutables.
   - Los datos que pueden actualizarse en un **Camionero** son: Dirección, Teléfono y Salario (solo por Admin).

---

## Documentación de Roles

Existen dos roles en el sistema: **ADMIN** y **CAMIONERO**. Cada usuario solo puede tener un rol asignado.

### ROL ADMIN
- Puede acceder a todos los endpoints de la aplicación.
- Puede modificar el rol de cualquier usuario, así como actualizar la contraseña de otros usuarios.
- Puede tener un camionero asignado y, por tanto, realizar algunas acciones propias del rol camionero.
- No puede crear directamente entidades específicas de **Camionero** como rutas, camiones, y paquetes, salvo que tenga un **Camionero** relacionado.

### ROL CAMIONERO
- Puede crear y gestionar sus propios **Camionero**, **Rutas** y **Paquetes**.
- No puede acceder a datos ni acciones de otros camioneros.
- Se sigue el principio de mínima responsabilidad.

---

## Casos de Uso y Endpoints

### 1. Camionero

#### Crear Camionero
- **Endpoint**: `camionero/create`
- **Descripción**: Un usuario sin un camionero asignado puede crear uno, verificando que no existe otro con el mismo DNI.
- **Datos adicionales**:
   - Puede relacionarse con una dirección ya existente pasando el ID, o crear una nueva pasando los datos de la dirección.

#### Modificar Camionero
- **Endpoint**: `camionero/update`
- **Descripción**: Un camionero puede modificar sus datos; un admin puede modificar los datos de cualquier camionero.
- **Notas**: El salario solo puede ser modificado por un admin.

#### Crear Ruta
- **Endpoint**: `camioneroCamion/create`
- **Descripción**: Asocia un camión con un camionero y una fecha de salida.
- **Notas**: Los paquetes asociados a esta ruta se asignan al camionero de la solicitud.

#### Crear Paquete
- **Endpoint**: `paquete/create`
- **Descripción**: Crea un paquete asignado a una ruta (camionero_camion).
- **Notas**: Un admin no puede añadir un paquete a una ruta de un camionero.

#### Cambiar Estado de Paquete
- **Endpoint**: `paquete/cambiarEstado`
- **Descripción**: Permite cambiar el estado de un paquete a `ENVIADO`, `A_ESPERA_DE_RECOGER` o `RECOGIDO`.

#### Eliminar Paquete
- **Endpoint**: `paquete/deleteByID`
- **Descripción**: Cambia el estado de un paquete a `dado_baja` sin eliminarlo de la base de datos.

#### Mostrar Rutas del Usuario
- **Endpoint**: `camioneroCamion/getAllByUser`
- **Descripción**: Muestra todas las rutas de un usuario.

### 2. Admin

#### Mostrar Todos los Camiones
- **Endpoint**: `camion/getAll`
- **Descripción**: Permite listar todos los camiones.

#### Mostrar Todas las Ciudades
- **Endpoint**: `ciudad/getAll`
- **Descripción**: Permite listar todas las ciudades.

#### Modificar Usuario
- **Endpoint**: `user/update`
- **Descripción**: Un admin puede actualizar los datos de cualquier usuario, mientras que un camionero solo puede actualizar sus propios datos.

---

## Notas de Integridad de Datos

Para asegurar la integridad de los datos:
- Un camionero solo puede acceder y modificar entidades que le pertenezcan.
- La relación entre camionero y camionero_camion es persistente, y los datos no se eliminan al dar de baja un camionero o camión.
- Ciudades y direcciones son inmutables una vez creadas.

  ## Notas de Seguridad

Para facilitar la corrección en el contexto del curso, la clave de JWT utilizada en esta aplicación es pública. Esto permite que los evaluadores puedan verificar 
la autenticación sin dificultades adicionales. **No se recomienda el uso de claves públicas en entornos de producción.**


