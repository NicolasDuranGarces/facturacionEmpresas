package com.eam.IngSoft1;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eam.IngSoft1.domain.Usuario;
import com.eam.IngSoft1.IRepository.IUsuarioRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class UsuarioIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	IUsuarioRepository repository;
	
}
