package fr.ge.common.storage.ws.rest.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.ge.common.storage.service.IStorageService;
import fr.ge.common.storage.service.impl.IStorageTrackerService;
import fr.ge.common.storage.ws.bean.conf.StorageConfigurationBean;
import fr.ge.common.storage.ws.rest.IStorageRestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/test-context.xml" })
public class MvcTest {

  protected static final String ENDPOINT = "local://api";

  @Autowired
  private JAXRSServerFactoryBean originalServerFactory;

  @Autowired
  private IStorageService storageService;
  @Autowired
  private IStorageTrackerService storageTrackerService;
  @Autowired
  private StorageConfigurationBean storageConfigurationBean;

  @Autowired
  @Qualifier("storageRestServiceClient")
  private IStorageRestService storageServiceClient;

  protected Server server;

  @Before
  public void setUp() throws Exception {
    Mockito.reset(this.storageService, this.storageTrackerService, this.storageConfigurationBean);
    final JAXRSServerFactoryBean serverFactory = new JAXRSServerFactoryBean(this.originalServerFactory.getServiceFactory());
    serverFactory.setProviders(this.originalServerFactory.getProviders());
    serverFactory.setAddress(ENDPOINT);
    this.server = serverFactory.create();
  }

  @After
  public void tearDown() throws Exception {
    this.server.stop();
    this.server.destroy();
  }

  @Test
  public void testSimple() throws Exception {
    final byte[] resourceAsBytes = "Resource content".getBytes(StandardCharsets.UTF_8);
    try (InputStream in = new ByteArrayInputStream(resourceAsBytes)) {
      Attachment storageFile = new Attachment("storageFile", MediaType.APPLICATION_OCTET_STREAM, in);
      this.storageServiceClient.store("archived", storageFile, "test.txt", null);
    }
  }
}
