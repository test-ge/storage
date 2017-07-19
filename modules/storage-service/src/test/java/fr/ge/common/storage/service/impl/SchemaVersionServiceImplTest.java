package fr.ge.common.storage.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import fr.ge.common.storage.service.mapper.SchemaVersionMapper;
import fr.ge.core.exception.TechniqueException;

public class SchemaVersionServiceImplTest {

  /** The postmail service. */
  @InjectMocks
  private SchemaVersionServiceImpl schemaVersionService;

  /** The mapper for schema_version. **/
  @Mock
  private SchemaVersionMapper schemaVersionMapper;

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
   * Test getting track id.
   * 
   * @throws TechniqueException
   *           Exceptiion raised
   */
  @Test
  public void testGetVersion() {
    String version = "1.00";
    Mockito.when(schemaVersionMapper.getVersion()).thenReturn(version);
    String actual = schemaVersionService.getVersion();
    assertEquals(actual, version);
  }
}
