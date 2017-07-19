/**
 * 
 */
package fr.ge.common.storage.bean;

import fr.ge.core.message.AbstractMessageProvider;

/**
 * Fournit les messages liées au tracker.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public class MessageTrackerProvider extends AbstractMessageProvider {

  /** instance. */
  private static MessageTrackerProvider instance = new MessageTrackerProvider();

  /**
   * Récupère l'instance unique de MessageTrackerProvider.
   *
   * @return unique instance de MessageTrackerProvider
   */
  public static MessageTrackerProvider getInstance() {
    return instance;
  }

}
