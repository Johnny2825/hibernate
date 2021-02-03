package hibernate.dao;

import hibernate.entity.Product;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class ProductDao {

    private EntityManagerFactory factory;
    private EntityManager em;

    public ProductDao() {
        factory = new Configuration()
                .configure("hibernate.xml")
                .buildSessionFactory();

        em = factory.createEntityManager();
    }

    public Product findById(Long id){
        em.getTransaction().begin();
        Product product = em.find(Product.class, 1L);
        em.getTransaction().commit();
        return product;
    }

    public List<Product> findAll(){
        List<Product> products = em.createQuery("SELECT a FROM Product a",Product.class).getResultList();
        return products;
    }

    public void deleteById(Long id){
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Product a WHERE a.id = :id");
        query.setParameter("id", id);
        if (query.executeUpdate() == 0) {
            System.out.println("Строки с таким id нету");
        } else {
            System.out.println("Строка удалена");
        }
        em.getTransaction().commit();
    }

    public Product saveOrUpdate(Product product){
        Query query = em.createQuery("select p from Product p where p.title = :title");
        query.setParameter("title", product.getTitle());
        Product anotherProduct;
        try {
            anotherProduct = (Product) query.getSingleResult();
            anotherProduct.setPrice(product.getPrice());
            em.getTransaction().begin();
            em.merge(anotherProduct);
            em.getTransaction().commit();

        } catch (NoResultException e) {
            System.out.println("Нету такого продукта, данный будет сохранен");
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
        }
        return product;
    }
}
