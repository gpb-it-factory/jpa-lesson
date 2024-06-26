package ru.gpb.jpalesson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.gpb.jpalesson.entity.one2one.Worker;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JdbcTemplateDemo {
    
    private final JdbcTemplate jdbcTemplate;
    
    public void sqlInjection(String customerId) {
        String sql = "select "
                + "customer_id,acc_number,branch_id,balance "
                + "from Accounts where customer_id = '"
                + customerId
                + "'";
        
        //        Connection c = dataSource.getConnection();
        //        ResultSet rs = c.createStatement().executeQuery(sql);
    }
    
    public String getPersonNameById(int id) {
        String sql = "SELECT name FROM person WHERE id = ?";
        
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }
    
    public List<Worker> getAllPersons() {
        String sql = "SELECT * FROM worker";
        
        return jdbcTemplate.query(sql, (rs, rowNum) -> Worker.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build());
    }
    
    public void updatePersonNameById(int id, String name) {
        String sql = "UPDATE person SET name = ? WHERE id = ?";
        
        jdbcTemplate.update(sql, name, id);
    }
    
    public void callStoredProc(int id) {
        String sql = "{call update_worker_name(?)}";
        
        jdbcTemplate.execute(sql, (CallableStatementCallback<Object>) cs -> {
            cs.setInt(1, id);
            cs.execute();
            return null;
        });
    }
    
}
