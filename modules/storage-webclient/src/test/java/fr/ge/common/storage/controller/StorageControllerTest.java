package fr.ge.common.storage.controller;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import fr.ge.common.storage.bean.conf.StorageConfigurationWebBean;
import fr.ge.common.storage.constant.ICodeExceptionStorageConstant;
import fr.ge.common.storage.facade.IStorageFacade;
import fr.ge.common.storage.ws.model.StoredTrayTree;
import fr.ge.core.exception.TechniqueException;

/**
 * Class test for Storage controller.
 */
public class StorageControllerTest {

  /** dashboard controller. */
  @InjectMocks
  private StorageController dashboardController;

  /** Storage facade. **/
  @Mock
  private IStorageFacade storageFacade;

  /** storage configuration web bean. */
  @Mock
  private StorageConfigurationWebBean storageConfigurationWebBean = Mockito.mock(StorageConfigurationWebBean.class);

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

  /**
   * Test display home page.
   *
   * @throws Exception
   *           exception
   */
  @Test
  public void testDisplayHome() throws Exception {
    String actual = dashboardController.displayHome();
    assertEquals(actual, "redirect:/home");
  }

  /**
   * Test display home page.
   *
   * @throws Exception
   *           exception
   */
  @Test
  public void testDisplayHomePage() throws Exception {
    Model model = Mockito.mock(Model.class);
    HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
    Locale locale = Locale.FRENCH;

    StoredTrayTree storedTrayTree = Mockito.mock(StoredTrayTree.class);
    Mockito.when(storageFacade.generateTree(Matchers.anyString())).thenReturn(storedTrayTree);
    Mockito.when(storageConfigurationWebBean.getUrlFeedback()).thenReturn("http://nash.int.guichet-entreprises.fr");
    String actual = dashboardController.displayHomePage(model, httpRequest, locale);
    assertEquals(actual, "home");
  }

  /**
   * Test display home page.
   *
   * @throws Exception
   *           exception
   */
  @Test
  public void testDisplayHomePageWithNull() throws Exception {
    Model model = Mockito.mock(Model.class);
    HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
    Locale locale = Locale.FRENCH;

    Mockito.when(storageFacade.generateTree(Matchers.anyString())).thenReturn(null);
    Mockito.when(storageConfigurationWebBean.getUrlFeedback()).thenReturn("http://nash.int.guichet-entreprises.fr");
    String actual = dashboardController.displayHomePage(model, httpRequest, locale);
    assertEquals(actual, "home");
  }

  /**
   * Test display home page.
   *
   * @throws Exception
   *           exception
   */
  @Test
  public void testDisplayHomePageException() throws Exception {
    Model model = Mockito.mock(Model.class);
    HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
    Locale locale = Locale.FRENCH;

    // StoredTrayTree storedTrayTree = new StoredTrayTree();
    Mockito.when(storageFacade.generateTree(Matchers.anyString()))
      .thenThrow(new TechniqueException(ICodeExceptionStorageConstant.ERR_STORAGE_WS_GET_TRAY));

    Mockito.when(storageConfigurationWebBean.getUrlFeedback()).thenReturn("http://nash.int.guichet-entreprises.fr");
    String actual = dashboardController.displayHomePage(model, httpRequest, locale);
    assertEquals(actual, "home");
  }

  /**
   * Test download file.
   *
   * @throws Exception
   *           exception
   */
  @Test
  public void testDownload() throws Exception {
    Model model = Mockito.mock(Model.class);
    HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);

    final String uid = "2017-02-ABC-DEF-00";
    final String tray = "archived";

    Response response = Mockito.mock(Response.class);
    // InputStream inputStream = Mockito.mock(InputStream.class);
    InputStream inputStream = new FileInputStream(
      StorageControllerTest.class.getResource("/").getFile() + "/datas/2017-02-ABC-DEF-00.zip");
    ServletOutputStream servletOutputStream = Mockito.mock(ServletOutputStream.class);

    Mockito.when(storageFacade.download(Matchers.anyString(), Matchers.anyString())).thenReturn(response);
    Mockito.when(response.getEntity()).thenReturn(inputStream);
    Mockito.when(httpResponse.getOutputStream()).thenReturn(servletOutputStream);
    Mockito.doNothing().when(httpResponse).flushBuffer();

    dashboardController.downloadFile(model, httpRequest, httpResponse, uid, tray);
  }

  /**
   * Test download file.
   *
   * @throws Exception
   *           exception
   */
  @Test
  public void testDelete() throws Exception {
    Model model = Mockito.mock(Model.class);
    HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);

    final String uid = "2017-02-ABC-DEF-00";
    final String tray = "archived";
    Mockito.doNothing().when(storageFacade).remove(Matchers.anyString(), Matchers.anyString());
    dashboardController.deleteFile(model, httpRequest, httpResponse, uid, tray);
  }

  /**
   * Test download file.
   * 
   * @throws TechniqueException
   *
   * @throws Exception
   *           exception
   */
  @Test
  public void testDeleteWithTechnicalException() throws TechniqueException {
    Model model = Mockito.mock(Model.class);
    HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);

    final String uid = "2017-02-ABC-DEF-00";
    final String tray = "archived";
    Mockito.doThrow(new TechniqueException(ICodeExceptionStorageConstant.ERR_STORAGE_WS_REMOVE_FILE)).when(storageFacade)
      .remove(Matchers.anyString(), Matchers.anyString());
    dashboardController.deleteFile(model, httpRequest, httpResponse, uid, tray);
  }
}
