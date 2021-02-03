package hibernate;

import hibernate.dao.ProductDao;
import hibernate.entity.Product;

public class Main {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();


        productDao.saveOrUpdate(new Product("Tomat", 46));
        productDao.findAll().stream().forEach(p -> System.out.println(p.toString()));
        System.out.println(productDao.findById(1L));
        productDao.deleteById(38L);

    }
}
