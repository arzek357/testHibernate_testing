package com.gb.arzek;

import com.gb.arzek.entity.Client;
import com.gb.arzek.entity.Order;
import com.gb.arzek.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("config/hibernate.cfg.xml")
                .buildSessionFactory();
        try {
            Client client = addNewClient(factory, new Client("Manya"));
            Product product1 = addNewProduct(factory, new Product("Apple", 5l));
            Product product2 = addNewProduct(factory, new Product("Water", 4l));
            addNewOrder(factory, client, product1, 2);
            addNewOrder(factory, client, product2, 4);
            getClientProductsInfo(factory,client).forEach(System.out::println);
        } finally {
            factory.close();
        }
    }

    private static Client addNewClient(SessionFactory factory, Client client) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(client);
        session.getTransaction().commit();
        session.close();
        return client;
    }

    private static void deleteClient(SessionFactory factory, Client client) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(session.get(Client.class, client.getId()));
        session.getTransaction().commit();
        session.close();
    }

    private static Product addNewProduct(SessionFactory factory, Product product) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
        return product;
    }

    private static void deleteProduct(SessionFactory factory, Product product) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(session.get(Product.class, product.getId()));
        session.getTransaction().commit();
        session.close();
    }

    private static Product addNewOrder(SessionFactory factory, Client client, Product product, long number) {
        Order order = new Order(product, number, client);
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();
        session.close();
        return product;
    }

    private static List<Product> getClientProductsInfo(SessionFactory factory, Client client) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Client clientFromDb = session.createQuery("SELECT c FROM Client c WHERE c.id=:id",Client.class).setParameter("id", client.getId()).getSingleResult();
        List<Product> products = new ArrayList<>(clientFromDb.getProductList());
        session.getTransaction().commit();
        session.close();
        return products;
    }

    private static List<Client> getProductClientsInfo(SessionFactory factory, Product product) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Product productFromDb = session.createQuery("SELECT p FROM Product p WHERE p.id=:id", Product.class).setParameter("id", product.getId()).getSingleResult();
        List<Client> clients = new ArrayList<>(productFromDb.getClientList());
        session.getTransaction().commit();
        session.close();
        return clients;
    }
}
