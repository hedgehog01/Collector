/*
 * Copyright (C) 2014 Hedgehog01
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.jjlcollectors.util.service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hedgehog01
 */
public final class VerifyString
{
    private static final Logger LOG = Logger.getLogger(VerifyString.class.getName());
    
    /**
     * method to verify that a String is Legal and has no forbidden special chars
     * @param str the string to check
     * @return true String is Legal
     */
    public static boolean isStringLegal (String str)
    {
        boolean isLegal = true;
        char ch1 = '"';
        char ch2 = '\'';
        if (str == null)
        {
            LOG.log(Level.SEVERE, "String is null");
            isLegal = false;
        }
        else if (str.trim().isEmpty())
        {
            LOG.log(Level.INFO, "String is empty");
            isLegal = false;
        }
        
        else if (str.indexOf(ch1) != -1 || str.indexOf(ch2) != -1)
        {
            LOG.log(Level.INFO, "String contains Non-Legal Chars.\nString: {0}",str);
            isLegal = false;
        }
            
        return isLegal;
    }
    
    /**
    * method to check if string contains numeric value.
     * @param str the String to evaluate.
     * @return true if String is numeric.
    */
    public static boolean isNumeric(String str)
    {
        try  
        {  
            double d = Double.parseDouble(str);  
        }  
        catch(NumberFormatException nfe)  
        {  
            LOG.log(Level.INFO, "Non numeric value. Value was: {0}", str);
            return false;  
        }  
        return true; 
        
    }
    
}
