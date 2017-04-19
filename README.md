# web-server-application
Web Application implemented in Java, using . [com.sun.net.httpserver.HttpServer](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html)   
This application has 3 different pages. In order to access, the user needs authorization and an specific role for each page. The user session will expire in 5 minutes from the last user action.
  - /p1 (ROLE_1)
  - /p2 (ROLE_2)
  - /p3 (ROLE_3)
 
To access the local server, open a browser at http://localhost:8080/p1
### Build and Deploy
   $ mvn clean install server
   $ java -jar server.jar

### REST API User
The application also provides a REST API for User management.  
##### Auth
HTTP basic authentication 
##### Role
ADMIN
| Method | Endpoint | Usage | Returns
| ------ | -------- | ----- | -----
| GET | /users | Get users | Users
| GET | /users/{id} | Get user | User
| DELETE | /users/{id} | Delete user | Deleted User
| POST | /users | Add or update user | User

### Users
| User | Password | Roles
| -----| -------- | ----- 
| admin | admin | ADMIN, ROLE1 
| user1 | user1 | ROLE1 
| user2 | user2 | ROLE2 
| user23 | user23 | ROLE2, ROLE3

### TODO
