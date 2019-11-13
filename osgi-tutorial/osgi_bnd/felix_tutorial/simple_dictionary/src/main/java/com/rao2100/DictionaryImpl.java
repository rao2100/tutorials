package com.rao2100;

import java.util.Hashtable;

import com.rao2100.service.DictionaryService;

public class DictionaryImpl implements DictionaryService
{
    // The set of words contained in the dictionary.
    String[] m_dictionary =
        { "welcome", "to", "the", "osgi", "tutorial" };

    /**
     * Implements DictionaryService.checkWord(). Determines
     * if the passed in word is contained in the dictionary.
     * @param word the word to be checked.
     * @return true if the word is in the dictionary,
     *         false otherwise.
    **/
    public boolean checkWord(String word)
    {
        word = word.toLowerCase();

        // This is very inefficient
        for (int i = 0; i < m_dictionary.length; i++)
        {
            if (m_dictionary[i].equals(word))
            {
                return true;
            }
        }
        return false;
    }
}
