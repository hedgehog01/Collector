/*
 * Copyright (C) 2015 nathanr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jjlcollectors.util.prefrences;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 * Class to handle preferences
 *
 * @author nathanr
 */
public final class PrefrencesHandler
{

    private static final Logger LOG = Logger.getLogger(PrefrencesHandler.class.getName());

    /**
     * Returns the last used user login email preference
     *
     * @return the last used user login email preference or null if doesn't
     * exist
     */
    public static String getLastUsedUserEmail()
    {
        LOG.log(Level.INFO, "Attemp to get last user used email login");
        Preferences prefs = Preferences.userNodeForPackage(PrefrencesHandler.class);
        String userEmail = prefs.get("userEmail", null);
        if (userEmail != null)
        {
            LOG.log(Level.INFO, "user Email returned from prefrences: {0}", userEmail);
            return userEmail;
        } else
        {
            LOG.log(Level.INFO, "No user email found in prefrences");
            return null;
        }
    }

    /**
     * Sets the last used user login email preference.
     *
     * @param userEmail the last used user login email preference.
     */
    public static void setLastUsedUserEmail(String userEmail)
    {
        Preferences prefs = Preferences.userNodeForPackage(PrefrencesHandler.class);
        if (userEmail != null || userEmail.isEmpty())
        {
            LOG.log(Level.INFO, "user email prefrences set to: {0}", userEmail);
            prefs.put("userEmail", userEmail);

        } else
        {
            LOG.log(Level.INFO, "user email is null or empty, remving from prefrences");
            prefs.remove("userEmail");
        }
    }

    public static File getFolderPath()
    {
        LOG.log(Level.INFO, "");
        Preferences prefs = Preferences.userNodeForPackage(PrefrencesHandler.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null)
        {
            LOG.log(Level.INFO, "file path returned from prefrences: {0}", filePath);
            return new File(filePath);
        } else
        {
            LOG.log(Level.INFO, "No file path found in prefrences");
            return null;
        }
    }

    /**
     * Sets the folder path of the currently selected folder.
     *
     * @param file the folder location or null to remove the path
     */
    public static void setFolderPath(File file)
    {
        Preferences prefs = Preferences.userNodeForPackage(PrefrencesHandler.class);
        if (file != null)
        {
            LOG.log(Level.INFO, "file path prefrences set to: {0}", file.getPath());
            prefs.put("filePath", file.getPath());

        } else
        {
            LOG.log(Level.INFO, "file path null , remving from prefrences");
            prefs.remove("filePath");
        }
    }

}
