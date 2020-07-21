package hr.java.web.peharec.moneyapp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
		
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	 auth
		 .jdbcAuthentication()
		 .dataSource(dataSource)
		 .passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http
		.authorizeRequests()
		.antMatchers("/expenses/**")
		.hasRole("USER")
		.antMatchers("/**").permitAll()
		.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/expenses/new", true)
		.and()
			.logout()
			.logoutSuccessUrl("/login?logout=true")
		.and()
		.rememberMe()
		.and()
		.csrf().ignoringAntMatchers("/api/**", "/login", "/logout");
	}

}
