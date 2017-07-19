package fr.ge.common.storage.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import fr.ge.common.storage.bean.StorageBean;
import fr.ge.common.storage.constante.ICodeExceptionConstante;
import fr.ge.common.storage.service.mapper.StorageMapper;
import fr.ge.core.exception.TechniqueException;

public class StorageServiceImplTest {

  /** The storage service. */
  @InjectMocks
  private StorageServiceImpl storageService;

  /** The storage mapper. */
  @Mock
  private StorageMapper storageMapper;

  /** The storage service to call Tracker. */
  @Mock
  private IStorageTrackerService storageTrackerService;

  /** String root directory. **/
  private static String ROOT_DIRECTORY = "target/data/storage";

  /** File root directory. **/
  private File rootdirectory = null;

  /**
   * Sets the up. // TODO refaire cette gestion des ressources de test => création de l'arborescence
   * cible oui, création à la volée des données de tests vide non!
   *
   * @throws Exception
   *           the exception
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.rootdirectory = new File(ROOT_DIRECTORY);
    this.rootdirectory.mkdirs();
    new File(ROOT_DIRECTORY + File.separator + "archived").mkdirs();
    new File(ROOT_DIRECTORY + File.separator + "archived" + File.separator + "2017").mkdirs();
    new File(ROOT_DIRECTORY + File.separator + "archived" + File.separator + "2017" + File.separator + "01").mkdirs();

    Mockito.doNothing().when(storageTrackerService).addMessage(Matchers.anyString(), Matchers.anyString(),
      Matchers.any(Object.class));

    Mockito.doNothing().when(storageTrackerService).linkReference(Matchers.anyString(), Matchers.anyString());
  }

  /**
   * JUNIT for storing file.
   * 
   * @throws TechniqueException
   *           Technical exception
   * @throws IOException
   */
  @Test
  public void storeFile() throws TechniqueException, IOException {
    final File fileZip = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test_store.zip");
    fileZip.createNewFile();
    Path fileLocation = Paths.get(ROOT_DIRECTORY + File.separator + "archived/2017/01/test_store.zip");
    byte[] storageFile = Files.readAllBytes(fileLocation);

    // final InputStream storageFile = new FileInputStream(fileZip);
    final String storageTray = "archived";
    final String storageFileName = "Fichier de test.zip";
    final String storageReferenceId = "test1";

    Mockito.when(storageTrackerService.createStorageId()).thenReturn("1");
    Mockito.doNothing().when(storageMapper).insertStorage(Matchers.any(StorageBean.class));

    StorageBean storedFile = storageService.storeFile(storageFile, storageTray, storageFileName, storageReferenceId,
      rootdirectory.getAbsolutePath());
    assertNotNull(storedFile);
    assertNotNull(storedFile.getId());
    assertNotNull(storedFile.getPath());
    assertNotNull(storedFile.getDateCreation());
    assertEquals(storageTray, storedFile.getTray());
  }

  /**
   * JUNIT for storing file with empty reference.
   * 
   * @throws TechniqueException
   *           Technical exception
   * @throws IOException
   */
  @Test
  public void storeFileWithEpmtyReference() throws TechniqueException, IOException {
    final File fileZip = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test_store.zip");
    fileZip.createNewFile();
    Path fileLocation = Paths.get(ROOT_DIRECTORY + File.separator + "archived/2017/01/test_store.zip");
    byte[] storageFile = Files.readAllBytes(fileLocation);

    final String storageTray = "archived";
    final String storageFileName = "Fichier de test.zip";
    final String storageReferenceId = "";

    Mockito.when(storageTrackerService.createStorageId()).thenReturn("1");
    Mockito.doNothing().when(storageMapper).insertStorage(Matchers.any(StorageBean.class));

    StorageBean storedFile = storageService.storeFile(storageFile, storageTray, storageFileName, storageReferenceId,
      rootdirectory.getAbsolutePath());
    assertNotNull(storedFile);
    assertNotNull(storedFile.getId());
    assertNotNull(storedFile.getPath());
    assertNotNull(storedFile.getDateCreation());
    assertEquals(storageTray, storedFile.getTray());

    storedFile = storageService.storeFile(storageFile, storageTray, storageFileName, null, rootdirectory.getAbsolutePath());
    assertNotNull(storedFile);
    assertNotNull(storedFile.getId());
    assertNotNull(storedFile.getPath());
    assertNotNull(storedFile.getDateCreation());
    assertEquals(storageTray, storedFile.getTray());
  }

