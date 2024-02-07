package com.jalizadeh.todocial.service.storage;

import com.jalizadeh.todocial.service.ServiceTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This controller create end-points for accessing uploaded files. 
 * For accessing images that are uploaded in "upload-image" directory,
 * you only need to use the following url where ever you need:
 * 
 * <img src="/photo/user/${user.photo} />
 * 
 * and the rest will be handled here, without exposing your storage folder's name
 */

@Controller
public class FileController {

	private final StorageService storageService;

	@Autowired
	public FileController(StorageService storageService) {
		this.storageService = storageService;
	}

	
	@GetMapping("/photo/{service}/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String service, @PathVariable String filename) {
		Resource file = storageService.loadAsResource(ServiceTypes.valueOf(service), filename);

		if (file == null){
			return null;
		}
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
