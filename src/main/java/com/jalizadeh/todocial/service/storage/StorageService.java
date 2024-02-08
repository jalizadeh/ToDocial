package com.jalizadeh.todocial.service.storage;

import com.jalizadeh.todocial.service.ServiceTypes;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void store(MultipartFile file, ServiceTypes service, String optionalFilename);

	Path load(String filename);

	Stream<Path> loadAll();

	Resource loadAsResource(ServiceTypes service, String filename);

	void deleteAll();

}
