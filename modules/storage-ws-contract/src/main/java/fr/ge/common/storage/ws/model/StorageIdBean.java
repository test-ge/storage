/**
 * 
 */
package fr.ge.common.storage.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean containing information resulting after storing a file.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
@XmlRootElement(name = "storageId")
@XmlAccessorType(XmlAccessType.FIELD)
public class StorageIdBean {

  /** Storage id. **/
  private String id;

  /** Storage original file name. **/
  private String storagePath;

  /**
   * Accesseur sur l'attribut {@link #id}.
   *
   * @return String id
   */
  public String getId() {
    return id;
  }

  /**
   * Mutateur sur l'attribut {@link #id}.
   *
   * @param id
   *          la nouvelle valeur de l'attribut id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Accesseur sur l'attribut {@link #storagePath}.
   *
   * @return String storagePath
   */
  public String getStoragePath() {
    return storagePath;
  }

  /**
   * Mutateur sur l'attribut {@link #storagePath}.
   *
   * @param storagePath
   *          la nouvelle valeur de l'attribut storagePath
   */
  public void setStoragePath(String storagePath) {
    this.storagePath = storagePath;
  }
}
