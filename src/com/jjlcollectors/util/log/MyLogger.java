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
package com.jjlcollectors.util.log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author nathanr
 */
public final class MyLogger
{

    public static Logger logger;
    public Handler fileHandler;
    private final static int LOG_SIZE_LIMIT = 1024000;
    private final static int NUMBER_OF_LOG_FILES = 3;
    private static final boolean APPEND_LOG = true;

    public MyLogger() throws IOException
    {
        //instance the logger
        logger = Logger.getLogger(MyLogger.class.getName());
        //instance the filehandler
        setupLog(logger);

    }

    private static Logger getLogger()
    {
        if (logger == null)
        {
            try
            {
                new MyLogger();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return logger;
    }

    public static void log(Level level, String msg)
    {
        getLogger().log(level, msg);
        System.out.println(msg);
    }

    public static void log(Level level, String msg, Object o)
    {
        getLogger().log(level, msg, o);
    }

    public static void log(Level level, String msg, Object o[])
    {
        getLogger().log(level, msg, o);
    }

    public static void setupLog(Logger LOG)
    {
        LogManager lm = LogManager.getLogManager();
        FileHandler fh;
        try
        {
            String propertyUserDir = System.getProperty("user.dir");
            //String propertyUserHome = System.getProperty("user.home");
            //LOG.log(Level.INFO, "system user.home directory: {0}",propertyUserHome);
            LOG.log(Level.INFO, "system user.dir directory: {0}", propertyUserDir);
            File logFolder = new File(propertyUserDir + "/Log");
            if (!(logFolder.exists() || logFolder.isDirectory()))
            {
                logFolder.mkdirs();
            }
            fh = new FileHandler(logFolder.getPath() + "/Log_Collector.txt", LOG_SIZE_LIMIT, NUMBER_OF_LOG_FILES, APPEND_LOG);

            lm.addLogger(LOG);
            LOG.setLevel(Level.INFO);
            fh.setFormatter(new SimpleFormatter());

            LOG.addHandler(fh);

        } catch (IOException ex)
        {
            Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex)
        {
            Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
