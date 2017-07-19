/**
 * 
 */
package fr.ge.common.storage.ws.model;

import java.io.Serializable;
import java.util.List;

/**
 * Bean representing a stored tray result.
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public class StoredTrayResult implements Serializable {

  /** Serial version uid. **/
  private static final long serialVersionUID = 1L;

  /** Stored file's original filename. **/
  private List < StoredFile > storedFiles;

  /** Tray name. **/
  private String tray;

  /**
   * Accesseur sur l'attribut {@link #storedFiles}.
   *
   * @return List<StoredFile> storedFiles
   */
  public List < StoredFile > getStoredFiles() {
    return storedFiles;
  }

  /**
   * Mutateur sur l'attribut {@link #storedFiles}.
   *
   * @param storedFiles
   *          la nouvelle valeur de l'attribut storedFiles
   */
  public void setStoredFiles(List < StoredFile > storedFiles) {
    this.storedFiles = storedFiles;
  }

  /**
   * Accesseur sur l'attribut {@link #tray}.
   *
   * @return String tray
   */
  public String getTray() {
    return tray;
  }

  /**
   * Mutateur sur l'attribut {@link #tray}.
   *
   * @param tray
   *          la nouvelle valeur de l'attribut tray
   */
  public void setTray(String tray) {
    this.tray = tray;
  }

}
