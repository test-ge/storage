/**
 * 
 */
package fr.ge.common.storage.ws.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean representing a stored tray result.
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public class StoredMonth implements Serializable {

  /** Serial version uid. **/
  private static final long serialVersionUID = 1L;

  /** Current month **/
  private String month;

  /** List of storage uid. **/
  private List < String > storageId;

  public StoredMonth() {
    this.storageId = new ArrayList < String >();
  }

  /**
   * Accesseur sur l'attribut {@link #month}.
   *
   * @return String month
   */
  public String getMonth() {
    return month;
  }

  /**
   * Mutateur sur l'attribut {@link #month}.
   *
   * @param month
   *          la nouvelle valeur de l'attribut month
   */
  public void setMonth(String month) {
    this.month = month;
  }

  /**
   * Accesseur sur l'attribut {@link #storageId}.
   *
   * @return List<String> storageId
   */
  public List < String > getStorageId() {
    return storageId;
  }

  /**
   * Mutateur sur l'attribut {@link #storageId}.
   *
   * @param storageId
   *          la nouvelle valeur de l'attribut storageId
   */
  public void setStorageId(List < String > storageId) {
    this.storageId = storageId;
  }

  @Override
  public boolean equals(Object object) {
    if (((StoredMonth) object).month == null && ((StoredMonth) object).storageId == null) {
      return true;
    }
    if (((StoredMonth) object).month == null && ((StoredMonth) object).storageId.equals(this.storageId)) {
      return true;
    }
    if (((StoredMonth) object).month.equals(this.month) && ((StoredMonth) object).storageId == null) {
      return true;
    }
    if (((StoredMonth) object).month.equals(this.month) && ((StoredMonth) object).storageId.equals(this.storageId)) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = this.month == null ? 0 : this.month.hashCode();
    hashCode = hashCode + (this.storageId == null ? 0 : this.storageId.hashCode());
    return hashCode;
  }

}
