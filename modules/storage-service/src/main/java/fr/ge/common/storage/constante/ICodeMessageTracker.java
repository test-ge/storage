/**
 * 
 */
package fr.ge.common.storage.constante;

/**
 * Constant grouping tracker messages for Storage.
 *
 * @author $Author: aolubi
 * @version $Revision: 0 $
 */
public interface ICodeMessageTracker {

  /** Tracker event linked to a storing file. */
  String STORAGE_FILE_POSTED = "tracker.storage.posted";

  /** Tracker event linked to a removing file. */
  String STORAGE_FILE_REMOVED = "tracker.storage.removed";

  /** Tracker event linked to a downloading file. */
  String STORAGE_FILE_DOWNLOAD = "tracker.storage.download";

  /** Tracker event linked to a removing file after purge action. */
  String STORAGE_FILE_PURGE = "tracker.storage.purge";

}
