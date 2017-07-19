package fr.ge.common.storage.ws.rest.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import fr.ge.common.storage.bean.StorageBean;
import fr.ge.common.storage.constante.ICodeExceptionConstante;
import fr.ge.common.storage.service.IStorageService;
import fr.ge.common.storage.service.impl.IStorageTrackerService;
import fr.ge.common.storage.ws.bean.conf.StorageConfigurationBean;
import fr.ge.common.storage.ws.model.StorageStatusBean;
import fr.ge.common.storage.ws.model.StoredFile;
import fr.ge.common.storage.ws.model.StoredTrayResult;
import fr.ge.core.exception.TechniqueException;

public class StorageRestServiceImplTest {

  /** Storage rest service. */
  @InjectMocks
  private StorageRestServiceImpl storageRestService;

  /** Storage service. */
  @Mock
  private IStorageService storageService;

  /** Storage service. */
  @Mock
  private IStorageTrackerService storageTrackerService;

  /** Storage configuration bean. */
  @Mock
  private StorageConfigurationBean storageConfigurationBean;

  /** String root directory. **/
  private static String ROOT_DIRECTORY = "target/data/storage";

  /** File root directory. **/
  private File rootdirectory = null;

  private Attachment storageFile = null;

  /**
   * Mock injection.
   */
  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.rootdirectory = new File(ROOT_DIRECTORY);
    this.rootdirectory.mkdirs();
    new File(ROOT_DIRECTORY + File.separator + "archived").mkdirs();
    new File(ROOT_DIRECTORY + File.separator + "archived" + File.separator + "2017").mkdirs();
    new File(ROOT_DIRECTORY + File.separator + "archived" + File.separator + "2017" + File.separator + "01").mkdirs();

    Mockito.when(storageConfigurationBean.getStorageRootDirectory()).thenReturn(rootdirectory.getAbsolutePath());
    List < String > status = Arrays.asList(new String[] {"archived", "error", "inferno" });
    Mockito.when(storageConfigurationBean.getStorageStatus()).thenReturn(status);
    Mockito.doNothing().when(storageTrackerService).addMessage(Matchers.anyString(), Matchers.anyString(),
      Matchers.any(Object.class));

