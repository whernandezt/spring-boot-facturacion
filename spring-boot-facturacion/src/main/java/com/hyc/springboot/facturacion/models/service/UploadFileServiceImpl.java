package com.hyc.springboot.facturacion.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	// Constante
	public final static String UPPLOAD_FOLDER = "uploads";

	@Override
	public Resource load(String filename) throws MalformedURLException {

		Path pathFoto = getPath(filename);
		log.info("pathFoto: " + pathFoto);
		Resource recurso = null;
		recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
		}
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		// Apunta a un directorio externo
		// String rootPath = "D://Temp//uploads";

		// Agregar un id para no reemplazar archivo con el mismo nombre
		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		// A un directorio raíz dentro del proyect
		Path rootPath = getPath(uniqueFileName);

		log.info("rootPath: " + rootPath);

		// otra forma de subir
		/*
		 * byte[] bytes = foto.getBytes(); Path rutaCompleta = Paths.get(rootPath + "//"
		 * + foto.getOriginalFilename()); Files.write(rutaCompleta, bytes);
		 */

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFileName;
	}

	@Override
	public boolean delete(String filename) {
		
		Path rootPath = getPath(filename);
		File archivo =  rootPath.toFile();
		
		if(archivo.exists() && archivo.canRead()) {
			if(archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPPLOAD_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {

		FileSystemUtils.deleteRecursively(Paths.get(UPPLOAD_FOLDER).toFile());	
	}

	@Override
	public void init() throws IOException {
		Files.createDirectories(Paths.get(UPPLOAD_FOLDER));
	}

}
