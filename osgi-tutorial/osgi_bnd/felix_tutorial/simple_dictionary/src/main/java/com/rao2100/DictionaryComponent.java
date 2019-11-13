package com.rao2100;

import java.util.Hashtable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceEvent;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import com.rao2100.service.DictionaryService;

@Component
public class DictionaryComponent {
	// Logger log = LoggerFactory.getLogger(this.getClass());

	@Activate
	public void start(BundleContext context) {
		// log.info("Starting Dictionary Service");
		Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("Language", "English");
        context.registerService(
            DictionaryService.class.getName(), new DictionaryImpl(), props);
	}

	@Deactivate
	public void stop(BundleContext context) {
		// log.info("Bye World");
	}

	
}
