package com.orders.web.controller;

import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.StreamedContent;

/**
 * Created by Luis Costela on 7/7/2017.
 */
@ManagedBean(name = "fileDownloadController")
@RequestScoped
public class FileDownloadController extends BasicController implements Serializable {


  private static final long serialVersionUID = -1006329502038669705L;

  public StreamedContent downloadFile(final Downloadable fileDto) throws FileNotFoundException {
//    StreamedContent file = null;
//    InputStream stream = null;
//    final String name = fileDto.getName();
//    final int size;
//    try {
//      String fullPath = path + File.separatorChar + fileDto.getLocation();
//
//      if (!Files.exists(Paths.get(fullPath))) {
//        fullPath = parametrosRPC.getTempPathArchivos() + File.separatorChar + fileDto.getLocation();
//        if (!Files.exists(Paths.get(fullPath))) {
//          throw new FileNotFoundException("No se encuentra en la carpeta final ni en temporal");
//        }
//      }
//
//      stream = new FileInputStream(fullPath);
//      size = stream.available();
//      file = new DefaultStreamedContent(stream, MULTIPART_FORM_DATA_VALUE, name, size);
//    } catch (final FileNotFoundException e) {
//      log.error("No se encuentra el archivo en la ubicación: {} ", fileDto.getLocation(), e);
//      throw e;
//    } catch (final IOException e) {
//      file = new DefaultStreamedContent(stream, MULTIPART_FORM_DATA_VALUE, name);
//    
//    return file;
//  }
	  return null;
  }
}
