package com.eam.IngSoft1;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eam.IngSoft1.domain.Categoriaproducto;
import com.eam.IngSoft1.IRepository.ICategoriaRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoriaIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	ICategoriaRepository repository;
	
}
