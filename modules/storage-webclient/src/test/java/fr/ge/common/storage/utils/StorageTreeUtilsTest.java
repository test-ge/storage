package fr.ge.common.storage.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import fr.ge.common.storage.ws.model.StoredFile;
import fr.ge.common.storage.ws.model.StoredTrayResult;
import fr.ge.common.storage.ws.model.StoredTrayTree;

public class StorageTreeUtilsTest {

  /**
   * Testing generateTree method.
   * 
   * @throws Exception
   */
  @Test
  public void generateTree() throws Exception {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_MONTH, 1);

    final String tray = "archived";
    String uid = "test1";
    String storageFileName = uid + ".zip";

    StoredTrayResult storedTrayResult = new StoredTrayResult();
    storedTrayResult.setTray(tray);
    storedTrayResult.setStoredFiles(new ArrayList < StoredFile >());

    StoredFile storedFile = new StoredFile();
    storedFile.setDateCreation(calendar.getTime());
    storedFile.setId(uid);
    String path = "/" + tray + "/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + storageFileName;
    storedFile.setPath(path);
    storedTrayResult.getStoredFiles().add(storedFile);

    calendar.add(Calendar.MONTH, 3);
    storedFile = new StoredFile();
    storedFile.setDateCreation(calendar.getTime());
    storedFile.setId(uid);
    path = "/" + tray + "/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + storageFileName;
    storedFile.setPath(path);
    storedTrayResult.getStoredFiles().add(storedFile);

    storedFile = new StoredFile();
    storedFile.setDateCreation(calendar.getTime());
    storedFile.setId(uid);
    path = "/" + tray + "/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + storageFileName;
    storedFile.setPath(path);
    storedTrayResult.getStoredFiles().add(storedFile);

    calendar.add(Calendar.YEAR, 3);
    storedFile = new StoredFile();
    storedFile.setDateCreation(calendar.getTime());
    storedFile.setId(uid);
    path = "/" + tray + "/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + storageFileName;
    storedFile.setPath(path);
    storedTrayResult.getStoredFiles().add(storedFile);

    calendar.add(Calendar.YEAR, -5);
    storedFile = new StoredFile();
    storedFile.setDateCreation(calendar.getTime());
    storedFile.setId(uid);
    path = "/" + tray + "/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + storageFileName;
    storedFile.setPath(path);
    storedTrayResult.getStoredFiles().add(storedFile);

    StoredTrayTree storedTrayTree = StorageTreeUtils.generateTree(storedTrayResult);
    assertNotNull(storedTrayTree);
    assertNotNull(storedTrayTree.getTree());
    assertEquals(5, storedTrayTree.getTotalResult());
  }
}
