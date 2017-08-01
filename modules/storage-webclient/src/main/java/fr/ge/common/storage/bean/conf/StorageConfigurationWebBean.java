/**
 * 
 */
package fr.ge.common.storage.bean.conf;

import java.io.Serializable;

/**
 * @author $Author: jzaire $
 * @version $Revision: 0 $
 */
public class StorageConfigurationWebBean implements Serializable {

  /** The serialVersionUID constant. */
  private static final long serialVersionUID = 1541769888327696943L;

  /** Static instance of the StorageConfigurationWeb bean. */
  private static StorageConfigurationWebBean instance = new StorageConfigurationWebBean();

  /** The URL for the feedback user. */
  private String urlFeedback;

  /** The host for the feedback user. */
  private String hostFeedback;

  /**
   * Retrieves the unique instance of the StorageConfigurationWebBean.
   *
   * @return the unique instance of the StorageConfigurationWebBean
   */
  public static StorageConfigurationWebBean getInstance() {
    return instance;
  }

  /**
   * Accesseur sur l'attribut {@link #urlFeedback}.
   *
   * @return String urlFeedback
   */
  public String getUrlFeedback() {
    return urlFeedback;
  }

  /**
   * Mutateur sur l'attribut {@link #urlFeedback}.
   *
   * @param urlFeedback
   *          la nouvelle valeur de l'attribut urlFeedback
   */
  public void setUrlFeedback(String urlFeedback) {
    this.urlFeedback = urlFeedback;
  }

  /**
   * Accesseur sur l'attribut {@link #hostFeedback}.
   *
   * @return String hostFeedback
   */
  public String getHostFeedback() {
    return hostFeedback;
  }

  /**
   * Mutateur sur l'attribut {@link #hostFeedback}.
   *
   * @param hostFeedback
   *          la nouvelle valeur de l'attribut hostFeedback
   */
  public void setHostFeedback(String hostFeedback) {
    this.hostFeedback = hostFeedback;
  }
}
