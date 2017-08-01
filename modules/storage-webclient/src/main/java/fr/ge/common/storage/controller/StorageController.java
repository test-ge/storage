/**
 * 
 */
package fr.ge.common.storage.controller;

import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ge.common.storage.bean.conf.StorageConfigurationWebBean;
import fr.ge.common.storage.facade.IStorageFacade;
import fr.ge.common.storage.ws.model.StorageTrayEnum;
import fr.ge.common.storage.ws.model.StoredTrayTree;
import fr.ge.core.exception.TechniqueException;
import fr.ge.core.log.GestionnaireTrace;

/**
 * Storage main page controller.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
@Controller
public class StorageController {

  /** Record facade. */
  @Autowired
  private IStorageFacade storageFacade;

  /** The functionnal logger. */
  private static final Logger LOGGER_FONC = GestionnaireTrace.getLoggerFonctionnel();

  /** Storage configuration bean. */
  @Autowired
  private StorageConfigurationWebBean storageConfigurationWebBean;

  /**
   * Display dashboard home page.
   *
   * @return string
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String displayHome() {
    LOGGER_FONC.debug("Home page display");
    return "redirect:/home";
  }

  @RequestMapping(value = "/home", method = RequestMethod.GET)
  public String displayHomePage(final Model model, final HttpServletRequest request, final Locale locale)
    throws TechniqueException {
    model.addAttribute("urlFeedback", storageConfigurationWebBean.getUrlFeedback());
    model.addAttribute("hostFeedback", storageConfigurationWebBean.getHostFeedback());
    LOGGER_FONC.debug("Home page displaying all trays");

    // -->Archived tray
    this.getTray(model, StorageTrayEnum.ARCHIVED.getName());

    // -->Error tray
    this.getTray(model, StorageTrayEnum.ERROR.getName());

    // -->Inferno tray
    this.getTray(model, StorageTrayEnum.INFERNO.getName());

    return "home";
  }

  /**
   * Get all storage files as a tree.
   * 
   * @param model
   *          The model to add attributes
   * @param storageTray
   *          The storage tray to display.
   * @return A storage tray tree
   */
  private StoredTrayTree getTray(final Model model, final String storageTray) {
    LOGGER_FONC.debug("Listing all storage files for {} tray", storageTray);
    StoredTrayTree storedTrayTree = null;
    try {
      storedTrayTree = storageFacade.generateTree(storageTray);
      if (null != storedTrayTree) {
        model.addAttribute(storageTray, storedTrayTree);
      }
    } catch (TechniqueException te) {
      LOGGER_FONC.error("An error occured when getting all files from tray '{}' due to : {}", storageTray, te.getMessage());
    }
    return storedTrayTree;
  }

  /**
   * Download a file from STORAGE.
   * 
   * @param modelThe
   *          model
   * @param httpRequest
   *          The request
   * @param httpResponse
   *          The response
   * @param uid
   *          The storage id
   * @param tray
   *          The storage tray
   * @throws Exception
   *           Exception
   */
  @RequestMapping(value = "/download", method = RequestMethod.GET)
  public void downloadFile(final Model model, final HttpServletRequest httpRequest, final HttpServletResponse httpResponse,
    @RequestParam(value = "uid", required = true) final String uid,
    @RequestParam(value = "tray", required = true) final String tray) throws Exception {
    LOGGER_FONC.debug("Downloading a storage file {} for {} tray", uid, tray);

    final Response response = this.storageFacade.download(uid, tray);
    final InputStream file = (InputStream) response.getEntity();
    httpResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, response.getHeaderString(HttpHeaders.CONTENT_DISPOSITION));
    httpResponse.setContentType("application/octet-stream");
    IOUtils.copy(file, httpResponse.getOutputStream());
    httpResponse.flushBuffer();
  }

  /**
   * Delete a file from STORAGE
   * 
   * @param model
   *          The model
   * @param request
   *          The request
   * @param storageId
   *          The storage id
   * @return The page to display
   * @throws TechniqueException
   */
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public String deleteFile(final Model model, final HttpServletRequest request, final HttpServletResponse httpResponse,
    @RequestParam(value = "uid", required = true) final String uid,
    @RequestParam(value = "tray", required = true) final String tray) throws TechniqueException {
    LOGGER_FONC.debug("Deleting a storage file {} for {} tray", uid, tray);
    try {
      this.storageFacade.remove(uid, tray);
    } catch (TechniqueException te) {
      LOGGER_FONC.error("An error occured when removing file {} from tray '{}' due to : {}", uid, tray, te.getMessage());
      return "error";
    }
    return "redirect:/home";
  }
}
