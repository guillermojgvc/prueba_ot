PRUEBA TÉCNICA 


============================================================================
# Pre Requisitos: 
    -- CON DOCKER DESARROLLO --
    > Para mayor facilidad del evaluador el proyecto incluye los archivos de creación del ambiente de 
    base de datos en docker "docker-compose.yml"
    > Levantar la base de datos Postgres con el comando (De forma automática se creara la base de datos y los esquemas ontop y provider, a su vez se restauraran los datos de entrada iniciales)
        %> docker-compose up
    
    -- SIN DOCKER --
    > En caso de que el evaluador considere realizar las pruebas en ambiente local Postgres deberá crear la base de datos y los esquemas ontop y provider ejecutando los scripts de la carpeta "database/init" en el siguiente orden:
        * 01 create_database.sql
        * 02 seed_tables.sql
============================================================================

# SPRINGBOOT: 
    Carpeta "vehiculos", aplicación Springboot para la administración de vehículos

    > Importar el proyecto en SpringToolSuite4 (proyecto Maven)
    > Realizar el cambio del archivo resources en la ubicación 
        wallet/src/main/resources/application.properties
        Ajustar los valores de la conexión como host, puerto, usuario y password (El archivo ya cuenta con la configuración del ambiente docker de desarrollo)
    > La aplicación cuenta por defecto con un sandbox de pruebas para los endpoints generado con Swagger que puede ser accedido de forma local en la siguiente URL:
        - http://localhost:3000/swagger-ui/index.html#/
    
    > En caso de que el evaluador requiera se incluye la coleccion postman "OnTop.postman_collection"
    
    > El modelo de base de datos puede ser consultado de la imagen modelo_db.png, consta de 2 esquemas 
        * ONTOP      -> Esquema para transacciones del wallet
        * PROVIDER   -> Esquema para registro de las transacciones del proveedor externo

    > El API desarrollado cuenta con información percargada de 1 usuario con user_id = 1
    > El usuario debe crear primero una cuenta como se muestra en el ejemplo: 
    
    http://localhost:3000/recipient_acounts/add
    
    {
        "first_name": "TONY",
        "last_name": "STARK",
        "routing_number": "211927207",
        "account_number": "1885226711",
        "identification": "0912345678",
        "bank_name": "New York Bank",
        "user_id": 1,
        "as_default": true
    }

    > El usuario puede hacer movimientos con el api
    
    http://localhost:3000/wallets/transactions

    en caso de transferencia a un banco (proveedor externo) se realiza una petición tipo feign para obtener los resultados del endpoint
