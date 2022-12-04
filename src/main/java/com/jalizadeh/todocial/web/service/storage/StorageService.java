package com.jalizadeh.todocial.web.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void store(MultipartFile file, String optionalFilename);

	Path load(String filename);

	Stream<Path> loadAll();

	Resource loadAsResource(String filename);

	void deleteAll();

}
