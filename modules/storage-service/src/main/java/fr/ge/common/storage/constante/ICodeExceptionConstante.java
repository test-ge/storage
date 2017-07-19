/**
 * 
 */
package fr.ge.common.storage.constante;

/**
 * Interface of all the code exception possible in ge-storage-service project.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public interface ICodeExceptionConstante {

  /** Error code exception for invalid JSON format. */
  String ERR_STORAGE_INVALID_PARAMETER = "ERR_STORAGE_INVALID_PARAMETER";

  /** Error code exception for invalid JSON format. **/
  String ERR_STORAGE_INVALID_FORMAT = "ERR_STORAGE_INVALID_FORMAT";

  /** Error code exception for invalid JSON format. */
  String ERR_STORAGE_INVALID_ID = "ERR_STORAGE_INVALID_ID";

  /** Error code exception for storage file into local file system. */
  String ERR_STORAGE_DISK_ERROR = "ERR_STORAGE_DISK_ERROR";

  /** Error code exception for registering postmail into database. */
  String ERR_STORAGE_DB_ERROR = "ERR_STORAGE_DB_ERROR";

  /** Error code exception for error when you call tracker for generation of ID. */
  String ERR_STORAGE_TRACKER = "ERR_STORAGE_TRACKER";

}
