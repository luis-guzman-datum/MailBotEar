package com.confia.mailbot.ws.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;


@Provider
@Compress
public class GZIPWriterInteceptor implements WriterInterceptor{

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
		
		MultivaluedMap<String, Object> headers = context.getHeaders();
		headers.add("Content-Encoding", "gzip");
		
		final OutputStream outputstream = context.getOutputStream();
		context.setOutputStream(new GZIPOutputStream(outputstream));
		System.out.println("GZIP INTERCEPTOR");
		context.proceed();
	}

}
