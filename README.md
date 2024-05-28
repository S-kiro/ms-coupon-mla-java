# Cúpon de compra

En este microservicio se creaan los usuarios y sus productos favoritos para 
posteriormente crear un cupon de descuento en base a dichos productos y un presusuesto. 
Tambien se podran los productos con mayor cantidad de favoritos.

![Java][shield-java]
![Apache_Maven][shield-maven]
![Spring Framework][shield-spring]
![IntelliJ_IDEA][shield-intellij-idea]
![Spring Boot][shield-spring-boot]

## Requisitos
Necesitara de los siguientes programas para ejecutar el proyecto:

* Open JDK 17
* Apache Maven 3.3
* Programa para consumir los endpoints (SOAP UI, Postman, etc.)
* ID de desarrollo (Intellij, Eclipse, Netbeans, etc.)

## Endpoints del microservicio
### Create: addFavoriteItem/toUser
Retorna el id del usuario junto a los items asociados y los que no fueron encontrados.

```
POST /mla/v1/addFavoriteItem/toUser
Accept: application/json
Content-Type: application/json

{
    "itemsIds": ["MLA1", "MLA2"],
    "userId": "USERTEST"
}

RESPONSE: HTTP 200 (Ok)

{
    "itemsIds": [
        "MLA2"
    ],
    "itemsIdsNoEncontrados": [
        "MLA1"
    ],
    "userId": "USERTEST"
}

Location header: http://localhost:8081/mla/v1/addFavoriteItem/toUser
```

### Select: coupon
Retorna los productos cuya sumatoria de sus precios se acerque lo mas posible al valor del cúpon.

```
POST /mla/v1/coupon
Accept: application/json
Content-Type: application/json

{
    "itemsIds": ["MLA1", "MLA2", "MLA3", "MLA4", "MLA5"],
    "amount": 500
}

RESPONSE: HTTP 200 (Ok)

{
    "itemsIds": ["MLA1", "MLA2", "MLA4", "MLA5"],
    "amount": 480
}

Location header: http://localhost:8081/mla/v1/coupon
```

### Select: coupon/stats
Retorna los 5 productos que mas se han seleccionado como favoritos.

```
GET /mla/v1/coupon/stats
Accept: application/json
Content-Type: application/json
RESPONSE: HTTP 200 (Ok)

[
    {
        "id": "MLA2",
        "quantity": 2
    },
    ...
]

Location header: http://localhost:8081/mla/v1/coupon/stats
```

### Select: user/create
Retorna mensaje de confirmacion del usuario creado.

```
POST /mla/v1/user/create
Accept: application/json
Content-Type: application/json

{
    "userId":"USERTEST"
}   

RESPONSE: HTTP 201 (Created)

"Usuario creado con exito"

Location header: http://localhost:8081/mla/v1/user/create
```

[shield-java]: https://img.shields.io/badge/Java-17-orange.svg
[shield-spring]: https://img.shields.io/badge/Spring_framework-6DB33F?logo=spring&logoColor=white
[shield-spring-boot]: https://img.shields.io/badge/Springboot-6DB33F?logo=springboot&logoColor=white
[shield-intellij-idea]: https://img.shields.io/badge/IntelliJ_IDEA-000000?logo=intellijidea&logoColor=white
[shield-maven]: https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white
