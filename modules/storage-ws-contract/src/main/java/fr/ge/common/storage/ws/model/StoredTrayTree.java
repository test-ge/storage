/**
 * 
 */
package fr.ge.common.storage.ws.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean representing a stored tray tree.
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public class StoredTrayTree implements Serializable {

  /** Serial version uid. **/
  private static final long serialVersionUID = 1L;

  /** List of storage files order by creation date. **/
  private List < StoredYear > tree;

  /** Total result. **/
  private int totalResult;

  /** Tray name. **/
  private String tray;

  /**
   * 
   * Constructeur de la classe.
   *
   * @param tray
   */
  public StoredTrayTree() {
    this.setTotalResult(0);
    this.setTree(new ArrayList < StoredYear >());
  }

  /**
   * Accesseur sur l'attribut {@link #tree}.
   *
   * @return List<StoredYear> tree
   */
  public List < StoredYear > getTree() {
    return tree;
  }

  /**
   * Mutateur sur l'attribut {@link #tree}.
   *
   * @param tree
   *          la nouvelle valeur de l'attribut tree
   */
  public void setTree(List < StoredYear > tree) {
    this.tree = tree;
  }

  /**
   * Accesseur sur l'attribut {@link #totalResult}.
   *
   * @return int totalResult
   */
  public int getTotalResult() {
    return totalResult;
  }

  /**
   * Mutateur sur l'attribut {@link #totalResult}.
   *
   * @param totalResult
   *          la nouvelle valeur de l'attribut totalResult
   */
  public void setTotalResult(int totalResult) {
    this.totalResult = totalResult;
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
