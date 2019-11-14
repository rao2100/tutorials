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
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rao2100.service.SpellCheckService;

@Component
public class SpellClientReference {    
    Logger log = LoggerFactory.getLogger(this.getClass());

    // Bundle's context.
    private BundleContext m_context = null;
    // The service tacker object.
    // private ServiceTracker m_tracker = null;

    // @Reference(policy=ReferencePolicy.DYNAMIC, cardinality=ReferenceCardinality.AT_LEAST_ONE)
    private volatile SpellCheckService checker;    

    @Reference
    void bindSpellCheckService(SpellCheckService checker) {
        this.checker = checker;
    }


	@Activate
	public void start(BundleContext context) throws Exception {

        System.out.println("Starting spell client");

		m_context = context;

        try
        {
            System.out.println("Enter a blank line to exit.");
            String passage = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            // Loop endlessly.
            while (true)
            {
                // Ask the user to enter a passage.
                System.out.print("Enter passage: ");
                passage = in.readLine();

                // Get the selected dictionary service, if available.
                // SpellCheckService checker = (SpellCheckService) m_tracker.getService();

                // If the user entered a blank line, then
                // exit the loop.
                if (passage.length() == 0)
                {
                    break;
                }
                // If there is no spell checker, then say so.
                else if (checker == null)
                {
                    System.out.println("No spell checker available.");
                }
                // Otherwise check passage and print misspelled words.
                else
                {
                    String[] errors = checker.check(passage);

                    if (errors == null)
                    {
                        System.out.println("Passage is correct.");
                    }
                    else
                    {
                        System.out.println("Incorrect word(s):");
                        for (int i = 0; i < errors.length; i++)
                        {
                            System.out.println("    " + errors[i]);
                        }
                    }
                }
            }
        } catch (Exception ex) { }

	}

	@Deactivate
	public void stop(BundleContext context) {
		log.info("Stopping SpellClientReference");
	}
	
	
}
