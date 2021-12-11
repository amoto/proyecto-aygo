# Proyecto Grupo 2

JULIAN DAVID DEVIA SERNA

DIEGO FERNANDO RODRÍGUEZ TORRES

GERMÁN ANDRÉS LÓPEZ PACHECO

JHONNY MAURICIO BURBANO MORENO

Para el proyecto dividimos el backend, el frontend y el deployment (aws-cdk) en carpetas diferentes dentro de este
repositorio.

El backend se desarrolló en Java utilizando Springboot y Postgres para la base de datos, la cual se utiliza Jpa para la
conexión, cuenta con un Dockerfile donde se especifica como se tiene que instanciar la aplicación y un docker-compose para
levantar las imágenes de la aplicación y el postgres. Para la seguridad contiene Spring security.

Cuenta con los siguientes recursos:

1. Obtener una pregunta por id

   `GET /faq/question/{id}`

    Retorna:

   ``` 
   {
    "id": 2,
    "title": "primera pregunta",
    "description": "primera descripcion",
    "tags": "java,springboot,postgres",
    "created_at": "2021-12-11T01:52:06.802+00:00",
    "created_by": "test_user",
    "votes_up": 0,
    "votes_down": 0
   }
   ```
2. Obtener todas las preguntas

   `GET /faq/questions`


   Retorna:

   ``` 
   [
    {
        "id": 1,
        "title": "primera pregunta",
        "description": "primera descripcion",
        "tags": "java,springboot,postgres",
        "created_at": "2021-12-10T03:12:45.157+00:00",
        "created_by": "test_user",
        "votes_up": 0,
        "votes_down": 0
    },
    {
        "id": 2,
        "title": "primera pregunta",
        "description": "primera descripcion",
        "tags": "java,springboot,postgres",
        "created_at": "2021-12-11T01:52:06.802+00:00",
        "created_by": "test_user",
        "votes_up": 0,
        "votes_down": 0
    }
   ]
   ``` 
   
3. Guardar una pregunta

   `POST /faq/question`


   Request:

   ``` 
   {
    "title":"primera pregunta",
    "description": "primera descripcion",
    "tags":"java,springboot,postgres"
   }
   ``` 
   
   Retorna:

   ``` 
   {
   "id": 2,
   "title": "primera pregunta",
   "description": "primera descripcion",
   "tags": "java,springboot,postgres",
   "created_at": "2021-12-11T01:52:06.802+00:00",
   "created_by": "test_user",
   "votes_up": 0,
   "votes_down": 0
   }
   ``` 
5. Obtener todas las preguntas creadas por un usuario (el usuario se obtiene del Jwt que se envía desde Cognito)

   `GET /faq/questions/`


   Retorna:

   
``` 
      [
         {
         "id": 1,
         "title": "primera pregunta",
         "description": "primera descripcion",
         "tags": "java,springboot,postgres",
         "created_at": "2021-12-10T03:12:45.157+00:00",
         "created_by": "test_user",
         "votes_up": 0,
         "votes_down": 0
         },
         {
         "id": 2,
         "title": "primera pregunta",
         "description": "primera descripcion",
         "tags": "java,springboot,postgres",
         "created_at": "2021-12-11T01:52:06.802+00:00",
         "created_by": "test_user",
         "votes_up": 0,
         "votes_down": 0
         }
      ]
   ``` 

5. Obtener las respuestas de una pregunta específica

   `GET /faq/responses/question/{questionId}`

   
   Retorna:

   ```
   [
    {
        "id": 3,
        "text": "segunda respuesta para la primera pregunta",
        "accepted": false,
        "created_at": "2021-12-11T02:05:55.357+00:00",
        "created_by": "test_user",
        "up_votes": 0,
        "down_votes": 0
    }
   ]
   ```

6. Guardar una respuesta
   
   `POST /faq/response/question/{questionId}`


   Request:

   ```
   {
    "text": "segunda respuesta para la primera pregunta",
    "up_votes": 0,
    "down_votes": 0
   }
   ```

   Retorna:

   ```
     {
    "id": 3,
    "text": "segunda respuesta para la primera pregunta",
    "accepted": false,
    "created_at": "2021-12-11T02:05:55.357+00:00",
    "created_by": "test_user",
    "up_votes": 0,
    "down_votes": 0
   }
   ```

7. Obtener todas las respuestas creadas por un usuario (el usuario se obtiene del Jwt que se envía desde Cognito)

   `GET /faq/responses`

   
   Retorna:

   ```
   [
    {
        "id": 3,
        "text": "segunda respuesta para la primera pregunta",
        "accepted": false,
        "created_at": "2021-12-11T02:05:55.357+00:00",
        "created_by": "test_user",
        "up_votes": 0,
        "down_votes": 0
    }
   ]
   ```

8. Votar por una pregunta

   `POST /faq/voteUp/question/{questionId}` -> Voto positivo

   `POST /faq/voteDown/question/{questionId}` -> Voto negativo

   
   Request:

   ```
    {}
   ```

   Retorna:

   ```
   {
    "id": 4,
    "vote_up": 1,
    "vote_down": 0,
    "created_by": "test_user"
   }
   ```


9. Actualizar votación de una pregunta

   `PUT /faq/voteUp/question/{questionId}` -> Voto positivo

   `PUT /faq/voteDown/question/{questionId}` -> Voto negativo

Request:

   ```
   {}
   ```

   Retorna:

   ```
   {}
   ```

11. Votar por una respuesta

   `POST /faq/voteUp/response/{responseId}` -> Voto positivo

   `POST /faq/voteUp/response/{responseId}` -> Voto negativo


   Request:

   ```
   {
    "vote_up": 0,
    "vote_down":0
   }
   ```

   Retorna:

   ```
   {
    "id": 5,
    "created_by": "test_user",
    "vote_up": 0,
    "vote_down": 1
   }
   ```

12. Actualizar voto de una respuesta

   `PUT /faq/voteUp/response/{responseId}` -> Voto positivo

   `PUT /faq/voteUp/response/{responseId}` -> Voto negativo


   Request:

  ```
  {}
  ```

   Retorna:

  ```
  {}
  ```

13. Marcar una respuesta como aceptada

   `PUT /faq/accepted/response/{responseId}`

   Request:

   ```
   {}
   ```

   Retorna:

   ```
   {}
   ```

