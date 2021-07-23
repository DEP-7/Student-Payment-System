/*
 * Copyright (c) Dhanushka Chandimal. All rights reserved.
 * Licensed under the MIT License. See License in the project root for license information.
 */

package util;

import java.io.*;
import java.util.ArrayList;

public class FileIO {

    public boolean writeDataToFile(ArrayList arrayList, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(arrayList);
            return true;

        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList readDataFromFile(ArrayList arrayList, File file) {

        if (!file.exists()) return null;

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream oos = new ObjectInputStream(fis)) {

            arrayList = (ArrayList) oos.readObject();
            return arrayList;

        } catch (IOException | ClassNotFoundException e) {

            if (e instanceof EOFException) {
                file.delete();
            } else {
                e.printStackTrace();
            }
            return null;
        }
    }
}
