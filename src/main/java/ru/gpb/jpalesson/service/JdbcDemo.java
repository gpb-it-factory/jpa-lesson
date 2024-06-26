package ru.gpb.jpalesson.service;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcDemo {
    
    @SneakyThrows
    public void simpleRequest() {
        //Регистрация драйвера
        Class.forName("org.postgresql.Driver");
        
        //Подключение к БД
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/mydatabase", "postgres", "***");
        
        //Подготовка запроса
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        
        //Обработка результата
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }
        
        //Закрытие подключения
        connection.close();
    }
}
