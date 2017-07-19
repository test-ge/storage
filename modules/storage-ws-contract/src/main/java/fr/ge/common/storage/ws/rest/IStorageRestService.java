/**
 * 
 */
package fr.ge.common.storage.ws.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import fr.ge.common.storage.ws.model.StoredFile;
import fr.ge.common.storage.ws.model.StoredTrayResult;

/**
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
@Path("/v1")
public interface IStorageRestService {

  /**
   * REST method storing a file with status.
   * 
   * @param storageTray
   *          the deposit tray : archived, error or inferno
   * @param storageFile
   *          the file to store
   * @param storageFilename
   *          the storage filename
   * @param storageReferenceId
   *          the id to reference the file to
   * @return The response
   */
  @POST
  // @Path("/tray/{storageTray : (archived)|(error)|(inferno) }/file")
  @Path("tray/{storageTray}/file")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  Response store(@PathParam("storageTray") final String storageTray, final Attachment storageFile,
    @QueryParam("storageFilename") final String storageFilename,
    @QueryParam("storageReferenceId") final String storageReferenceId);

  /**
   * REST method downloading a file.
   * 
   * @param storageTray
   *          the deposit tray : archived, error or inferno
   * @param storageId
   *          The uid storage
   * @return The response
   */
  @GET
  @Path("tray/{storageTray}/file/{storageId}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  Response download(@PathParam("storageId") @DefaultValue("") final String storageId,
    @PathParam("storageTray") final String storageTray);

  /**
   * REST method removing a file.
   * 
   * @param storageTray
   *          the deposit tray : archived, error or inferno
   * @param storageId
   *          The uid storage
   * @return The response
   */
  @DELETE
  @Path("tray/{storageTray}/file/{storageId}")
  @Produces(MediaType.APPLICATION_JSON)
  StoredFile remove(@PathParam("storageId") @DefaultValue("") final String storageId,
    @PathParam("storageTray") final String storageTray);

  /**
   * REST method getting all tray records.
   * 
   * @param storageTray
   *          the deposit tray : archived, error or inferno
   * @return The response
   */
  @GET
  @Path("tray/{storageTray}")
  @Produces({MediaType.APPLICATION_JSON })
  StoredTrayResult get(@PathParam("storageTray") final String storageTray);

}
