package com.easyebay.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easyebay.upload.FileSystemStorageService;

@Controller
public class FilesController {
	
	@Autowired
	FileSystemStorageService storageService;
	
	@GetMapping("/files/{filename:.+}")
	 @ResponseBody
	 public ResponseEntity<Resource> serveFile(@PathVariable String filename){
		 
		 Resource file= storageService.loadAsResource(filename);
		 return ResponseEntity.ok().body(file);
	

}

	
}
