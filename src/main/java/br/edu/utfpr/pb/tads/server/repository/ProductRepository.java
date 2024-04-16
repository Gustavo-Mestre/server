package br.edu.utfpr.pb.tads.server.repository;

import br.edu.utfpr.pb.tads.server.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//Esta interface, que nada tem a ver com front-end, funciona como
//um contrato para as operações de banco de dados que seu aplicativo pode executar sobre as entidades modeladas.

/*No contexto do Spring Data, essas interfaces permitem que você defina métodos customizados de busca,
inserção, exclusão e atualização, entre outros, sem precisar implementá-los explicitamente.
O Spring Data se encarrega de gerar a implementação concreta dessas interfaces em tempo de execução.
Isso é possível através do uso de proxies dinâmicos ou classes geradas
automaticamente que implementam os métodos definidos na interface.*/

//Portanto, esta interface vem com métodos prontos da dependência SPRING DATA JPA.


//Deixar explícito que será um bean gerenciado pelo spring.
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long>{

}
