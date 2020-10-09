package com.eam.IngSoft1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.eam.IngSoft1.domain.Usuario;
import com.eam.IngSoft1.IRepository.IUsuarioRepository;
import com.eam.IngSoft1.domain.Authority;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IUsuarioRepository usuarioRepository;
	
    @Override
     public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
    	
    	System.out.println("nombreUsuario "+ nombreUsuario);
		
     //Buscar el usuario con el repositorio y si no existe lanzar una exepcion
	     Usuario appUser = 
	                 usuarioRepository.findByNombreUsuario(nombreUsuario).orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));
			
	     //System.out.println( appUser.toString());
	     
	    //Mapear nuestra lista de Authority con la de spring security 
	    List grantList = new ArrayList();
	    for (Authority authority: appUser.getAuthority()) {
	        // ROLE_USER, ROLE_ADMIN,..
	        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
	            grantList.add(grantedAuthority);
	            System.out.println("Permiso: "+authority.getAuthority());
	    }
			
	    //Crear El objeto UserDetails que va a ir en sesion y retornarlo.
	    UserDetails user = (UserDetails) new User(appUser.getNombreUsuario(), appUser.getContrasena(), grantList);
	    System.out.println(appUser.getNombreUsuario()+" - clave:" + appUser.getContrasena());
        return user;
    }
}