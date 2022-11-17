package ru.job4j.cinema;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@SpringBootApplication
public class Main {


    /**
     * Метод активирует пул соединений
     *
     * @return пул соединений
     */
    @Bean
    public BasicDataSource loadPool() {
        Properties cfg = loadDBProperties();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }

    /**
     * Метод загружает настройки БД
     *
     * @return Properties
     */
    private Properties loadDBProperties() {
        Properties cfg = new Properties();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Main.class.getClassLoader().getResourceAsStream("db.properties")
                )
        )) {
            cfg.load(reader);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return cfg;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/index");
    }
}
