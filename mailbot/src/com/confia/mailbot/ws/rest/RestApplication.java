package com.confia.mailbot.ws.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class RestApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<>();
		classes.add(MailTailRest.class);
		classes.add(MailProcesoRest.class);
		classes.add(CORSResponseFilter.class);
		
		return classes;
	}
	
}
