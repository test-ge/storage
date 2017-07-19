package fr.ge.common.storage.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import fr.ge.common.storage.facade.impl.StorageFacadeImpl;
import fr.ge.common.storage.ws.model.StoredFile;
import fr.ge.common.storage.ws.model.StoredTrayResult;
import fr.ge.common.storage.ws.model.StoredTrayTree;
import fr.ge.common.storage.ws.rest.IStorageRestService;
import fr.ge.core.exception.TechniqueException;

public class StorageFacadeImplTest {

  /** Storage facade */
  @InjectMocks
  private StorageFacadeImpl storageFacade;

  @Mock
  private IStorageRestService storageRestService;

  /**
   * Injection of mocks in controller.
   *
   * @throws Exception
   *           exception
   */
  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getTray() throws TechniqueException {
    StoredTrayResult storedTrayExpected = Mockito.mock(StoredTrayResult.class);
    Mockito.when(storageRestService.get(Matchers.anyString())).thenReturn(storedTrayExpected);
    StoredTrayResult storedTrayResult = storageFacade.getTray("archived");
    assertNotNull(storedTrayResult);
  }

  @Test(expected = TechniqueException.class)
  public void getTrayNull() throws TechniqueException {
    Mockito.when(storageRestService.get(Matchers.anyString())).thenReturn(null);
    StoredTrayResult storedTrayResult = storageFacade.getTray("error");
    assertNull(storedTrayResult);
  }

  @Test(expected = TechniqueException.class)
  public void getTrayException() throws TechniqueException {
    Mockito.when(storageRestService.get(Matchers.anyString())).thenThrow(new NullPointerException());
    storageFacade.getTray("inferno");
  }

  @Test
  public void download() throws TechniqueException {
    Response responseExpected = Mockito.mock(Response.class);
    Mockito.when(storageRestService.download(Matchers.anyString(), Matchers.anyString())).thenReturn(responseExpected);
    Response response = storageFacade.download("1", "archived");
    assertNotNull(response);
  }

  @Test(expected = TechniqueException.class)
  public void downloadNull() throws TechniqueException {
    Mockito.when(storageRestService.download(Matchers.anyString(), Matchers.anyString())).thenReturn(null);
    storageFacade.download("1", "error");
  }

  @Test(expected = TechniqueException.class)
  public void downloadException() throws TechniqueException {
    Mockito.when(storageRestService.download(Matchers.anyString(), Matchers.anyString())).thenThrow(new NullPointerException());
    storageFacade.download("1", "error");
  }

  @Test
  public void remove() throws TechniqueException {
    StoredFile storedFile = Mockito.mock(StoredFile.class);
    Mockito.when(storageRestService.remove(Matchers.anyString(), Matchers.anyString())).thenReturn(storedFile);
    storageFacade.remove("1", "archived");
  }

  @Test(expected = TechniqueException.class)
  public void removeNull() throws TechniqueException {
    Mockito.when(storageRestService.remove(Matchers.anyString(), Matchers.anyString())).thenReturn(null);
    storageFacade.remove("1", "error");
  }

  @Test(expected = TechniqueException.class)
  public void removeException() throws TechniqueException {
    Mockito.when(storageRestService.remove(Matchers.anyString(), Matchers.anyString())).thenThrow(new NullPointerException());
    storageFacade.remove("1", "error");
  }

  @Test
  public void generateTree() throws TechniqueException {
    StoredTrayResult storedTrayExpected = Mockito.mock(StoredTrayResult.class);
    Mockito.when(storageRestService.get(Matchers.anyString())).thenReturn(storedTrayExpected);
    StoredTrayTree storedTrayTreeResult = storageFacade.generateTree("error");
    assertNotNull(storedTrayTreeResult);
  }
}
