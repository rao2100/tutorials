package com.rao2100;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rao2100.service.DictionaryService;

@Component
public class DictionaryClientRobust {    
    Logger log = LoggerFactory.getLogger(this.getClass());

    // Bundle's context.
    private BundleContext m_context = null;
    // The service tacker object.
    private ServiceTracker m_tracker = null;

	@Activate
	public void start(BundleContext context) throws Exception {

        System.out.println("Starting dictionary client");

		m_context = context;

        // Create a service tracker to monitor dictionary services.
        m_tracker = new ServiceTracker(
            m_context,
            m_context.createFilter(
                "(&(objectClass=" + DictionaryService.class.getName() + ")" +
                "(Language=*))"),
            null);
        m_tracker.open();

        try
        {
            System.out.println("Enter a blank line to exit.");
            String word = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            // Loop endlessly.
            while (true)
            {
                // Ask the user to enter a word.
                System.out.print("Enter word: ");
                word = in.readLine();

                // Get the selected dictionary service, if available.
                DictionaryService dictionary = (DictionaryService) m_tracker.getService();

                // If the user entered a blank line, then
                // exit the loop.
                if (word.length() == 0)
                {
                    break;
                }
                // If there is no dictionary, then say so.
                else if (dictionary == null)
                {
                    System.out.println("No dictionary available.");
                }
                // Otherwise print whether the word is correct or not.
                else if (dictionary.checkWord(word))
                {
                    System.out.println("Correct.");
                }
                else
                {
                    System.out.println("Incorrect.");
                }
            }
        } catch (Exception ex) { }

	}

	@Deactivate
	public void stop(BundleContext context) {
		log.info("Stopping DictionaryClientRobust");
	}
	
	
}
