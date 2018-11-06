package com.pierceecom.blog.resource.annotation.provider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.container.ContainerRequestContext;

import org.h2.util.IOUtils;

public abstract class AbstractFilter {

	protected String getEntityBody(ContainerRequestContext requestContext) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = requestContext.getEntityStream();
         
        final StringBuilder b = new StringBuilder();
        try {

            IOUtils.copy(in, out);
 
            byte[] requestEntity = out.toByteArray();

            if (requestEntity.length == 0) {
                b.append("\n");
            } else {
                b.append(new String(requestEntity)).append("\n");
            }
            requestContext.setEntityStream( new ByteArrayInputStream(requestEntity) );

        } catch (IOException ex) {
        	//ignore
        }

        return b.toString();
    }
	
}