  /**
   * JUNIT for storing file with exception.
   * 
   * @throws TechniqueException
   *           Technical exception
   * @throws IOException
   */
  @Test(expected = TechniqueException.class)
  public void storeFileWithNullPointerException() throws TechniqueException, IOException {
    final byte[] storageFile = null;
    final String storageTray = "archived";
    final String storageFileName = "Fichier de test.zip";
    final String storageReferenceId = "test1";

    storageService.storeFile(storageFile, storageTray, storageFileName, storageReferenceId, rootdirectory.getAbsolutePath());
    // TODO ajouter commentaire sur la raison du test
  }

  /**
   * JUNIT for storing file with exception.
   * 
   * @throws TechniqueException
   *           Technical exception
   * @throws IOException
   */
  @Test(expected = TechniqueException.class)
  public void storeFileWithException() throws TechniqueException, IOException {
    final File fileZip = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test_store3.zip");
    fileZip.createNewFile();
    Path fileLocation = Paths.get(fileZip.getAbsolutePath());
    byte[] storageFile = Files.readAllBytes(fileLocation);

    // final InputStream storageFile = new FileInputStream(fileZip);
    final String storageTray = "archived";
    final String storageFileName = "Fichier de test.zip";
    final String storageReferenceId = "test1";

    Mockito.doThrow(new NullPointerException()).when(storageMapper).insertStorage(Matchers.any(StorageBean.class));

    storageService.storeFile(storageFile, storageTray, storageFileName, storageReferenceId, rootdirectory.getAbsolutePath());
    // TODO ajouter commentaire sur la raison du test
  }

  /**
   * JUNIT for storing file with exception.
   * 
   * @throws TechniqueException
   *           Technical exception
   * @throws IOException
   */
  @Test(expected = TechniqueException.class)
  public void storeFileWithTrackUnavailable() throws TechniqueException, IOException {
    final File fileZip = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test_store3.zip");
    fileZip.createNewFile();
    Path fileLocation = Paths.get(fileZip.getAbsolutePath());
    byte[] storageFile = Files.readAllBytes(fileLocation);
    // final InputStream storageFile = new FileInputStream(fileZip);
    final String storageTray = "archived";
    final String storageFileName = "Fichier de test.zip";
    final String storageReferenceId = "test1";

    Mockito.when(storageTrackerService.createStorageId())
      .thenThrow(new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_TRACKER));

