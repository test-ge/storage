package fr.ge.common.storage.utils;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ge.common.storage.constante.IStorageConstant;
import fr.ge.core.exception.TechniqueException;

/**
 * JUNIT for utility class for Storage bubble.
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public class StorageUtilsTest {

  /** String root directory. **/
  private static String ROOT_DIRECTORY = "target/data/storage";

  /** File root directory. **/
  private File rootdirectory = null;

  /**
   * Méthode exécutée en début de JUNIT.
   */
  @Before
  public void setUp() {
    this.rootdirectory = new File(ROOT_DIRECTORY);
    this.rootdirectory.mkdirs();
    new File(ROOT_DIRECTORY + File.separator + "inferno").mkdirs();
    new File(ROOT_DIRECTORY + File.separator + "error").mkdirs();
    new File(ROOT_DIRECTORY + File.separator + "error" + File.separator + "2017").mkdirs();
    new File(ROOT_DIRECTORY + File.separator + "error" + File.separator + "2017" + File.separator + "05").mkdirs();
  }

  /**
   * Testing MD5 checksum creation returning byte array.
   * 
   * @throws Exception
   */
  @Test
  public void createByteArrayChecksumMD5() throws Exception {
    File storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_MD5.zip");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    byte[] checksumMD5 = StorageUtils.createChecksum(storageFile, IStorageConstant.MD5);
    Assert.assertNotNull(checksumMD5);

    storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_MD5.pdf");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    checksumMD5 = StorageUtils.createChecksum(storageFile, IStorageConstant.MD5);
    Assert.assertNotNull(checksumMD5);

    storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_MD5.txt");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    checksumMD5 = StorageUtils.createChecksum(storageFile, IStorageConstant.MD5);
    Assert.assertNotNull(checksumMD5);
  }

  /**
   * Testing SHA-1 checksum creation returning byte array.
   * 
   * @throws Exception
   */
  @Test
  public void createByteArrayChecksumSHA1() throws Exception {
    File storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_SHA1.zip");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    byte[] checksumSHA1 = StorageUtils.createChecksum(storageFile, IStorageConstant.SHA1);
    Assert.assertNotNull(checksumSHA1);

    storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_SHA1.pdf");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    checksumSHA1 = StorageUtils.createChecksum(storageFile, IStorageConstant.SHA1);
    Assert.assertNotNull(checksumSHA1);

    storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_SHA1.txt");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    checksumSHA1 = StorageUtils.createChecksum(storageFile, IStorageConstant.SHA1);
    Assert.assertNotNull(checksumSHA1);
  }

  /**
   * Testing MD5 checksum creation returning string.
   * 
   * @throws Exception
   */
  @Test
  public void createStringChecksumMD5() throws Exception {
    File storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_MD5_str.zip");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    String checksumMD5 = StorageUtils.createChecksumToString(storageFile, IStorageConstant.MD5);
    Assert.assertNotNull(checksumMD5);

    storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_MD5_str.pdf");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    checksumMD5 = StorageUtils.createChecksumToString(storageFile, IStorageConstant.MD5);
    Assert.assertNotNull(checksumMD5);

    storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_MD5_str.txt");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    checksumMD5 = StorageUtils.createChecksumToString(storageFile, IStorageConstant.MD5);
    Assert.assertNotNull(checksumMD5);
  }

  /**
   * Testing SHA-1 checksum creation returning string.
   * 
   * @throws Exception
   */
  @Test
  public void createStringChecksumSHA1() throws Exception {
    File storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/testSHA1_str.zip");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    String checksumSHA1 = StorageUtils.createChecksumToString(storageFile, IStorageConstant.SHA1);
    Assert.assertNotNull(checksumSHA1);

    storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/testSHA1_str.pdf");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    checksumSHA1 = StorageUtils.createChecksumToString(storageFile, IStorageConstant.SHA1);
    Assert.assertNotNull(checksumSHA1);

    storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/testSHA1_str.txt");
    storageFile.createNewFile();
    Assert.assertEquals(storageFile.exists(), true);
    checksumSHA1 = StorageUtils.createChecksumToString(storageFile, IStorageConstant.SHA1);
    Assert.assertNotNull(checksumSHA1);
  }

  /**
   * Testing all storage files.
   * 
   * @throws TechniqueException
   * @throws IOException
   * @throws Exception
   */
  @Test
  public void createStorageFiles() throws TechniqueException, IOException {
    final String storageId = "2017-02-ABC-DEF-00";
    final File storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/storage.pdf");
    storageFile.createNewFile();
    final InputStream fileStorageStream = new FileInputStream(storageFile);
    final byte[] storageFileByte = IOUtils.toByteArray(fileStorageStream);
    final String storageTray = "error";
    final String storageFileName = "Fichier de test.pdf";

    StorageUtils.createStorageFile(storageId, storageFileByte, storageTray, storageFileName, null, ROOT_DIRECTORY);

    final String storageReferenceId = "2017-02-ABC-DEF-01";
    StorageUtils.createStorageFile(storageId, storageFileByte, storageTray, storageFileName, storageReferenceId, ROOT_DIRECTORY);
  }

  /**
   * Testing all storage files.
   * 
   * @throws TechniqueException
   * @throws IOException
   * @throws Exception
   */
  @Test
  public void createStorageFilesWithCreatingRootDir() throws TechniqueException, IOException {
    final String storageId = "2017-02-ABC-DEF-00";
    final File storageFile = new File(ROOT_DIRECTORY + File.separator + "storage.pdf");
    storageFile.createNewFile();
    final InputStream fileStorageStream = new FileInputStream(storageFile);
    final byte[] storageFileByte = IOUtils.toByteArray(fileStorageStream);
    final String storageTray = "error";
    final String storageFileName = "Fichier de test";

    final File errorDir = new File(ROOT_DIRECTORY + File.separator + "error" + File.separator + "2017" + File.separator + "05");
    Files.walkFileTree(errorDir.toPath(), new FileVisitor() {

      @Override
      public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        Path pathFileDelete = (Path) file;
        Files.delete(pathFileDelete);
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        Path pathDirDelete = (Path) dir;
        Files.delete(pathDirDelete);
        return FileVisitResult.CONTINUE;
      }
    });

    StorageUtils.createStorageFile(storageId, storageFileByte, storageTray, storageFileName, null, ROOT_DIRECTORY);

    final String storageReferenceId = "2017-02-ABC-DEF-01";
    StorageUtils.createStorageFile(storageId, storageFileByte, storageTray, storageFileName, storageReferenceId, ROOT_DIRECTORY);
  }

  /**
   * Testing all storage files.
   * 
   * @throws TechniqueException
   * @throws IOException
   * @throws Exception
   */
  @Test
  public void createStorageFilesInferno() throws TechniqueException, IOException {
    final String storageId = "2017-02-ABC-DEF-00";
    File storageFile = new File(ROOT_DIRECTORY + File.separator + "inferno/storage.pdf");
    storageFile.createNewFile();
    final InputStream fileStorageStream = new FileInputStream(storageFile);
    final byte[] storageFileByte = IOUtils.toByteArray(fileStorageStream);
    final String storageTray = "inferno";
    final String storageFileName = "Fichier de test.pdf";
    final String storageReferenceId = "2017-02-ABC-DEF-01";

    StorageUtils.createStorageFile(storageId, storageFileByte, storageTray, null, null, ROOT_DIRECTORY);

    StorageUtils.createStorageFile(storageId, storageFileByte, storageTray, storageFileName, storageReferenceId, ROOT_DIRECTORY);
  }

  /**
   * Testing reading hash file.
   * 
   * @throws TechniqueException
   * @throws IOException
   */
  @Test
  public void readHashFile() throws TechniqueException, IOException {
    File storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_read_hash.zip");
    storageFile.createNewFile();
    final Path path = Paths.get(storageFile.getAbsolutePath());
    byte[] readHashFile = StorageUtils.readHashFile(path);
    assertNotNull(readHashFile);
  }

  /**
   * Testing reading hash file.
   * 
   * @throws TechniqueException
   * @throws IOException
   */
  @Test(expected = TechniqueException.class)
  public void readHashFileWithException() throws TechniqueException, IOException {
    File storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_file_exception.zip");
    final Path path = Paths.get(storageFile.getAbsolutePath());
    byte[] readHashFile = StorageUtils.readHashFile(path);
    assertNotNull(readHashFile);
  }

  /**
   * Testing transforming inputStream to a byte array.
   * 
   * @throws TechniqueException
   * @throws IOException
   */
  @Test
  public void transformInputStreamToByteArray() throws TechniqueException, IOException {
    File storageFile = new File(ROOT_DIRECTORY + File.separator + "error/2017/05/test_byte_array.zip");
    storageFile.createNewFile();
    final InputStream is = new FileInputStream(storageFile);
    byte[] storageFileByte = StorageUtils.transformInputStreamToByteArray(is);
    assertNotNull(storageFileByte);
  }

  /**
   * Testing transform input stream to byte array failed.
   * 
   * @throws TechniqueException
   * @throws IOException
   */
  @Test(expected = TechniqueException.class)
  public void transformInputStreamToByteArrayFailed() throws TechniqueException, IOException {
    StorageUtils.transformInputStreamToByteArray(null);
  }

  /**
   * Testing removing data, metadata, sha1 and md5 files into Storage
   * 
   * @throws IOException
   * 
   */
  @Test
  public void remove() throws TechniqueException, IOException {
    final String storageAbsoluteFilePath = ROOT_DIRECTORY + File.separator + "error/2017/05/test_removing.zip";
    File storageFile = new File(storageAbsoluteFilePath);
    storageFile.createNewFile();

    storageFile = new File(storageAbsoluteFilePath + ".sha1");
    storageFile.createNewFile();

    storageFile = new File(storageAbsoluteFilePath + ".md5");
    storageFile.createNewFile();

    storageFile = new File(storageAbsoluteFilePath + ".txt");
    storageFile.createNewFile();

    StorageUtils.remove(storageAbsoluteFilePath);
  }

  /**
   * Testing create metadata file.
   * 
   * @throws TechniqueException
   * @throws IOException
   */
  @Test(expected = TechniqueException.class)
  public void createMetadataFileWithTechniqueException() throws TechniqueException, IOException {
    final String storageId = "2017-02-ABC-DEF-00";
    final File storageFile = null;
    final String storageTray = "inferno";
    final String storageFileName = "Fichier de test.pdf";
    final String storageReferenceId = "2017-02-ABC-DEF-01";

    StorageUtils.createMetadataFile(storageId, storageTray, storageFileName, storageReferenceId, storageFile, ROOT_DIRECTORY);
  }

  /**
   * Testing create SHA-1 and MD5 files.
   * 
   * @throws TechniqueException
   * @throws IOException
   */
  @Test(expected = TechniqueException.class)
  public void createsha1md5FilesWithTechniqueException() throws TechniqueException, IOException {
    final String storageId = "2017-02-ABC-DEF-00";
    final File storageFile = null;
    StorageUtils.createsha1md5Files(storageId, storageFile, ROOT_DIRECTORY);
  }

  /**
   * Testing remove complement files.
   * 
   * @throws TechniqueException
   * @throws IOException
   */
  @Test
  public void removeComplementFile() throws TechniqueException, IOException {
    String storageAbsoluteFilePath = ROOT_DIRECTORY + File.separator + "error/2017/05/test_removing.zip";
    File storageFile = new File(storageAbsoluteFilePath + IStorageConstant.MD5_EXTENSION);
    storageFile.createNewFile();
    StorageUtils.removeComplementFile(storageAbsoluteFilePath, IStorageConstant.MD5_EXTENSION);

    StorageUtils.removeComplementFile(storageAbsoluteFilePath, IStorageConstant.MD5_EXTENSION);
  }

}