    storageFile = Mockito.mock(Attachment.class);
    final byte[] resourceAsBytes = "Resource content".getBytes(StandardCharsets.UTF_8);
    Mockito.when(storageFile.getObject()).thenReturn(resourceAsBytes);
    Mockito.when(storageFile.getObject(byte[].class)).thenReturn(resourceAsBytes);

  }

  /**
   * Testing REST Service store.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void store() throws Exception {
    String storageFileName = "Fichier de test.zip";
    String storageReferenceId = "string";
    String storageStatus = "archived";

    Mockito.when(storageService.storeFile(Matchers.any(byte[].class), Matchers.anyString(), Matchers.anyString(),
      Matchers.anyString(), Matchers.anyString())).thenReturn(new StorageBean());

    Response response = storageRestService.store(storageStatus, storageFile, storageFileName, storageReferenceId);
    assertEquals(200, response.getStatus());
  }

  /**
   * Testing REST Service store.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void storeFromByteArray() throws Exception {
    String storageFileName = "Fichier de test.zip";
    String storageReferenceId = "string";
    String storageStatus = "archived";

    Mockito.when(storageService.storeFile(Matchers.any(byte[].class), Matchers.anyString(), Matchers.anyString(),
      Matchers.anyString(), Matchers.anyString())).thenReturn(new StorageBean());

    final Attachment storageFile = Mockito.mock(Attachment.class);
    final byte[] resourceAsBytes = "Resource content".getBytes(StandardCharsets.UTF_8);
    Mockito.when(storageFile.getObject(byte[].class)).thenReturn(resourceAsBytes);

    Response response = storageRestService.store(storageStatus, storageFile, storageFileName, storageReferenceId);
    assertEquals(200, response.getStatus());
  }

  /**
   * Testing REST Service store with exception.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void storeWithNullStoredFile() throws Exception {
    String storageFileName = "Fichier de test.zip";
    String storageReferenceId = "string";
    String storageStatus = "no_existing_status";

    Mockito.when(storageService.storeFile(Matchers.any(byte[].class), Matchers.anyString(), Matchers.anyString(),
      Matchers.anyString(), Matchers.anyString())).thenReturn(new StorageBean());

    Response response = storageRestService.store(storageStatus, storageFile, storageFileName, storageReferenceId);
    assertEquals(400, response.getStatus());
  }

  /**
   * Testing REST Service store with exception.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void storeWithTechniqueException() throws Exception {
    String storageFileName = "Fichier de test.zip";
    String storageReferenceId = "string";
    String storageStatus = "archived";

    Mockito.when(storageService.storeFile(Matchers.any(byte[].class), Matchers.anyString(), Matchers.anyString(),
      Matchers.anyString(), Matchers.anyString()))
      .thenThrow(new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DB_ERROR));

    Response response = storageRestService.store(storageStatus, storageFile, storageFileName, storageReferenceId);
    assertEquals(204, response.getStatus());
  }

  /**
   * Testing REST Service store with unknow status.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void storeWithStatusException() throws Exception {
    String storageFileName = "Fichier de test.zip";
    String storageReferenceId = "string";
    String storageStatus = "";

    Mockito.when(storageService.storeFile(Matchers.any(byte[].class), Matchers.anyString(), Matchers.anyString(),
      Matchers.anyString(), Matchers.anyString())).thenReturn(new StorageBean());

    Response response = storageRestService.store(storageStatus, storageFile, storageFileName, storageReferenceId);
    assertEquals(400, response.getStatus());
  }

  /**
   * Testing REST Service store with empty byte array.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void storeWithEmptyByteArray() throws Exception {
    String storageFileName = "Fichier de test.zip";
    String storageReferenceId = "string";
    String storageStatus = "archived";

    final Attachment storageFile = Mockito.mock(Attachment.class);
    Mockito.when(storageFile.getObject(byte[].class)).thenReturn(null);
    Mockito.when(storageFile.getObject(byte[].class)).thenReturn(null);

    Response response = storageRestService.store(storageStatus, storageFile, storageFileName, storageReferenceId);
    assertEquals(500, response.getStatus());
  }

  /**
   * Testing REST Service remove.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void remove() throws Exception {
    Mockito.when(storageService.remove(Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
      .thenReturn(new StorageBean());

    final String storageId = "storageId";
    final String storageTray = "archived";
    StoredFile storedFile = storageRestService.remove(storageId, storageTray);
    assertNotNull(storedFile);
  }

  /**
   * Testing REST Service remove with technical exception.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void removeWithTechniqueException() throws Exception {
    Mockito.when(storageService.remove(Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
      .thenThrow(new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DB_ERROR));

    final String storageId = "storageId";
    final String storageTray = "archived";
    StoredFile storedFile = storageRestService.remove(storageId, storageTray);
    assertNull(storedFile);
  }

  /**
   * Testing REST Service remove with unknown tray.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void removeWithUnknownTray() throws Exception {
    final String storageId = "storageId";
    final String storageTray = "unknown";

    StoredFile storedFile = storageRestService.remove(storageId, storageTray);
    assertNull(storedFile);
  }

  /**
   * Testing REST Service download.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void download() throws Exception {
    final File fileDownload = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test_download.zip");
    fileDownload.createNewFile();
    StorageBean storageBean = new StorageBean();
    final String storageId = "storageId";
    final String storageTray = "archived";
    storageBean.setId(storageId);
    storageBean.setPath("archived/2017/01/test_download.zip");

    Mockito.when(storageService.get(Matchers.anyString(), Matchers.anyString())).thenReturn(storageBean);

    Response response = storageRestService.download(storageId, storageTray);
    assertNotNull(response);
    assertEquals(200, response.getStatus());

    storageBean.setOriginalfilename("Fichier de test.zip");
    Mockito.when(storageService.get(Matchers.anyString(), Matchers.anyString())).thenReturn(storageBean);
    response = storageRestService.download(storageId, storageTray);
    assertNotNull(response);
    assertEquals(200, response.getStatus());
  }

  /**
   * Testing REST Service download with exception.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void downloadWithException() throws Exception {
    final File fileDownload = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test_download.zip");
    fileDownload.createNewFile();
    final String storageId = "storageId";
    final String storageTray = "archived";
    Mockito.when(storageService.get(Matchers.anyString(), Matchers.anyString())).thenThrow(new NullPointerException());

    Response response = storageRestService.download(storageId, storageTray);
    assertNotNull(response);
    assertEquals(204, response.getStatus());
  }

  /**
   * Testing REST Service download with exception.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void downloadWithTechniqueException() throws Exception {
    final File fileDownload = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test_download.zip");
    fileDownload.createNewFile();
    StorageStatusBean storageStatusBean = new StorageStatusBean();
    final String storageId = "storageId";
    final String storageTray = "archived";
    storageStatusBean.setId(storageId);
    storageStatusBean.setPath("archived/2017/01/test_download.zip");
    Mockito.when(storageService.get(Matchers.anyString(), Matchers.anyString()))
      .thenThrow(new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DB_ERROR));

    Response response = storageRestService.download(storageId, storageTray);
    assertNotNull(response);
    assertEquals(204, response.getStatus());

  }

  /**
   * Testing REST Service download with file not found exception.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void downloadWithFileNotFoundException() throws Exception {
    final File fileDownload = new File(ROOT_DIRECTORY + File.separator + "archived/2017/01/test_download.zip");
    fileDownload.createNewFile();
    final String storageId = "storageId";
    final String storageTray = "archived";
    StorageBean storageBean = new StorageBean();
    storageBean.setId(storageId);
    storageBean.setPath("archived/2017/01/test_download.zip");
    Mockito.when(storageConfigurationBean.getStorageRootDirectory()).thenReturn("test");
    Mockito.when(storageService.get(Matchers.anyString(), Matchers.anyString())).thenReturn(storageBean);

    Response response = storageRestService.download(storageId, storageTray);
    assertNotNull(response);
    assertEquals(204, response.getStatus());

  }

  /**
   * Testing REST Service to get all tray records.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void getAll() throws Exception {
    final String storageTray = "archived";
    final String storageId = "storageId";
    StorageBean storageBean = new StorageBean();
    storageBean.setId(storageId);
    List < StorageBean > listStorageBean = new ArrayList < StorageBean >();
    listStorageBean.add(storageBean);
    Mockito.when(storageService.getAll(Matchers.anyString())).thenReturn(listStorageBean);

    StoredTrayResult storedTrayResult = storageRestService.get(storageTray);
    assertNotNull(storedTrayResult);
    assertNotNull(storedTrayResult.getStoredFiles());
    assertTrue(!storedTrayResult.getStoredFiles().isEmpty());
  }

  /**
   * Testing REST Service to get all tray records.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void getWithUnknownTray() throws Exception {
    final String storageTray = "unknown";
    final String storageId = "storageId";
    StorageBean storageBean = new StorageBean();
    storageBean.setId(storageId);

    StoredTrayResult storedTrayResult = storageRestService.get(storageTray);
    assertNull(storedTrayResult);
  }

  /**
   * Testing REST Service to get all tray records.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void getWithTechniqueException() throws Exception {
    final String storageTray = "archived";
    final String storageId = "storageId";
    StorageBean storageBean = new StorageBean();
    storageBean.setId(storageId);
    Mockito.when(storageService.getAll(Matchers.anyString()))
      .thenThrow(new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DB_ERROR, "Failed db research"));

    StoredTrayResult storedTrayResult = storageRestService.get(storageTray);
    assertNull(storedTrayResult);
  }

  /**
   * Testing REST Service to get all tray records.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void getAllWithNullList() throws Exception {
    final String storageTray = "archived";
    Mockito.when(storageService.getAll(Matchers.anyString())).thenReturn(null);

    StoredTrayResult storedTrayResult = storageRestService.get(storageTray);
    assertNull(storedTrayResult);
  }

  /**
   * Testing REST Service download with unknown tray.
   *
   * @throws Exception
   *           Exception to be thrown
   */
  @Test
  public void downloadWithUnknownTray() throws Exception {
    final String storageId = "storageId";
    final String storageTray = "unknown";

    Response response = storageRestService.download(storageId, storageTray);
    assertNotNull(response);
    assertEquals(400, response.getStatus());
  }
}
