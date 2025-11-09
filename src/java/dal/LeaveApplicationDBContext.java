package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LeaveApplication;
import model.Employee;
import java.util.List;

public class LeaveApplicationDBContext extends DBContext<LeaveApplication> {

    @Override
    public ArrayList<LeaveApplication> list() {
        ArrayList<LeaveApplication> list = new ArrayList<>();
        String sql = "SELECT ID, created_by, create_time, [from], [to], reason, status, processed_by FROM LeaveApplication";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                LeaveApplication la = new LeaveApplication();
                la.setId(rs.getLong("ID"));

                Employee creator = new Employee();
                creator.setId(rs.getLong("created_by"));
                la.setCreatedBy(creator);

                la.setCreateTime(rs.getTimestamp("create_time"));
                la.setFrom(rs.getTimestamp("from"));
                la.setTo(rs.getTimestamp("to"));
                la.setReason(rs.getString("reason"));
                la.setStatus(rs.getString("status"));

                long processedById = rs.getLong("processed_by");
                if (!rs.wasNull()) {
                    Employee processor = new Employee();
                    processor.setId(processedById);
                    la.setProcessedBy(processor);
                }

                list.add(la);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveApplicationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // override abstract method từ DBContext
    @Override
    public LeaveApplication get(int id) {
        return get((long) id);
    }

    // method thực sự dùng long cho BIGINT
    public LeaveApplication get(long id) {
        LeaveApplication la = null;
        String sql = "SELECT ID, created_by, create_time, [from], [to], reason, status, processed_by FROM LeaveApplication WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                la = new LeaveApplication();
                la.setId(rs.getLong("ID"));

                Employee creator = new Employee();
                creator.setId(rs.getLong("created_by"));
                la.setCreatedBy(creator);

                la.setCreateTime(rs.getTimestamp("create_time"));
                la.setFrom(rs.getTimestamp("from"));
                la.setTo(rs.getTimestamp("to"));
                la.setReason(rs.getString("reason"));
                la.setStatus(rs.getString("status"));

                long processedById = rs.getLong("processed_by");
                if (!rs.wasNull()) {
                    Employee processor = new Employee();
                    processor.setId(processedById);
                    la.setProcessedBy(processor);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveApplicationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return la;
    }

    @Override
    public void insert(LeaveApplication leave) {
        String sql = "INSERT INTO LeaveApplication "
                + "(created_by, create_time, [from], [to], reason, status, processed_by) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, leave.getCreatedBy().getId());
            stm.setTimestamp(2, new Timestamp(leave.getCreateTime().getTime()));
            stm.setTimestamp(3, new Timestamp(leave.getFrom().getTime()));
            stm.setTimestamp(4, new Timestamp(leave.getTo().getTime()));
            stm.setString(5, leave.getReason());
            stm.setString(6, leave.getStatus());

            if (leave.getProcessedBy() != null) {
                stm.setLong(7, leave.getProcessedBy().getId());
            } else {
                stm.setNull(7, java.sql.Types.BIGINT);
            }

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(LeaveApplication la) {
        String sql = "UPDATE LeaveApplication SET created_by = ?, create_time = ?, [from] = ?, [to] = ?, reason = ?, status = ?, processed_by = ? WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, la.getCreatedBy().getId());
            stm.setTimestamp(2, new Timestamp(la.getCreateTime().getTime()));
            stm.setTimestamp(3, new Timestamp(la.getFrom().getTime()));
            stm.setTimestamp(4, new Timestamp(la.getTo().getTime()));
            stm.setString(5, la.getReason());
            stm.setString(6, la.getStatus());
            if (la.getProcessedBy() != null) {
                stm.setLong(7, la.getProcessedBy().getId());
            } else {
                stm.setNull(7, Types.BIGINT);
            }
            stm.setLong(8, la.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LeaveApplicationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(LeaveApplication la) {
        String sql = "DELETE FROM LeaveApplication WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, la.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LeaveApplicationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Update status và processed_by
    public void updateStatus(LeaveApplication leave) {
        String sql = "UPDATE LeaveApplication SET status = ?, processed_by = ? WHERE ID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, leave.getStatus());
            if (leave.getProcessedBy() != null) {
                stm.setLong(2, leave.getProcessedBy().getId());
            } else {
                stm.setNull(2, java.sql.Types.BIGINT);
            }
            stm.setLong(3, leave.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách đơn PENDING các nhân viên mà manager quản lý
    public List<LeaveApplication> getLeavesForManager(long managerId) {
        List<LeaveApplication> leaves = new ArrayList<>();
        String sql = "SELECT la.*, e.Name AS empName, p.Name AS processorName "
                + "FROM LeaveApplication la "
                + "JOIN Employee e ON la.created_by = e.ID "
                + "LEFT JOIN Employee p ON la.processed_by = p.ID "
                + "WHERE e.ID_manager = ? AND la.status = 'PENDING'";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, managerId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    LeaveApplication leave = new LeaveApplication();
                    leave.setId(rs.getLong("ID"));

                    Employee creator = new Employee();
                    creator.setId(rs.getLong("created_by"));
                    creator.setName(rs.getString("empName"));
                    leave.setCreatedBy(creator);

                    Timestamp tCreate = rs.getTimestamp("create_time");
                    if (tCreate != null) {
                        leave.setCreateTime(new Date(tCreate.getTime()));
                    }

                    Timestamp tFrom = rs.getTimestamp("from");
                    if (tFrom != null) {
                        leave.setFrom(new Date(tFrom.getTime()));
                    }

                    Timestamp tTo = rs.getTimestamp("to");
                    if (tTo != null) {
                        leave.setTo(new Date(tTo.getTime()));
                    }

                    leave.setReason(rs.getString("reason"));
                    leave.setStatus(rs.getString("status"));

                    long processedById = rs.getLong("processed_by");
                    if (!rs.wasNull()) {
                        Employee processor = new Employee();
                        processor.setId(processedById);
                        processor.setName(rs.getString("processorName"));
                        leave.setProcessedBy(processor);
                    }

                    leaves.add(leave);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaves;
    }

// Lấy tất cả đơn của 1 nhân viên (cả PENDING/APPROVED/REJECTED/CANCELLED)
    public List<LeaveApplication> getLeavesByEmployee(long employeeId) {
        List<LeaveApplication> list = new ArrayList<>();
        String sql = "SELECT la.*, e.Name AS empName, p.Name AS processorName "
                + "FROM LeaveApplication la "
                + "JOIN Employee e ON la.created_by = e.ID "
                + "LEFT JOIN Employee p ON la.processed_by = p.ID "
                + "WHERE la.created_by = ? "
                + "ORDER BY la.create_time DESC";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, employeeId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    LeaveApplication la = new LeaveApplication();
                    la.setId(rs.getLong("ID"));
                    // createdBy
                    Employee creator = new Employee();
                    creator.setId(rs.getLong("created_by"));
                    creator.setName(rs.getString("empName"));
                    la.setCreatedBy(creator);

                    // timestamps -> java.util.Date
                    Timestamp tCreate = rs.getTimestamp("create_time");
                    if (tCreate != null) {
                        la.setCreateTime(new Date(tCreate.getTime()));
                    }
                    Timestamp tFrom = rs.getTimestamp("from");
                    if (tFrom != null) {
                        la.setFrom(new Date(tFrom.getTime()));
                    }
                    Timestamp tTo = rs.getTimestamp("to");
                    if (tTo != null) {
                        la.setTo(new Date(tTo.getTime()));
                    }

                    la.setReason(rs.getString("reason"));
                    la.setStatus(rs.getString("status"));

                    long processedById = rs.getLong("processed_by");
                    if (!rs.wasNull()) {
                        Employee processor = new Employee();
                        processor.setId(processedById);
                        processor.setName(rs.getString("processorName"));
                        la.setProcessedBy(processor);
                    }

                    list.add(la);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public LeaveApplication getLeaveById(long id) {
        LeaveApplication leave = null;
        String sql = "SELECT la.*, e.Name AS empName, e.ID_manager, p.Name AS processorName "
                + "FROM LeaveApplication la "
                + "JOIN Employee e ON la.created_by = e.ID "
                + "LEFT JOIN Employee p ON la.processed_by = p.ID "
                + "WHERE la.ID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    leave = new LeaveApplication();
                    leave.setId(rs.getLong("ID"));

                    // Người tạo
                    Employee creator = new Employee();
                    creator.setId(rs.getLong("created_by"));
                    creator.setName(rs.getString("empName"));
                    long managerId = rs.getLong("ID_manager");
                    if (!rs.wasNull()) {
                        Employee manager = new Employee();
                        manager.setId(managerId);
                        creator.setManager(manager); // set manager cho creator
                    }
                    leave.setCreatedBy(creator);

                    // Thời gian
                    leave.setCreateTime(rs.getTimestamp("create_time") != null
                            ? new Date(rs.getTimestamp("create_time").getTime())
                            : null);
                    leave.setFrom(rs.getTimestamp("from") != null
                            ? new Date(rs.getTimestamp("from").getTime())
                            : null);
                    leave.setTo(rs.getTimestamp("to") != null
                            ? new Date(rs.getTimestamp("to").getTime())
                            : null);

                    leave.setReason(rs.getString("reason"));
                    leave.setStatus(rs.getString("status"));

                    // Người duyệt
                    long processedById = rs.getLong("processed_by");
                    if (!rs.wasNull()) {
                        Employee processor = new Employee();
                        processor.setId(processedById);
                        processor.setName(rs.getString("processorName"));
                        leave.setProcessedBy(processor);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leave;
    }
    
    public List<LeaveApplication> getLeavesApproveForManager(long managerId) {
        List<LeaveApplication> leaves = new ArrayList<>();
        String sql = "SELECT la.*, e.Name AS empName, p.Name AS processorName "
                + "FROM LeaveApplication la "
                + "JOIN Employee e ON la.created_by = e.ID "
                + "LEFT JOIN Employee p ON la.processed_by = p.ID "
                + "WHERE e.ID_manager = ? AND la.status = 'APPROVED'";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, managerId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    LeaveApplication leave = new LeaveApplication();
                    leave.setId(rs.getLong("ID"));

                    Employee creator = new Employee();
                    creator.setId(rs.getLong("created_by"));
                    creator.setName(rs.getString("empName"));
                    leave.setCreatedBy(creator);

                    Timestamp tCreate = rs.getTimestamp("create_time");
                    if (tCreate != null) {
                        leave.setCreateTime(new Date(tCreate.getTime()));
                    }

                    Timestamp tFrom = rs.getTimestamp("from");
                    if (tFrom != null) {
                        leave.setFrom(new Date(tFrom.getTime()));
                    }

                    Timestamp tTo = rs.getTimestamp("to");
                    if (tTo != null) {
                        leave.setTo(new Date(tTo.getTime()));
                    }

                    leave.setReason(rs.getString("reason"));
                    leave.setStatus(rs.getString("status"));

                    long processedById = rs.getLong("processed_by");
                    if (!rs.wasNull()) {
                        Employee processor = new Employee();
                        processor.setId(processedById);
                        processor.setName(rs.getString("processorName"));
                        leave.setProcessedBy(processor);
                    }

                    leaves.add(leave);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaves;
    }

}
