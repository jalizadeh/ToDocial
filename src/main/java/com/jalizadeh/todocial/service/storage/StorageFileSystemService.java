package com.jalizadeh.todocial.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import com.jalizadeh.todocial.service.ServiceTypes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageFileSystemService implements StorageService {

	private final Path ROOT_LOCATION = Paths.get("upload-dir");

	@Override
	public void store(MultipartFile file, ServiceTypes service, String optionalFilename) {
		String filename;
		Path rootLocation = Paths.get(this.ROOT_LOCATION + "/" + service.name());
		
		if (optionalFilename == null) {
			filename = StringUtils.cleanPath(file.getOriginalFilename());
		} else {
			filename = optionalFilename;
		}
		
		try {
			if (file.isEmpty()) {
				throw new StorageException("StorageException: Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"StorageException: Cannot store file with relative path outside current directory "
								+ filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, rootLocation.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.ROOT_LOCATION, 1)
				.filter(path -> !path.equals(this.ROOT_LOCATION))
				.map(this.ROOT_LOCATION::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return ROOT_LOCATION.resolve(filename);
	}

	
	// if the `username.jpg` is not found, it will return `default.jpg`
	@Override
	public Resource loadAsResource(ServiceTypes service, String filename) {
		Resource resource = null;
		try {
			resource =  new UrlResource(load(service.name() + "/" + filename).toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				//throw new StorageFileNotFoundException("Could not read file: " + filename);
				return new UrlResource(load(service.name() + "/" + "default.jpg").toUri());
			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(ROOT_LOCATION.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(ROOT_LOCATION);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
