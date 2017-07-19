package fr.ge.common.storage.service.mapper;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.ge.common.storage.bean.StorageBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-storage-test-config.xml" })
@Ignore("Utiliser la JNDI")
public class StorageMapperTest {

  /** The storage mapper. */
  @Mock
  private StorageMapper storageMapper;

  /**
   * Setup.
   *
   * @throws Exception
   *           exception
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * Test inserting a record into db.
   */
  @Test
  public void testInsertStorage() {
    String trackId = "2017-02-GHI-JKM-00";
    String referenceId = "2017-02-GHI-JKM-01";

    StorageBean storageBean = new StorageBean();
    storageBean.setId(trackId);
    storageBean.setOriginalfilename("Fichier de test");
    storageBean.setReferenceId(referenceId);
    storageBean.setTray("archived");
    storageBean.setDateCreation(Calendar.getInstance().getTime());
    storageBean.setPath("/archived/2017/02/2017-02-GHI-JKM-00.zip");
    storageMapper.insertStorage(storageBean);
  }

  /**
   * Test deleting a record from db.
   */
  @Test
  public void testDeleteStorage() {
    String storageId = "2017-02-GHI-JKM-01";
    String referenceId = "2017-02-GHI-JKM-01";
    String storageTray = "archived";

    StorageBean storageBean = new StorageBean();
    storageBean.setId(storageId);
    storageBean.setOriginalfilename("Fichier de test.zip");
    storageBean.setReferenceId(referenceId);
    storageBean.setTray(storageTray);
    storageBean.setDateCreation(Calendar.getInstance().getTime());
    storageBean.setPath("/archived/2017/02/2017-02-GHI-JKM-01.zip");
    storageMapper.insertStorage(storageBean);

    storageBean = storageMapper.getStorageByIdAndTray(storageId, storageTray);
    assertNotNull(storageBean);

    storageMapper.deleteStorageByIdAndTray(storageId, storageTray);
  }

  /**
   * Test getting all records from db.
   */
  @Test
  public void testGetAll() {
    final String storageTray = "archived";
    List < StorageBean > storageList = storageMapper.getAll(storageTray);
    assertNotNull(storageList);
  }
}
