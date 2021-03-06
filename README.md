# web-server-application
Web Application implemented in Java, using . [com.sun.net.httpserver.HttpServer](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html)   
This application has 3 different pages. In order to access, the user needs authorization and an specific role for each page. The user session will expire in 5 minutes from the last user action.
  - /p1 (ROLE_1)
  - /p2 (ROLE_2)
  - /p3 (ROLE_3)
 
To access the local server, open a browser at http://localhost:8080/p1
### Build and Deploy
   $ mvn clean package web-server-application
   
   $ cd /target
   
   $ java -jar web-server-application.jar

### REST API User
The application also provides a REST API for User management.  

##### Auth
HTTP basic authentication
 
##### Role
ADMIN

| Method | Endpoint | Usage | Returns
| ------ | -------- | ----- | -------
| GET | /users | Get users | Users
| GET | /users/{id} | Get user | User
| DELETE | /users/{id} | Delete user | Deleted User
| POST | /users | Add or update user | User

Post Json Data:
```json
{
	"user": "user7",
	"password":"user7",
	"roles": [
		"PAGE_1",
		"PAGE_2",
		"PAGE_3"
   ]
}
```
### Users
| User | Password | Roles
| -----| -------- | ----- 
| admin | admin | ADMIN, ROLE3
| user1 | user1 | ROLE1 
| user2 | user2 | ROLE2 
| user23 | user23 | ROLE2, ROLE3

### TODO

* Improve code coverage
* Refactor some classes (for example: HttpRequest), writing cleaner code and for easier unit testing.
* Add content negotiation. XmlView can be implemented, and ControllerHandler should accept a set of views. Then a different view can be used, depending on the Accept header. 