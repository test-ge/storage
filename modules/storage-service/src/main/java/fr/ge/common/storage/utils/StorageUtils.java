package fr.ge.common.storage.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import fr.ge.common.storage.constante.ICodeExceptionConstante;
import fr.ge.common.storage.constante.IStorageConstant;
import fr.ge.core.exception.TechniqueException;
import fr.ge.core.log.GestionnaireTrace;

/**
 * Utility class for Storage bubble.
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public final class StorageUtils {

  /** Functionnal logger. */
  private static final Logger LOGGER_FONC = GestionnaireTrace.getLoggerFonctionnel();

  private static final Pattern FILE_PATTERN = Pattern.compile(".*\\.([^.]+)");

  private static final SimpleDateFormat SDF_DDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");

  /**
   * 
   * Constructeur de la classe.
   *
   */
  private StorageUtils() {
    super();
  }

  /**
   * Read the file and calculate checksum with a specific algorithm.
   *
   * @param storageFile
   *          the file to read
   * @param algorithmType
   *          algorithm type (sha1 or md5)
   * @return the checksum using byte
   * @throws Exception
   *           if an I/O error occurs
   */
  public static byte[] createChecksum(final File storageFile, final String algorithmType) throws Exception {
    final MessageDigest digest = MessageDigest.getInstance(algorithmType);
    try (InputStream fis = new FileInputStream(storageFile)) {
      int n = 0;
      final byte[] buffer = new byte[8192];

      while (n != -1) {
        n = fis.read(buffer);
        if (n > 0) {
          digest.update(buffer, 0, n);
        }
      }
      return digest.digest();
    }
  }

  /**
   * Read the file and calculate checksum with a specific algorithm.
   * 
   * @param storageFile
   *          the file to read
   * @param algorithmType
   *          algorithm type (sha1 or md5)
   * @return the checksum using string
   * @throws Exception
   */
  public static String createChecksumToString(final File storageFile, final String algorithmType) throws Exception {
    return new HexBinaryAdapter().marshal(createChecksum(storageFile, algorithmType));
  }

  /**
   * Return storage directory from root directory
   * 
   * @param rootDirectory
   *          the root directory
   * @return The directory full path
   */
  private static StringBuilder getDirectory(final String statusDirectory) {
    final String currentYear = new SimpleDateFormat(IStorageConstant.YEAR_PATTERN).format(Calendar.getInstance().getTime());
    final String currentMonth = new SimpleDateFormat(IStorageConstant.MONTH_PATTERN).format(Calendar.getInstance().getTime());
    final StringBuilder pathStorage = new StringBuilder();
    pathStorage.append(File.separator).append(statusDirectory).append(File.separator).append(currentYear).append(File.separator)
      .append(currentMonth);
    return pathStorage;
  }

  /**
   * Create storage files into file system.
   * 
   * @param storageId
   *          The storage uid
   * @param storageFileByte
   *          Storage file byte array
   * @param storageTray
   *          The storage status
   * @param storageFileName
   *          The original file name
   * @param storageReferenceId
   *          The storage reference uid
   * @param storageRootDirectory
   *          Storage root directory
   * @return the relative storage path
   * @throws TechniqueException
   *           Technical Exception
   */
  public static String createStorageFile(final String storageId, final byte[] storageFileByte, final String storageTray,
    final String storageFileName, final String storageReferenceId, final String storageRootDirectory) throws TechniqueException {
    LOGGER_FONC.info("Creating storage file into file system");
    StringBuilder relativeStorageDirectory = null;

    try {

      final StringBuilder storageDirectory = new StringBuilder();
      storageDirectory.append(storageRootDirectory).append(File.separator);

      relativeStorageDirectory = getDirectory(storageTray);
      storageDirectory.append(relativeStorageDirectory);

      final File storageDir = new File(storageDirectory.toString());
      if (!storageDir.exists()) {
        storageDir.mkdirs();
      }
      // -->Create storage file
      final StringBuilder storageFilename = storageDirectory.append(File.separator).append(storageId);
      relativeStorageDirectory.append(File.separator).append(storageId);
      if (StringUtils.isNotEmpty(storageFileName)) {
        final Matcher matcher = FILE_PATTERN.matcher(storageFileName);
        if (matcher.matches()) {
          // -->Get the extension file
          storageFilename.append(IStorageConstant.POINT).append(matcher.group(1));
          relativeStorageDirectory.append(IStorageConstant.POINT).append(matcher.group(1));
        }
      }

      final File storageFile = new File(storageFilename.toString());
      Files.write(storageFile.toPath(), storageFileByte, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
        StandardOpenOption.WRITE);

      // -->Create metadata file
      createMetadataFile(storageId, storageTray, storageFileName, storageReferenceId, storageFile, storageRootDirectory);

      // -->Create SHA-1 and MD5 file
      createsha1md5Files(storageId, storageFile, storageRootDirectory);

    } catch (final IOException e) {
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DISK_ERROR,
        "Error when creating data file for id " + storageId, e);
    }
    return relativeStorageDirectory.toString();
  }

  /**
   * Create metadata files.
   * 
   * @param storageSendBean
   *          Storage send information
   * @param storageFile
   *          Storage file
   * @param rootDirectory
   *          Storage root directory
   * @param relativeStorageDirectory
   *          Storage relative directory
   * @throws TechniqueException
   *           Technical Exception
   */

  /**
   * 
   * @param storageId
   *          The storage uid
   * @param storageTray
   *          The storage status
   * @param storageFileName
   *          The original file name
   * @param storageReferenceId
   *          The storage reference uid
   * @param storageFile
   *          Storage file
   * @param rootDirectory
   *          Storage root directory
   * @throws TechniqueException
   *           Technical Exception
   */
  public static void createMetadataFile(final String storageId, final String storageTray, final String storageFileName,
    final String storageReferenceId, final File storageFile, final String rootDirectory) throws TechniqueException {
    LOGGER_FONC.info("Creating storage metadata file into file system");
    try {
      // -->Create metadata file
      final String metadataFilename = storageFile.getAbsolutePath() + IStorageConstant.TXT_EXTENSION;
      final File metadataFile = new File(metadataFilename);

      final StringBuilder metadataContent = buildMetadataContent(storageId, storageTray, storageFileName, storageReferenceId,
        storageFile, rootDirectory);

      Files.write(metadataFile.toPath(), metadataContent.toString().getBytes(), StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

    } catch (final Exception e) {
      LOGGER_FONC.error("Error when creating metadata file for id {}", storageId, e);
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DISK_ERROR,
        "Error when creating metadata file for id " + storageId, e);
    }
  }

  /**
   * Returns a StringBuilder containing metadata information
   * 
   * @param storageSendBean
   *          Storage send information
   * @param relativeStorageDirectory
   *          Storage relative directory
   * @return The metadata content
   */
  public static StringBuilder buildMetadataContent(final String storageId, final String storageTray, final String storageFileName,
    final String storageReferenceId, final File storageFile, final String rootDirectory) {
    final StringBuilder metadataContent = new StringBuilder();
    metadataContent.append(IStorageConstant.METADATA_ID).append(storageId).append(IStorageConstant.RETURN_LINE);
    if (StringUtils.isNotBlank(storageFileName)) {
      metadataContent.append(IStorageConstant.METADATA_ORIGINAL_NAME).append(storageFileName)
        .append(IStorageConstant.RETURN_LINE);
    }
    metadataContent.append(IStorageConstant.METADATA_STATUS).append(storageTray).append(IStorageConstant.RETURN_LINE);
    String relativePath = storageFile.getAbsolutePath().replace(rootDirectory, StringUtils.EMPTY);
    metadataContent.append(IStorageConstant.METADATA_PATH).append(relativePath).append(IStorageConstant.RETURN_LINE);
    metadataContent.append(IStorageConstant.METADATA_STORAGE_DATE).append(SDF_DDMMYYYY.format(Calendar.getInstance().getTime()))
      .append(IStorageConstant.RETURN_LINE);
    if (StringUtils.isNotBlank(storageReferenceId)) {
      metadataContent.append(IStorageConstant.METADATA_ID_REF).append(storageReferenceId);
    }
    return metadataContent;
  }

  /**
   * Create SHA-1 and MD5 files.
   * 
   * @param storageSendBean
   *          Storage send information
   * @param storageFile
   *          Storage file
   * @param rootDirectory
   *          Storage root directory
   * @throws TechniqueException
   *           Technical Exception
   */
  public static void createsha1md5Files(final String storageId, final File storageFile, final String rootDirectory)
    throws TechniqueException {
    LOGGER_FONC.info("Creating storage sha1 and md5 files into file system");
    try {
      // -->Create SHA-1 file
      final String sha1Filename = storageFile.getAbsolutePath() + IStorageConstant.SHA1_EXTENSION;
      final File sha1File = new File(sha1Filename.toString());
      Files.write(sha1File.toPath(),
        new HexBinaryAdapter().marshal(createChecksum(storageFile, IStorageConstant.SHA1)).getBytes(), StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

      // -->Create MD5 file
      final String md5Filename = storageFile.getAbsolutePath() + IStorageConstant.MD5_EXTENSION;
      final File md5File = new File(md5Filename);
      Files.write(md5File.toPath(), new HexBinaryAdapter().marshal(createChecksum(storageFile, IStorageConstant.MD5)).getBytes(),
        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    } catch (final Exception e) {
      LOGGER_FONC.error("Error when creating sha1/md5 files for id {}", storageId, e);
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DISK_ERROR,
        "Error when creating sha1/md5 file for id " + storageId, e);
    }
  }

  /**
   * Remove data, metadata, sha1 and md5 files into Storage
   * 
   * @param storageAbsoluteFilePath
   *          path to the store file
   */
  public static void remove(final String storageAbsoluteFilePath) throws TechniqueException {
    removeComplementFile(storageAbsoluteFilePath, IStorageConstant.SHA1_EXTENSION);
    removeComplementFile(storageAbsoluteFilePath, IStorageConstant.MD5_EXTENSION);
    removeComplementFile(storageAbsoluteFilePath, IStorageConstant.TXT_EXTENSION);

    final Path storagePath = Paths.get(storageAbsoluteFilePath);
    if (storagePath.toFile().exists()) {
      storagePath.toFile().delete();
      LOGGER_FONC.debug("Deleting file named {}  with success", storageAbsoluteFilePath);
    }
  }

  /**
   * Remove complements file.
   * 
   * @param storageAbsoluteFilePath
   *          Storage absiolute file path
   * @param extension
   *          Extension file to remove
   * @throws TechniqueException
   *           Technical exception
   */
  public static void removeComplementFile(final String storageAbsoluteFilePath, final String extension)
    throws TechniqueException {
    final String absoluteRemoveFilePath = storageAbsoluteFilePath + extension;
    final Path removeFilePath = Paths.get(absoluteRemoveFilePath);
    if (removeFilePath.toFile().exists()) {
      removeFilePath.toFile().delete();
      LOGGER_FONC.debug("Deleting file named {}  with success", storageAbsoluteFilePath);
    }
  }

  /**
   * Get byte array from Path.
   * 
   * @param path
   *          The path
   * @return A byte array
   * @throws IOException
   *           IO Exception
   */
  public static byte[] readHashFile(final Path path) throws TechniqueException {
    try (InputStream stream = new FileInputStream(path.toString())) {
      return transformInputStreamToByteArray(stream);
    } catch (final IOException ex) {
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DISK_ERROR, "Unable to read file " + path, ex);
    }
  }

  /**
   * Transform an InputStream into a byte array.
   * 
   * @param storageFile
   *          Storage file inputstream
   * @return a byte array
   * @throws TechniqueException
   *           Technical exception
   */
  public static byte[] transformInputStreamToByteArray(final InputStream storageFile) throws TechniqueException {
    byte[] storageFileByte;
    try {
      storageFileByte = IOUtils.toByteArray(storageFile);
    } catch (final Exception e) {
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_INVALID_FORMAT, "Error converting the file to byte array",
        e);
    }
    return storageFileByte;
  }
}
