package fr.ge.common.storage.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.ge.common.storage.bean.MessageTrackerProvider;
import fr.ge.core.exception.TechniqueException;
import fr.ge.tracker.facade.ITrackerFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/ge-services-commons-properties-files-config.xml" })
public class StorageTrackerServiceImplTest {

  /** The postmail service. */
  @InjectMocks
  private StorageTrackerServiceImpl storageTrackerService;

  /** tracker facade. */
  @Mock
  private ITrackerFacade trackerFacade;

  /**
   * Message Provider.
   */
  @Mock
  private MessageTrackerProvider messageTrackerProvider;

  /**
   * Sets the up.
   *
   * @throws Exception
   *           the exception
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * Test creating new track id.
   * 
   * @throws TechniqueException
   *           Exceptiion raised
   */
  @Test
  public void testCreateStorageId() throws TechniqueException {
    String trackId = "2017-02-ABC-DEF-00";
    Mockito.when(trackerFacade.createUid()).thenReturn(trackId);
    String actual = storageTrackerService.createStorageId();
    assertEquals(actual, trackId);
  }

  /**
   * Test creating new track id with exception.
   * 
   * @throws TechniqueException
   *           Exceptiion raised
   */
  @Test(expected = TechniqueException.class)
  public void testFaileCreateTrackId() throws TechniqueException {
    String trackId = "2017-02-ABC-DEF-00";
    Mockito.doThrow(new NullPointerException()).when(trackerFacade).createUid();
    String actual = storageTrackerService.createStorageId();
    assertEquals(actual, trackId);
  }

  /**
   * Test add a new message.
   * 
   */
  @Test
  public void testAddMessage() {
    String trackId = "2017-02-ABC-DEF-00";
    String eventCode = "tracker.storage.posted";
    Mockito.when(messageTrackerProvider.getMessage(Matchers.anyString(), Matchers.anyString())).thenReturn("");
    storageTrackerService.addMessage(trackId, eventCode);
  }

  /**
   * Test add a new message with failure.
   * 
   */
  @Test
  public void testFailedAddMessage() {
    String trackId = "2017-02-ABC-DEF-00";
    String eventCode = "";
    Mockito.when(trackerFacade.post(Matchers.anyString(), Matchers.anyString())).thenThrow(new NullPointerException());
    storageTrackerService.addMessage(trackId, eventCode);
  }

  /**
   * Test link method.
   * 
   * @throws TechniqueException
   *           technical exception
   * 
   */
  @Test
  public void testLink() throws TechniqueException {
    String storageId = "2017-02-ABC-DEF-00";
    String referenceId = "2017-02-ABC-DEF-01";
    Mockito.when(trackerFacade.link(Matchers.anyString(), Matchers.anyString())).thenReturn(storageId);
    storageTrackerService.linkReference(storageId, referenceId);
  }

  /**
   * Test link method with exception.
   * 
   * @throws TechniqueException
   *           technical exception
   * 
   */
  @Test(expected = TechniqueException.class)
  public void testLinkWithException() throws TechniqueException {
    String storageId = "2017-02-ABC-DEF-00";
    String referenceId = "2017-02-ABC-DEF-01";
    Mockito.when(trackerFacade.link(Matchers.anyString(), Matchers.anyString())).thenThrow(new NullPointerException());
    storageTrackerService.linkReference(storageId, referenceId);
  }
}
