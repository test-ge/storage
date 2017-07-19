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
public class StoredYear implements Serializable {

  /** Serial version uid. **/
  private static final long serialVersionUID = 1L;

  /** Current year of tree. **/
  private String year;

  /** List of storage files per month. **/
  private List < StoredMonth > storedMonth;

  /**
   * 
   * Constructeur de la classe.
   *
   */
  public StoredYear() {
    this.storedMonth = new ArrayList < StoredMonth >();
  }

  /**
   * Accesseur sur l'attribut {@link #year}.
   *
   * @return String year
   */
  public String getYear() {
    return year;
  }

  /**
   * Mutateur sur l'attribut {@link #year}.
   *
   * @param year
   *          la nouvelle valeur de l'attribut year
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * Accesseur sur l'attribut {@link #storedMonth}.
   *
   * @return List<StoredMonth> storedMonth
   */
  public List < StoredMonth > getStoredMonth() {
    return storedMonth;
  }

  /**
   * Mutateur sur l'attribut {@link #storedMonth}.
   *
   * @param storedMonth
   *          la nouvelle valeur de l'attribut storedMonth
   */
  public void setStoredMonth(List < StoredMonth > storedMonth) {
    this.storedMonth = storedMonth;
  }

  @Override
  public boolean equals(Object object) {
    if (((StoredYear) object).year == null && ((StoredYear) object).storedMonth == null) {
      return true;
    }
    if (((StoredYear) object).year == null && ((StoredYear) object).storedMonth.equals(this.storedMonth)) {
      return true;
    }
    if (((StoredYear) object).year.equals(this.year) && ((StoredYear) object).storedMonth == null) {
      return true;
    }
    if (((StoredYear) object).year.equals(this.year) && ((StoredYear) object).storedMonth.equals(this.storedMonth)) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = this.year == null ? 0 : this.year.hashCode();
    hashCode = hashCode + (this.storedMonth == null ? 0 : this.storedMonth.hashCode());
    return hashCode;
  }

}
