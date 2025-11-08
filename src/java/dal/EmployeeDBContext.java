package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.Division;

public class EmployeeDBContext extends DBContext<Employee> {

    @Override
    public ArrayList<Employee> list() {
        ArrayList<Employee> list = new ArrayList<>();
        String sql = "SELECT ID, name, username, password, ID_manager, ID_division FROM Employee";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getLong("ID"));
                e.setName(rs.getString("name"));
                e.setUsername(rs.getString("username"));
                e.setPassword(rs.getString("password"));

                long managerId = rs.getLong("ID_manager");
                if (!rs.wasNull()) {
                    Employee manager = new Employee();
                    manager.setId(managerId);
                    e.setManager(manager);
                }

                long divisionId = rs.getLong("ID_division");
                if (!rs.wasNull()) {
                    Division division = new Division();
                    division.setId(divisionId);
                    e.setDivision(division);
                }

                list.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Employee get(int id) {
        return get((long) id); // override abstract method
    }
    
    public Employee get(String username, String password) {
        Employee e = null;
        String sql = "SELECT ID, name, username, password, ID_manager, ID_division FROM Employee WHERE username = ? AND password = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                e = new Employee();
                e.setId(rs.getLong("ID"));
                e.setName(rs.getString("name"));
                e.setUsername(rs.getString("username"));
                e.setPassword(rs.getString("password"));

                long managerId = rs.getLong("ID_manager");
                if (!rs.wasNull()) {
                    Employee manager = new Employee();
                    manager.setId(managerId);
                    e.setManager(manager);
                }

                long divisionId = rs.getLong("ID_division");
                if (!rs.wasNull()) {
                    Division division = new Division();
                    division.setId(divisionId);
                    e.setDivision(division);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    public Employee get(long id) {
        Employee e = null;
        String sql = "SELECT ID, name, username, password, ID_manager, ID_division FROM Employee WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                e = new Employee();
                e.setId(rs.getLong("ID"));
                e.setName(rs.getString("name"));
                e.setUsername(rs.getString("username"));
                e.setPassword(rs.getString("password"));

                long managerId = rs.getLong("ID_manager");
                if (!rs.wasNull()) {
                    Employee manager = new Employee();
                    manager.setId(managerId);
                    e.setManager(manager);
                }

                long divisionId = rs.getLong("ID_division");
                if (!rs.wasNull()) {
                    Division division = new Division();
                    division.setId(divisionId);
                    e.setDivision(division);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    @Override
    public void insert(Employee e) {
        String sql = "INSERT INTO Employee (name, username, password, ID_manager, ID_division) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, e.getName());
            stm.setString(2, e.getUsername());
            stm.setString(3, e.getPassword());
            if (e.getManager() != null) {
                stm.setLong(4, e.getManager().getId());
            } else {
                stm.setNull(4, Types.BIGINT);
            }
            if (e.getDivision() != null) {
                stm.setLong(5, e.getDivision().getId());
            } else {
                stm.setNull(5, Types.BIGINT);
            }
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Employee e) {
        String sql = "UPDATE Employee SET name = ?, username = ?, password = ?, ID_manager = ?, ID_division = ? WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, e.getName());
            stm.setString(2, e.getUsername());
            stm.setString(3, e.getPassword());
            if (e.getManager() != null) {
                stm.setLong(4, e.getManager().getId());
            } else {
                stm.setNull(4, Types.BIGINT);
            }
            if (e.getDivision() != null) {
                stm.setLong(5, e.getDivision().getId());
            } else {
                stm.setNull(5, Types.BIGINT);
            }
            stm.setLong(6, e.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Employee e) {
        String sql = "DELETE FROM Employee WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, e.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