    storageService.storeFile(storageFile, storageTray, storageFileName, storageReferenceId, rootdirectory.getAbsolutePath());
    // TODO ajouter commentaire sur la raison du test
  }

  /**
   * JUNIT for getting storage file from db.
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Test
  public void getStorage() throws TechniqueException {
    String storageId = "2017-12-VHB-LKD-55";
    String storageTray = "archived";

    StorageBean storageBean = new StorageBean();
    storageBean.setId(storageId);
    storageBean.setOriginalfilename("string");
    storageBean.setPath("string");
    storageBean.setTray(storageTray);
    Mockito.when(storageMapper.getStorageByIdAndTray(Matchers.anyString(), Matchers.anyString())).thenReturn(storageBean);

    storageBean = storageService.get(storageId, storageTray);
    assertNotNull(storageBean);
    // TODO ajouter test sur le bean retour
  }

  /**
   * JUNIT for getting storage file with null storage id.
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Test(expected = TechniqueException.class)
  public void getStorageWithNullStorageId() throws TechniqueException {
    StorageBean storageBean = storageService.get(null, "archived");
    assertNotNull(storageBean);
  }

  /**
   * JUNIT for getting storage file with null storage tray.
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Test(expected = TechniqueException.class)
  public void getStorageWithNullStorageTray() throws TechniqueException {
    StorageBean storageBean = storageService.get("1", null);
    assertNotNull(storageBean);
  }

  /**
   * JUNIT for get storage file from db with null..
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Test(expected = TechniqueException.class)
  public void getStorageWithNull() throws TechniqueException {
    String storageId = "2017-12-VHB-LKD-55";
    String storageTray = "archived";
    Mockito.when(storageMapper.getStorageByIdAndTray(Matchers.anyString(), Matchers.anyString())).thenReturn(null);

    StorageBean storageBean = storageService.get(storageId, storageTray);
    assertNotNull(storageBean);
    // TODO ajouter commentaire sur la raison du test
  }

  /**
   * JUNIT for getting storage file from db with null..
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Test(expected = TechniqueException.class)
  public void getStorageWithNullPointerException() throws TechniqueException {
    String storageId = "2017-12-VHB-LKD-55";
    String storageTray = "archived";
    Mockito.when(storageMapper.getStorageByIdAndTray(Matchers.anyString(), Matchers.anyString()))
      .thenThrow(new NullPointerException());

    StorageBean storageBean = storageService.get(storageId, storageTray);
    assertNotNull(storageBean);
  }

  /**
   * JUNIT for removing storage file.
   * 
   * @throws TechniqueException
   *           Technical exception
   * @throws IOException
   */
  @Transactional(readOnly = false, value = "ge_storage")
  @Test
  public void remove() throws TechniqueException, IOException {
    final File fileZip = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test.zip");
    fileZip.createNewFile();
    final File fileSha1 = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test.zip.sha1");
    fileSha1.createNewFile();
    final File fileMd5 = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test.zip.md5");
    fileMd5.createNewFile();
    final File fileTxt = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test.zip.txt");
    fileTxt.createNewFile();

    String storageId = "2017-12-VHB-LKD-57";
    StorageBean storageBean = new StorageBean();
    storageBean.setId(storageId);
    storageBean.setOriginalfilename("test");
    storageBean.setPath("archived/2017/01/test.zip");
    storageBean.setTray("archived");

    Mockito.doNothing().when(storageMapper).insertStorage(Matchers.any(StorageBean.class));
    Mockito.doNothing().when(storageMapper).deleteStorageByIdAndTray(Matchers.anyString(), Matchers.anyString());
    Mockito.when(storageMapper.getStorageByIdAndTray(Matchers.anyString(), Matchers.anyString())).thenReturn(storageBean);

    Path fileLocation = Paths.get(fileZip.getAbsolutePath());
    byte[] storageFile = Files.readAllBytes(fileLocation);
    // final InputStream storageFile = new FileInputStream(fileZip);
    final String storageTray = "archived";
    final String storageFileName = "Fichier de test.zip";
    final String storageReferenceId = "test1";

    storageService.storeFile(storageFile, storageTray, storageFileName, storageReferenceId, rootdirectory.getAbsolutePath());

    storageBean = storageService.remove(storageId, storageTray, this.rootdirectory.getAbsolutePath());
    assertNotNull(storageBean);
    // TODO ajouter controle sur le bean
  }

  /**
   * JUNIT for getting all storage file from db.
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Transactional(readOnly = true, value = "ge_storage")
  @Test
  public void getAll() throws TechniqueException {
    final String storageTray = "archived";
    final String storageId = "2017-12-VHB-LKD-57";
    StorageBean storageBean = new StorageBean();
    storageBean.setId(storageId);
    storageBean.setOriginalfilename("test");
    storageBean.setPath("archived/2017/01/test.zip");
    storageBean.setTray(storageTray);

    List < StorageBean > storageList = new ArrayList < StorageBean >();
    storageList.add(storageBean);
    Mockito.when(storageMapper.getAll(Matchers.anyString())).thenReturn(storageList);

    storageList = storageService.getAll(storageTray);
    assertNotNull(storageList);
    assertEquals(1, storageList.size());
  }

  /**
   * JUNIT for getting a storage file with null storage tray.
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Test(expected = TechniqueException.class)
  public void getWithNullStorageTray() throws TechniqueException {
    StorageBean storageBean = storageService.get("1", null);
    assertNull(storageBean);
  }

  /**
   * JUNIT for getting a storage file with null storage id.
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Test(expected = TechniqueException.class)
  public void getWithNullStorageId() throws TechniqueException {
    StorageBean storageBean = storageService.get(null, "archived");
    assertNull(storageBean);
  }

  /**
   * JUNIT for getting all storage file with null storage tray.
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Test(expected = TechniqueException.class)
  public void getAllWithNullStorageTray() throws TechniqueException {
    storageService.getAll(null);
  }

  /**
   * JUNIT for getting all storage file with exception raised.
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Test(expected = TechniqueException.class)
  public void getAllWithException() throws TechniqueException {
    Mockito.when(storageMapper.getAll(Matchers.anyString())).thenThrow(new NullPointerException());
    storageService.getAll("archived");
  }

  /**
   * JUNIT for removing a storage file with null storage tray.
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Test(expected = TechniqueException.class)
  public void removeWithNullStorageTray() throws TechniqueException {
    StorageBean storageBean = storageService.remove("1", null, rootdirectory.getAbsolutePath());
    assertNull(storageBean);
  }

  /**
   * JUNIT for removing a storage file with null storage id.
   * 
   * @throws TechniqueException
   *           Technical exception
   */
  @Test(expected = TechniqueException.class)
  public void removeWithNullStorageId() throws TechniqueException {
    StorageBean storageBean = storageService.remove(null, "archived", rootdirectory.getAbsolutePath());
    assertNull(storageBean);
  }
}
