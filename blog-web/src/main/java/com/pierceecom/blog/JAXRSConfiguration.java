package com.pierceecom.blog;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/service")
public class JAXRSConfiguration  extends Application {
   
	//Let the server scan for resources by himself
//	@Override
//    public Set<Class<?>> getClasses() {
//        HashSet<Class<?>> classes = new HashSet<>();
//        classes.add(HelloPierceResource.class);
//        classes.add(PostResource.class);
//        return classes;
//    }
	
}
