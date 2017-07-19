/**
 * 
 */
package fr.ge.common.storage.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ge.common.storage.bean.MessageTrackerProvider;
import fr.ge.common.storage.constante.ICodeExceptionConstante;
import fr.ge.core.exception.TechniqueException;
import fr.ge.core.log.GestionnaireTrace;
import fr.ge.tracker.facade.ITrackerFacade;

/**
 * Implementation for services to call Tracker.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public class StorageTrackerServiceImpl implements IStorageTrackerService {

  /** Functionnal logger. */
  private static final Logger LOGGER_FONC = GestionnaireTrace.getLoggerFonctionnel();

  /** Technical logger. */
  private static final Logger LOGGER_TECH = GestionnaireTrace.getLoggerTechnique();

  /** Tracker facade. */
  @Autowired
  private ITrackerFacade trackerFacade;

  /** Message Provider. **/
  private MessageTrackerProvider messageTrackerProvider = MessageTrackerProvider.getInstance();

  /**
   * {@inheritDoc}
   * 
   */
  public String createStorageId() throws TechniqueException {
    try {
      final String trackId = trackerFacade.createUid();
      LOGGER_FONC.debug("Track uid generation {}", trackId);
      return trackId;
    } catch (Exception e) {
      String messageErrorTracker = "An error occured when calling Tracker uid generation";
      LOGGER_TECH.error(messageErrorTracker, e);
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_TRACKER, messageErrorTracker, e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void addMessage(final String storageId, final String eventCode, final Object... params) {
    String messageTracker = null;
    try {
      messageTracker = messageTrackerProvider.getMessage(eventCode, params);
      trackerFacade.post(storageId, messageTracker);
      LOGGER_FONC.debug("Calling TRACKER with success => Adding message [{}] for the storage uid {}", messageTracker, storageId);
    } catch (Exception e) {
      String messageErrorTracker = "An error occured when calling Tracker adding message method with storage id {}";
      LOGGER_TECH.warn(messageErrorTracker, storageId, e);
    }
  }

  @Override
  public void linkReference(final String storageId, final String referenceId) throws TechniqueException {
    try {
      trackerFacade.link(storageId, referenceId);
      LOGGER_FONC.debug("Calling TRACKER with success => Creating link with storage id {} and existing reference {}", storageId,
        referenceId);
    } catch (Exception e) {
      String messageErrorTracker = "An error occured when calling Tracker linking method with storage id {} and existing reference {}";
      LOGGER_TECH.error(messageErrorTracker, storageId, referenceId, e);
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_TRACKER, messageErrorTracker, e);
    }
  }
}
