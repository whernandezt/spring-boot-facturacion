package com.hyc.springboot.facturacion;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//import com.hyc.springboot.facturacion.SpringBootFacturacionApplication;
import com.hyc.springboot.facturacion.models.service.IUploadFileService;

@SpringBootApplication
public class SpringBootFacturacionApplication {

	@Autowired
    public IUploadFileService uploadSerive;
	

	@Autowired
	private BCryptPasswordEncoder passwordEconder;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFacturacionApplication.class, args);
	}
			
	
	public void run(String... args) throws Exception {
		//uploadSerive.deleteAll();
		uploadSerive.init();
		
		//Metodo para generar contraseñas encriptadas
		String password = "hc2020";
		for(int i = 0; i<2;i++) {
		 
			String bcryptPassword = passwordEconder.encode(password);
			System.out.println(bcryptPassword);
		}
	}

}
