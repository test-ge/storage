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
@XmlRootElement(name = "storageSend")
@XmlAccessorType(XmlAccessType.FIELD)
public class StorageSendBean implements Serializable {

  /** Serial version uid. **/
  private static final long serialVersionUID = 1L;

  /** Storage original file name. **/
  private String originalFileName;

  /** Storage file state. **/
  private String state;

  /** Storage uid reference. **/
  private String referenceId;

  /**
   * Accesseur sur l'attribut {@link #state}.
   *
   * @return StorageStatusEnum state
   */
  public String getState() {
    return state;
  }

  /**
   * Mutateur sur l'attribut {@link #state}.
   *
   * @param state
   *          la nouvelle valeur de l'attribut state
   */
  public void setState(String state) {
    this.state = state;
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
   * Accesseur sur l'attribut {@link #originalFileName}.
   *
   * @return String originalFileName
   */
  public String getOriginalFileName() {
    return originalFileName;
  }

  /**
   * Mutateur sur l'attribut {@link #originalFileName}.
   *
   * @param originalFileName
   *          la nouvelle valeur de l'attribut originalFileName
   */
  public void setOriginalFileName(String originalFileName) {
    this.originalFileName = originalFileName;
  }

}
