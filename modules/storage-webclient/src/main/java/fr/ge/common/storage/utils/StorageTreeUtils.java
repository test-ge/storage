/**
 * 
 */
package fr.ge.common.storage.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ge.common.storage.ws.model.StoredFile;
import fr.ge.common.storage.ws.model.StoredMonth;
import fr.ge.common.storage.ws.model.StoredTrayResult;
import fr.ge.common.storage.ws.model.StoredTrayTree;
import fr.ge.common.storage.ws.model.StoredYear;
import fr.ge.core.exception.TechniqueException;

/**
 * Utility tree class for storage-webclient.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public final class StorageTreeUtils {

  /**
   * 
   * Constructeur de la classe.
   *
   */
  private StorageTreeUtils() {

  }

  /**
   * Static method for tree generation.
   * 
   * @param storedTrayResult
   *          Input storage tray bean
   * @return A storage tray as a tree order by creation date
   * @throws TechniqueException
   *           Technical exception
   */
  public static StoredTrayTree generateTree(final StoredTrayResult storedTrayResult) throws TechniqueException {
    StoredTrayTree result = new StoredTrayTree();
    List < StoredYear > tree = generateListForTree(storedTrayResult);
    result.setTree(tree);
    result.setTotalResult(storedTrayResult.getStoredFiles().size());
    result.setTray(storedTrayResult.getTray());
    return result;
  }

  /**
   * Build a new list to display from input tray result.
   * 
   * @param storedTrayResult
   *          Input storage tray bean
   * @return a new generated list as a tree
   */
  private static List < StoredYear > generateListForTree(final StoredTrayResult storedTrayResult) {
    String currentYear = "N/A";
    String currentMonth = "N/A";
    StoredYear storedYear = null;
    StoredMonth storedMonth = null;
    List < StoredYear > storageList = new ArrayList < StoredYear >();

    for (StoredFile storedFile : storedTrayResult.getStoredFiles()) {
      final Date dateCreation = storedFile.getDateCreation();
      final String storageId = storedFile.getId();
      String year = new SimpleDateFormat("YYYY").format(dateCreation).toUpperCase();
      String month = new SimpleDateFormat("MM").format(dateCreation).toUpperCase();
      if (!year.equals(currentYear)) {
        currentYear = year;

        // -->Construction d'une nouvelle année
        storedYear = new StoredYear();
        storedYear.setYear(currentYear);

        // -->Changement de mois : construction d'un nouveau mois
        currentMonth = month;
        storedMonth = new StoredMonth();
        storedMonth.setMonth(currentMonth);

        storedMonth.getStorageId().add(storageId);
        storedYear.getStoredMonth().add(storedMonth);

        if (!storageList.contains(storedYear)) {
          storageList.add(storedYear);
        }
      } else {
        if (!month.equals(currentMonth)) {

          if (!storedYear.getStoredMonth().contains(storedMonth)) {
            storedYear.getStoredMonth().add(storedMonth);
          }
          // -->Changement de mois : construction d'un nouveau mois
          currentMonth = month;
          storedMonth = new StoredMonth();
          storedMonth.setMonth(currentMonth);
        }

        // -->Ajout du fichier dans la liste du mois en cours
        if (!storedMonth.getStorageId().contains(storageId)) {
          storedMonth.getStorageId().add(storageId);
        }

        if (!storedYear.getStoredMonth().contains(storedMonth)) {
          storedYear.getStoredMonth().add(storedMonth);

          if (!storageList.contains(storedYear)) {
            storageList.add(storedYear);
          }
        }
      }

    }
    return storageList;
  }
}
