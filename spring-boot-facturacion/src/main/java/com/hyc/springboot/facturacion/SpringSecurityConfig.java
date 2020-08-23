package com.hyc.springboot.facturacion;

//import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import com.hyc.springboot.facturacion.auth.handler.LoginSuccessHandler;
import com.hyc.springboot.facturacion.models.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled = true) //permite las anotaciones Secured en los contraladores
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	
	//@Autowired
	//private DataSource dataSource;
	
	@Autowired
	private JpaUserDetailsService UserDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEnconder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/","/css/**","/js/**","/images/**").permitAll()
		.antMatchers("/clientes/**").hasAnyRole("USER")
		.antMatchers("/uploads/**").hasAnyRole("USER")
		.antMatchers("/factura/**").hasAnyRole("ADMIN")
		/*.antMatchers("/eliminar/**").hasAnyRole("ADMIN")*/
		/*.antMatchers("/factura/**").hasAnyRole("ADMIN")*/
		.anyRequest().authenticated()
		.and()
			.formLogin()
				.successHandler(successHandler)
				.loginPage("/login")
				.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
		/*
		//JDBC 
		builder.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEnconder)
		.usersByUsernameQuery("select username, password, enabled from users where username=?")
		.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id = u.id) where u.username=?");
		*/
		
		//JPA
		builder.userDetailsService(UserDetailsService)
		.passwordEncoder(passwordEnconder);
		/*//En memoria
		PasswordEncoder encoder = this.passwordEconder;
		
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN","USER"))
		.withUser(users.username("walter").password("12345").roles("USER"));
		*/
	}
}
