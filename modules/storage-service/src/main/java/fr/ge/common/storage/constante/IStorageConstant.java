/**
 * 
 */
package fr.ge.common.storage.constante;

/**
 * Constants for Storage.
 * 
 * @author $Author: aolubi
 * @version $Revision: 0 $
 */
public interface IStorageConstant {

  /** THE texte extension of record. */
  String TXT_EXTENSION = ".txt";

  /** THE SHA1 extension of record. */
  String SHA1_EXTENSION = ".sha1";

  /** THE MD5 extension of record. */
  String MD5_EXTENSION = ".md5";

  /** THE SHA1 algorithm of record. */
  String SHA1 = "sha1";

  /** THE MD5 algorithm of record. */
  String MD5 = "md5";

  /** THE metadata id name. */
  String METADATA_ID = "ID=";

  /** THE metadata original name. */
  String METADATA_ORIGINAL_NAME = "ORIGINALNAME=";

  /** THE metadata status name. */
  String METADATA_STATUS = "STATUS=";

  /** THE metadata storage relative path. */
  String METADATA_PATH = "PATH=";

  /** THE metadata storage date name. */
  String METADATA_STORAGE_DATE = "STORAGE_DATE=";

  /** THE metadata id reference. */
  String METADATA_ID_REF = "ID_REF=";

  /** THE return line. */
  String RETURN_LINE = "\n";

  /** THE point string. */
  String POINT = ".";

  /** Storage year pattern. */
  String YEAR_PATTERN = "yyyy";

  /** Storage year pattern. */
  String MONTH_PATTERN = "MM";

  /** Storage tracker date pattern. */
  String TRACKER_DATE_PATTERN = "dd/MM/yyyy";
}
