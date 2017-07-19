/**
 * 
 */
package fr.ge.common.storage.ws.bean.conf;

import java.io.Serializable;
import java.util.List;

/**
 * Class StorageConfigurationBean.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public class StorageConfigurationBean implements Serializable {

  /** UID. */
  private static final long serialVersionUID = -1323454463640492145L;

  /** root directory storage. */
  private String storageRootDirectory;

  /** List of storage status. **/
  private List < String > storageStatus;

  /** Instance statique du bean de configuration. */
  private static StorageConfigurationBean instance = new StorageConfigurationBean();

  /**
   * Récupère l'instance unique de StorageConfigurationBean.
   *
   * @return unique instance de StorageConfigurationBean
   */
  public static StorageConfigurationBean getInstance() {
    return instance;
  }

  /**
   * Accesseur sur l'attribut {@link #storageRootDirectory}.
   *
   * @return String storageRootDirectory
   */
  public String getStorageRootDirectory() {
    return storageRootDirectory;
  }

  /**
   * Mutateur sur l'attribut {@link #storageRootDirectory}.
   *
   * @param storageRootDirectory
   *          la nouvelle valeur de l'attribut storageRootDirectory
   */
  public void setStorageRootDirectory(String storageRootDirectory) {
    this.storageRootDirectory = storageRootDirectory;
  }

  /**
   * Accesseur sur l'attribut {@link #storageStatus}.
   *
   * @return List<String> storageStatus
   */
  public List < String > getStorageStatus() {
    return storageStatus;
  }

  /**
   * Mutateur sur l'attribut {@link #storageStatus}.
   *
   * @param storageStatus
   *          la nouvelle valeur de l'attribut storageStatus
   */
  public void setStorageStatus(List < String > storageStatus) {
    this.storageStatus = storageStatus;
  }

}
