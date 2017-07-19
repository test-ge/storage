/**
 * 
 */
package fr.ge.common.storage.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean containing error information.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
@XmlRootElement(name = "storageError")
@XmlAccessorType(XmlAccessType.FIELD)
public class StorageErrorBean {

  /** Storage error code. **/
  private String errorCode;

  /** Storage error message. **/
  private String errorMessage;

  /**
   * Accesseur sur l'attribut {@link #errorCode}.
   *
   * @return String errorCode
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * Mutateur sur l'attribut {@link #errorCode}.
   *
   * @param errorCode
   *          la nouvelle valeur de l'attribut errorCode
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * Accesseur sur l'attribut {@link #errorMessage}.
   *
   * @return String errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Mutateur sur l'attribut {@link #errorMessage}.
   *
   * @param errorMessage
   *          la nouvelle valeur de l'attribut errorMessage
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

}
