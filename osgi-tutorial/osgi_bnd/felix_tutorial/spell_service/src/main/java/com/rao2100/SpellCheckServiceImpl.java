package com.rao2100;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import com.rao2100.service.DictionaryService;
import com.rao2100.service.SpellCheckService;

@Component
public class SpellCheckServiceImpl implements SpellCheckService
{
     /**
     * List of service objects.
     *
     * This field is managed by the Service Component Runtime and updated
     * with the current set of available dictionary services.
     * At least one dictionary service is required.
     */
    @Reference(policy=ReferencePolicy.DYNAMIC, cardinality=ReferenceCardinality.AT_LEAST_ONE)
    private volatile List<DictionaryService> m_svcObjList;

    /**
     * Checks a given passage for spelling errors. A passage is any number of
     * words separated by a space and any of the following punctuation marks:
     * comma (,), period (.), exclamation mark (!), question mark (?),
     * semi-colon (;), and colon(:).
     *
     * @param passage
     *            the passage to spell check.
     * @return An array of misspelled words or null if no words are misspelled.
     */
    public String[] check( String passage )
    {
        // No misspelled words for an empty string.
        if ( ( passage == null ) || ( passage.length() == 0 ) )
        {
            return null;
        }

        List<String> errorList = new ArrayList<String>();

        // Tokenize the passage using spaces and punctionation.
        StringTokenizer st = new StringTokenizer( passage, " ,.!?;:" );

        // Put the current set of services in a local field
        // the field m_svcObjList might be modified concurrently
        final List<DictionaryService> localServices = m_svcObjList;

        // Loop through each word in the passage.
        while ( st.hasMoreTokens() )
        {
            String word = st.nextToken();
            boolean correct = false;

            // Check each available dictionary for the current word.
            for(final DictionaryService dictionary : localServices) {
                if ( dictionary.checkWord( word ) )
                {
                    correct = true;
                }
            }

            // If the word is not correct, then add it
            // to the incorrect word list.
            if ( !correct )
            {
                errorList.add( word );
            }
        }

        // Return null if no words are incorrect.
        if ( errorList.size() == 0 )
        {
            return null;
        }

        // Return the array of incorrect words.
        return errorList.toArray( new String[errorList.size()] );
    }
}