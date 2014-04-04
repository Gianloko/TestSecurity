package com.gian.conf;

import com.gian.entities.Authorization;
import com.gian.entities.User;
import javax.sql.DataSource;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Gianluca Tessitore
 */
@Configuration
@ComponentScan(basePackages = "com.gian")
public class HibernateConfiguration {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new AnnotationConfiguration()
                    .configure()
                    .addPackage("com.gian.entities")  
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Authorization.class)
                    .buildSessionFactory();           
        } catch (HibernateException ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public HibernateConfiguration() {
    }

    @Bean(name = "sessionFactory", autowire = Autowire.BY_NAME)
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}
