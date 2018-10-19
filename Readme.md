ReadMe.

Instructions detailing how to run the application on a users laptop:

This is a standalone application that has the capability to build an executable jar.

To run the app from IDE or run it from the accompanying jar follow these steps:
1. Import the project into an IDE (Intellij, Eclipse, etc)
2. Build and Run PersonApplication from within the IDE
3. OR, locate included JAR file for PersonApplication (PersonApplication.jar)
4. and create a Run configuration to run a Jar.
Once the application is running, the application is located on the port 8081 ( this can be
changed via application.properties). The following are the URLS to access the application:
1. PUT - http://localhost:8081/persons/data/ (accepts JSON body)
        e.g. {
             	"name": "Jen",
             	"age": 21,
             	"locale": "US"
             }

2. GET - http://localhost:8081/persons/data/
         http://localhost:8081/persons/data/{id}
3. DELETE - http://localhost:8081/persons/data/{id}



Describe two different methods to deploy the API and describe the pros and cons of each approach
1. Traditional deployments:
    Spring boot apps can be deployed traditionally to any container (Jboss, Tomcar) via a war file. The project isn't setup
    to generate a war file but it is a simple update that can change the output artifact to a war. The downside
    to this approach is that the onus of maintaining the containers and load balancers (along with failsafes)
     is on us.
2. ContainerLess deployments:
    This is the more modern approach to deploying an app. It allows for better control over containers
    and load balancers which are embedded. This is a much cleaner way to deploy apps and is better for
     performance tuning. However, the downside is that only one app can run on one container.


Securing the endpoint in production
Endpoint security is built into Spring Security. A basic Spring security configuration can look like this:
@Configuration
public class ActuatorSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
				.anyRequest().hasRole("ENDPOINT_ADMIN")
				.and()
			.httpBasic();
	}

}

This uses requestMatcher(EndpointRequest.toAnyEndpoint()) to let Spring know that only ENDPOINT_ADMIN rolese
are allowed access.


Monitoring the endpoint
Spring Actuators make a number of default endpoints available for use within an APP. e.g. /actuator/health, /actuator/env
There is also room to add custom end points by using the @Bean with @Endpoint annotations. Within the bean any
method annotated with annotations like @ReadOperation and @WriteOperation are exposed in JMX and over Http.


Scaling the deployment to meet continually escalating demand
Deploying to cloud in the future to fulfil scaling requirements can be an option. It comes with tools that allow
applications to scale e.g. Load balancers, Failover paths for APIs. The best way to plan for future scaling requirements
is to get data of how the application is performing today and predict best and worst case scenarios. These will then
allow us to work a solution into the applications. Planning for future changes (other language implementations outside of JSON,
expecting higher traffic due to other events).

