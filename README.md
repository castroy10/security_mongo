# Spring Security & MongoDB
#### Программа для демонстрации работы Spring Security и MongoDB

Использованные технологии:
- Spring Boot
- MongoDB
- Spring Security

Для получения JWT токена необходимо сделать POST запрос на /api/gettoken, в теле запроса передать JSON формата {"username": "user","password": "pass"}

Для авторизации с токеном необходимо сдеать GET запрос на один из эндпоинтов /data или /secret следующего формата:

/data?Authorization=Bearer yJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIERldGFpbHMiLCJleHAiOjE2OTUyMDQ4NzQsImlhdCI6MTY5NTIwNDU3NCwidXNlcm5hbWUiOiJ1c2VyIn0.pLe98H2O7E55WIMPgid00kodBymdgtReo-S9l2CAWjI

где yJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIERldGFpbHMiLCJleHAiOjE2OTUyMDQ4NzQsImlhdCI6MTY5NTIwNDU3NCwidXNlcm5hbWUiOiJ1c2VyIn0.pLe98H2O7E55WIMPgid00kodBymdgtReo-S9l2CAWjI – полученный токен.

Программа подразумевает наличие двух пользователей в коллекции appuser:  
admin, с ролями ROLE_ADMIN, ROLE_USER  
user, с ролью ROLE_USER  

Коллекцию в MongoDB добавляем командой:

    db.createCollection("appuser");

Пользователя в MongoDB добавляем командой:

    db.appuser.insertOne({
      username: "user",
      password: "$2a$10$g21qoMauUQ8lL5drzzh9JedxzOvM.L8W0tlsUe7va8.QSoqBkdBRO", //пароль 12345
      roles: ["ROLE_USER"], 
      isAccountNonExpired: true,
      isAccountNonLocked: true,
      isCredentialsNonExpired: true,
      isEnabled: true
    });

Срок жизни токена установлен на 5 минут.
