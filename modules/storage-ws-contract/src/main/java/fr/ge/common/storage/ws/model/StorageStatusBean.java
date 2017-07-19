/**
 * 
 */
package fr.ge.common.storage.ws.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean containing information to store a file.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
@XmlRootElement(name = "storageStatus")
@XmlAccessorType(XmlAccessType.FIELD)
public class StorageStatusBean implements Serializable {

  /** Serial version uid. **/
  private static final long serialVersionUID = 1L;

  /** Storage original file name. **/
  private String name;

  /** Storage id. **/
  private String id;

  /** Storage file status. **/
  private String status;

  /** Storage path. **/
  private String path;

  /**
   * Accesseur sur l'attribut {@link #name}.
   *
   * @return String name
   */
  public String getName() {
    return name;
  }

  /**
   * Mutateur sur l'attribut {@link #name}.
   *
   * @param name
   *          la nouvelle valeur de l'attribut name
   */
  public void setName(String name) {
    this.name = name;
  }

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
   * Accesseur sur l'attribut {@link #status}.
   *
   * @return String status
   */
  public String getStatus() {
    return status;
  }

  /**
   * Mutateur sur l'attribut {@link #status}.
   *
   * @param status
   *          la nouvelle valeur de l'attribut status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Accesseur sur l'attribut {@link #path}.
   *
   * @return String path
   */
  public String getPath() {
    return path;
  }

  /**
   * Mutateur sur l'attribut {@link #path}.
   *
   * @param path
   *          la nouvelle valeur de l'attribut path
   */
  public void setPath(String path) {
    this.path = path;
  }

}
