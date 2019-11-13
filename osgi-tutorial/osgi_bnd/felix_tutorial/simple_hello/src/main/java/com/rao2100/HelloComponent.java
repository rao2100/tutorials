package com.rao2100;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class HelloComponent {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Activate
	public void start(BundleContext context) {
		log.info("Hello World");
	}

	@Deactivate
	public void stop(BundleContext context) {
		log.info("Bye World");
	}

	/**
     * Implements ServiceListener.serviceChanged().
     * Prints the details of any service event from the framework.
     * @param event the fired service event.
    **/
    // public void serviceChanged(ServiceEvent event)
    // {
    //     String[] objectClass = (String[])
    //         event.getServiceReference().getProperty("objectClass");

    //     if (event.getType() == ServiceEvent.REGISTERED)
    //     {
    //         System.out.println(
    //             "Ex1: Service of type " + objectClass[0] + " registered.");
    //     }
    //     else if (event.getType() == ServiceEvent.UNREGISTERING)
    //     {
    //         System.out.println(
    //             "Ex1: Service of type " + objectClass[0] + " unregistered.");
    //     }
    //     else if (event.getType() == ServiceEvent.MODIFIED)
    //     {
    //         System.out.println(
    //             "Ex1: Service of type " + objectClass[0] + " modified.");
    //     }
    // }

	
}
