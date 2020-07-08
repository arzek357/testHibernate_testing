package com.gb.arzek;

import com.gb.arzek.entity.Client;
import com.gb.arzek.entity.Order;
import com.gb.arzek.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MainClass {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("config/hibernate.cfg.xml")
                .buildSessionFactory();
        try {
            Client client = addNewClient(factory, new Client("Petya"));
            Product product1 = addNewProduct(factory, new Product("Bulka", 19l));
            Product product2 = addNewProduct(factory, new Product("Kex", 92l));
            addNewOrder(factory, client, product1, 1);
            addNewOrder(factory, client, product2, 1);
            getClientProductsInfo(factory, client).forEach(System.out::println);
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
        List<Product> products = new ArrayList<>();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        List<Order> orders = session.createQuery("SELECT o FROM Order o WHERE o.client_id=:id", Order.class).setParameter("id", client.getId()).getResultList();
        orders.forEach(order -> products.add(order.getProduct()));
        session.getTransaction().commit();
        session.close();
        return products;
    }

    private static List<Client> getProductClientsInfo(SessionFactory factory, Product product) {
        List<Client> clients = new ArrayList<>();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        List<Order> orders = session.createQuery("SELECT o FROM Order o WHERE o.client_id=:id", Order.class).setParameter("id", product.getId()).getResultList();
        orders.forEach(order -> clients.add(order.getClient()));
        session.getTransaction().commit();
        session.close();
        return clients;
    }
}
