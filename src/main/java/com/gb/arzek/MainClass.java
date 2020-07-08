package com.gb.arzek;

import com.gb.arzek.entity.Client;
import com.gb.arzek.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainClass {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("config/hibernate.cfg.xml")
                .buildSessionFactory();
        try {
            addNewClient(factory, new Client("Sasha"));
            deleteClientById(factory,1);
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

    private static void deleteClientById(SessionFactory factory, long id) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(session.get(Client.class,id));
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
    private static void deleteProductById(SessionFactory factory, long id) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(session.get(Product.class,id));
        session.getTransaction().commit();
        session.close();
    }

}
