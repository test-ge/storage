package fr.ge.common.storage.bean;

import java.util.Date;

/**
 * Class representing a stored file.
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public class StorageBean {

  /** Storage id. **/
  private String id;

  /** Storage original file name. **/
  private String originalfilename;

  /** Storage tray. **/
  private String tray;

  /** Storage relative path. **/
  private String path;

  /** Storage date. **/
  private Date dateCreation;

  /** Storage id reference. **/
  private String referenceId;

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
   * Accesseur sur l'attribut {@link #originalfilename}.
   *
   * @return String originalfilename
   */
  public String getOriginalfilename() {
    return originalfilename;
  }

  /**
   * Mutateur sur l'attribut {@link #originalfilename}.
   *
   * @param originalfilename
   *          la nouvelle valeur de l'attribut originalfilename
   */
  public void setOriginalfilename(String originalfilename) {
    this.originalfilename = originalfilename;
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

}
