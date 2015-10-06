/*
 * Copyright (C) 2015 Hedgehog01
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
package com.jjlcollectors.util.filehandling;

import com.jjlcollectors.controllers.AddCoinController;
import com.jjlcollectors.util.log.MyLogger;
import com.jjlcollectors.util.prefrences.PrefrencesHandler;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * Class to handle loading and saving of files
 *
 * @author Hedgehog01
 */
public class FileHandleClass
{

    private static final String USER_DIR = "user.dir";
    private static final String IMAGE_DIRECTORY = "images";
    private static final String LOG_CLASS_NAME = "FileHandleClass: ";
    private static final String IMAGE_OUTPUT_EXTENSION = "jpg";
    

    /**
     * method to handle item image saving
     * @param buffImage the image to be saved
     * @param imageNum the image number
     * @param userUUID user UUID
     * @param collectionUUID collection UUID
     * @param itemUUID the item UUID
     * @return true if save was successful
     */
   public static boolean handleImageSaveing(BufferedImage buffImage, int imageNum, UUID userUUID, UUID collectionUUID, UUID itemUUID)
    {
        boolean imageSaved = false;
        if (System.getProperty(USER_DIR) == null || userUUID == null || collectionUUID == null || itemUUID == null)
        {
            MyLogger.log(Level.SEVERE, LOG_CLASS_NAME + "Could not save image due to missing info.");
            return imageSaved;
        } else
        {
            String userDir = System.getProperty(USER_DIR);
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "User Dir: {0}", userDir);
            String filePath = userDir + "/" + IMAGE_DIRECTORY + "/" + userUUID.toString() + "/" + collectionUUID.toString();
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Full path to save the image: {0}", userDir);
            String imageName = itemUUID.toString() + imageNum + "." + IMAGE_OUTPUT_EXTENSION;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Image name: {0}", imageName);

            File outPutFilePath = new File(filePath);
            if (!outPutFilePath.exists())
            {
                MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Folder path {0} did not exist, attempting to create", outPutFilePath.getPath());
                if (!outPutFilePath.mkdirs())
                {
                    MyLogger.log(Level.SEVERE, LOG_CLASS_NAME + "Could not create folder path {0} returning false", outPutFilePath.getPath());
                    return false;
                }
            }

            File imageFile = new File(outPutFilePath.getPath() + "/" + imageName);
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Full image path is: {0}", imageFile.getPath());

            try
            {
                MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Buffered image {0}", buffImage.getWidth());
                ImageIO.write(buffImage, IMAGE_OUTPUT_EXTENSION, imageFile);
                MyLogger.log(Level.INFO, LOG_CLASS_NAME + "image {0} was written to disk", imageFile.getPath());
                imageSaved = true;
            } catch (IOException ex)
            {
                imageSaved = false;
                MyLogger.log(Level.SEVERE, LOG_CLASS_NAME + "Image write failed {0}", ex);
            }

        }
        return imageSaved;
    }
   
    /*
     * method to handle loading of coin image including save of last folder preference
     */
   /**
    * method to handle loading of buffered image from File and save of last folder preference
    * @param coinImageFile the file to load the buffered image from
    * @return the buffered image
    */
    public static BufferedImage getBufferedImageFromFile(File coinImageFile)
    {
        //save folder path to prefrences
        BufferedImage bufferedImage = null;
        MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Attempting to get buffered image");
        try
        {
            bufferedImage = ImageIO.read(coinImageFile);

        } catch (IOException ex)
        {
            MyLogger.log(Level.SEVERE, null, ex);
        }

        String tempPath = coinImageFile.getPath();
        String stringFolderPath = tempPath.substring(0, tempPath.lastIndexOf("\\"));
        File folderPath = new File(stringFolderPath);
        MyLogger.log(Level.INFO, LOG_CLASS_NAME+"attempting to save folder path to prefrences");
        PrefrencesHandler.setFolderPath(folderPath);

        MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Folder path selected is: {0}", folderPath);
        
        return bufferedImage;
    }
    
    public static ArrayList<File> getItemImages(UUID userUUID, UUID collectionUUID, UUID itemUUID)
    {
        ArrayList<File> fileList = new ArrayList<>();
        MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Attempting to return the list of images");
        
        String userDir = System.getProperty(USER_DIR);
        MyLogger.log(Level.INFO, LOG_CLASS_NAME + "User Dir: {0}", userDir);
        String filePath = userDir + "/" + IMAGE_DIRECTORY + "/" + userUUID.toString() + "/" + collectionUUID.toString();
        MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Full path to save the image: {0}", userDir);
        
        return fileList;
    }
}
