/**
 * 
 */
package fr.ge.common.storage.ws.model;

/**
 * Enumeration of all storage tray enum.
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public enum StorageTrayEnum {

  /** Archived state. */
  ARCHIVED("archived"),

  /** Error state. */
  ERROR("error"),

  /** Inferno state. */
  INFERNO("inferno");

  /** the name of the criteria. */
  private String name;

  /**
   * Constructor.
   *
   * @param typeActionName
   *          type action name
   */
  StorageTrayEnum(final String name) {
    this.name = name;
  }

  /**
   * Accessor on {@link #name}.
   *
   * @return String name
   */
  public String getName() {
    return this.name;
  }

}
