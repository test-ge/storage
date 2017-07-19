/**
 * 
 */
package fr.ge.common.storage.ws.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean representing a stored file.
 *
 */
// TODO virer les annotations XML sur les autres beans
// TODO virer les beans inutiles
// TODO transformer les Responses en Bean
public class StoredFile implements Serializable {

  /** Serial version uid. **/
  private static final long serialVersionUID = 1L;

  /** Stored file's original filename. **/
  private String name;

  /** Stored file's id. **/
  private String id;

  /** Stored file's tray. **/
  private String tray;

  /** Stored file's relative path. **/
  private String path;

  /** Stored file's reference id. This is meant to be used as a remote id for this file's id. **/
  private String referenceId;

  /** Stored file creation date. **/
  private Date dateCreation;

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

  /**
   * Accesseur sur l'attribut {@link #referenceId}.
   *
   * @return String referenceId
   */
  public String getReferenceId() {
    return referenceId;
  }

  /**
   * Mutateur sur l'attribut {@link #referenceId}.
   *
   * @param referenceId
   *          la nouvelle valeur de l'attribut referenceId
   */
  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  /**
   * Accesseur sur l'attribut {@link #dateCreation}.
   *
   * @return Date dateCreation
   */
  public Date getDateCreation() {
    return dateCreation;
  }

  /**
   * Mutateur sur l'attribut {@link #dateCreation}.
   *
   * @param dateCreation 
   *          la nouvelle valeur de l'attribut dateCreation
   */
  public void setDateCreation(Date dateCreation) {
    this.dateCreation = dateCreation;
  }

}
