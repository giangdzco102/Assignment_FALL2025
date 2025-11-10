package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Division;
import model.Employee;

public class EmployeeDBContext extends DBContext<Employee> {

    @Override
    public ArrayList<Employee> list() {
        ArrayList<Employee> list = new ArrayList<>();
        String sql = "SELECT e.ID, e.name, e.username, e.password, e.ID_manager, "
                + "e.ID_division, m.Name AS manager_name, d.name AS division_name "
                + "FROM Employee e "
                + "LEFT JOIN Employee m ON e.ID_manager = m.ID "
                + "LEFT JOIN Division d ON e.ID_division = d.ID";
        try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

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
                    manager.setName(rs.getString("manager_name"));
                    e.setManager(manager);
                }

                long divisionId = rs.getLong("ID_division");
                if (!rs.wasNull()) {
                    Division division = new Division();
                    division.setId(divisionId);
                    division.setName(rs.getString("division_name"));
                    e.setDivision(division);
                }

                list.add(e);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Employee> listWithRoles() {
        ArrayList<Employee> list = new ArrayList<>();
        String sql = """
        SELECT e.ID, e.name, e.username, e.password,
               d.ID AS div_id, d.name AS div_name,
               m.ID AS manager_id, m.Name AS manager_name,
               r.ID AS role_id, r.name AS role_name
        FROM Employee e
        LEFT JOIN Division d ON e.ID_division = d.ID
        LEFT JOIN Employee m ON e.ID_manager = m.ID
        LEFT JOIN EmployeeRole er ON e.ID = er.EmployeeID
        LEFT JOIN Role r ON er.RoleID = r.ID
        ORDER BY e.ID
    """;

        try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

            java.util.Map<Long, Employee> empMap = new java.util.HashMap<>();

            while (rs.next()) {
                long empId = rs.getLong("ID");
                Employee emp = empMap.get(empId);

                if (emp == null) {
                    emp = new Employee();
                    emp.setId(empId);
                    emp.setName(rs.getString("name"));
                    emp.setUsername(rs.getString("username"));
                    emp.setPassword(rs.getString("password"));

                    long divId = rs.getLong("div_id");
                    if (!rs.wasNull()) {
                        Division div = new Division();
                        div.setId(divId);
                        div.setName(rs.getString("div_name"));
                        emp.setDivision(div);
                    }

                    long managerId = rs.getLong("manager_id");
                    if (!rs.wasNull()) {
                        Employee manager = new Employee();
                        manager.setId(managerId);
                        manager.setName(rs.getString("manager_name"));
                        emp.setManager(manager);
                    }

                    emp.setRoles(new java.util.ArrayList<>());
                    empMap.put(empId, emp);
                }

                long roleId = rs.getLong("role_id");
                if (!rs.wasNull()) {
                    model.Role role = new model.Role();
                    role.setId(roleId);
                    role.setName(rs.getString("role_name"));
                    emp.getRoles().add(role);
                }
            }

            list.addAll(empMap.values());
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Employee get(int id) {
        return get((long) id);
    }

    public Employee get(long id) {
        Employee e = null;
        String sql = "SELECT e.ID, e.name, e.username, e.password, e.ID_manager, "
                + "e.ID_division, m.Name AS manager_name, d.name AS division_name "
                + "FROM Employee e "
                + "LEFT JOIN Employee m ON e.ID_manager = m.ID "
                + "LEFT JOIN Division d ON e.ID_division = d.ID "
                + "WHERE e.ID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
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
                    manager.setName(rs.getString("manager_name"));
                    e.setManager(manager);
                }

                long divisionId = rs.getLong("ID_division");
                if (!rs.wasNull()) {
                    Division division = new Division();
                    division.setId(divisionId);
                    division.setName(rs.getString("division_name"));
                    e.setDivision(division);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    public Employee get(String username, String password) {
        Employee e = null;
        String sql = "SELECT ID, name, username, password, ID_manager, ID_division "
                + "FROM Employee WHERE username = ? AND password = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
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

    @Override
    public void insert(Employee e) {
        String sql = "INSERT INTO Employee (name, username, password, ID_manager, ID_division) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                e.setId(rs.getLong(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Employee e) {
        String sql = "UPDATE Employee SET name = ?, username = ?, password = ?, "
                + "ID_manager = ?, ID_division = ? WHERE ID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
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
        String sqlDeleteRoles = "DELETE FROM EmployeeRole WHERE EmployeeID = ?";
        String sqlDeleteLeavesCreated = "DELETE FROM LeaveApplication WHERE created_by = ?";
        String sqlDeleteLeavesProcessed = "UPDATE LeaveApplication SET processed_by = NULL WHERE processed_by = ?";
        String sqlUnsetDivisionHead = "UPDATE Division SET head_division_id = NULL WHERE head_division_id = ?";
        String sql = "DELETE FROM Employee WHERE ID = ?";

        try {
            connection.setAutoCommit(false);  

            try (PreparedStatement stm1 = connection.prepareStatement(sqlDeleteRoles); PreparedStatement stm2 = connection.prepareStatement(sqlDeleteLeavesCreated); PreparedStatement stm3 = connection.prepareStatement(sqlDeleteLeavesProcessed); PreparedStatement stm4 = connection.prepareStatement(sqlUnsetDivisionHead); PreparedStatement stm5 = connection.prepareStatement(sql)) {

                stm1.setLong(1, e.getId());
                stm1.executeUpdate();

                stm2.setLong(1, e.getId());
                stm2.executeUpdate();

                stm3.setLong(1, e.getId());
                stm3.executeUpdate();

                stm4.setLong(1, e.getId());
                stm4.executeUpdate();

                stm5.setLong(1, e.getId());
                stm5.executeUpdate();

                connection.commit(); 
            } catch (SQLException ex) {
                connection.rollback();
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> getFeatureURLsByEmployee(long employeeId) {
        List<String> urls = new ArrayList<>();
        String sql = "SELECT f.URL "
                + "FROM Feature f "
                + "JOIN RoleFeature rf ON f.ID = rf.FeatureID "
                + "JOIN EmployeeRole er ON rf.RoleID = er.RoleID "
                + "WHERE er.EmployeeID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, employeeId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                urls.add(rs.getString("URL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return urls;
    }

    public boolean isAdmin(long employeeId) {
        String sql = "SELECT COUNT(*) FROM EmployeeRole er "
                + "JOIN Role r ON er.RoleID = r.ID "
                + "WHERE er.EmployeeID = ? AND LOWER(r.name) = 'Admin'";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, employeeId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getDivisionNameByEmployeeId(long empId) {
        String sql = "SELECT d.name "
                + "FROM Employee e "
                + "LEFT JOIN Division d ON e.ID_division = d.ID "
                + "WHERE e.ID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, empId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Chưa có";
    }
}
