package izeller.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import izeller.server.security.model.Role;
import izeller.server.security.model.SecurityRouteData;
import izeller.server.security.model.User;
import izeller.server.security.model.SecurityRouteData.Auth;
import izeller.server.security.SecurityInterceptorManager;
import izeller.server.security.SecurityService;
import izeller.server.security.SessionRepository;
import izeller.server.security.UserRepository;
import izeller.server.web.ControllerHandler;
import izeller.server.web.InvokerHandler;
import izeller.server.web.handler.BasicPageHandler;
import izeller.server.web.handler.DeleteUserHandler;
import izeller.server.web.handler.LoginActionHandler;
import izeller.server.web.handler.LoginRedirectHandler;
import izeller.server.web.handler.NewUserHandler;
import izeller.server.web.handler.RemoveSessionHandler;
import izeller.server.web.handler.UserHandler;
import izeller.server.web.handler.UserPerIdHandler;
import izeller.server.web.route.Route;
import izeller.server.web.route.Router;
import izeller.server.web.route.Route.RequestMethod;
import izeller.server.web.view.HtmlView;
import izeller.server.web.view.JsonView;

public class App {
	
	public static void main(String[] args) throws IOException {
		
		HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
		UserRepository userRepository = new UserRepository();
		userRepository.save(new User("user1", "user1").addRole(Role.PAGE_1));
		userRepository.save(new User("user2", "user2").addRole(Role.PAGE_2));
		userRepository.save(new User("user23", "user23").addRole(Role.PAGE_2).addRole(Role.PAGE_3));
		
		userRepository.save(new User("admin", "admin").addRole(Role.ADMIN).addRole(Role.PAGE_3));
	
		SessionRepository sessionRepository = new SessionRepository();
		SecurityService securityService = new SecurityService(userRepository, sessionRepository);
		Router router = new Router(new SecurityInterceptorManager(securityService));
		
		router.attach(new ControllerHandler.Builder(new Route("/login", RequestMethod.GET))
				.addHandler(new LoginRedirectHandler())
				.addView(new HtmlView("login"))
				.create());
		router.attach(new ControllerHandler.Builder(new Route("/loginAction", RequestMethod.POST))
				.addHandler(new LoginActionHandler(securityService, "/p1"))
				.create());
		router.attach(new ControllerHandler.Builder(new Route("/removeSession", RequestMethod.GET))
				.addHandler(new RemoveSessionHandler(sessionRepository))
				.create());
		
		router.attach(new ControllerHandler.Builder(new Route("/p1", RequestMethod.GET))
				.addHandler(new BasicPageHandler())
				.addView(new HtmlView("page"))
				.create(),
				new SecurityRouteData(Auth.SESSION, Role.PAGE_1));
		router.attach(new ControllerHandler.Builder(new Route("/p2", RequestMethod.GET))
				.addHandler(new BasicPageHandler())
				.addView(new HtmlView("page"))
				.create(),
				new SecurityRouteData(Auth.SESSION, Role.PAGE_2));
		router.attach(new ControllerHandler.Builder(new Route("/p3", RequestMethod.GET))
				.addHandler(new BasicPageHandler())
				.addView(new HtmlView("page"))
				.create(),
				new SecurityRouteData(Auth.SESSION, Role.PAGE_3));
		
		router.attach(new ControllerHandler.Builder(new Route("/users", RequestMethod.GET))
				.addHandler(new UserHandler(userRepository))
				.addView(new JsonView())
				.create(),
				new SecurityRouteData(Auth.BASIC, Role.ADMIN));
		
		router.attach(new ControllerHandler.Builder(new Route("/users/([a-zA-Z0-9]+)", RequestMethod.GET))
				.addHandler(new UserPerIdHandler(userRepository))
				.addView(new JsonView())
				.create(),
				new SecurityRouteData(Auth.BASIC, Role.ADMIN));
		router.attach(new ControllerHandler.Builder(new Route("/users/([a-zA-Z0-9]+)", RequestMethod.DELETE))
				.addHandler(new DeleteUserHandler(userRepository))
				.addView(new JsonView())
				.create(),
				new SecurityRouteData(Auth.BASIC, Role.ADMIN));
		router.attach(new ControllerHandler.Builder(new Route("/users", RequestMethod.POST))
				.addHandler(new NewUserHandler(userRepository))
				.addView(new JsonView())
				.create(),
				new SecurityRouteData(Auth.BASIC, Role.ADMIN));
		
		httpServer.createContext("/", new DispatcherHandler(new InvokerHandler(router)));
		httpServer.start();
	}
}
