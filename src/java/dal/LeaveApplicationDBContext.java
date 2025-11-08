package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LeaveApplication;
import model.Employee;

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
    public void insert(LeaveApplication la) {
        String sql = "INSERT INTO LeaveApplication (created_by, create_time, [from], [to], reason, status, processed_by) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LeaveApplicationDBContext.class.getName()).log(Level.SEVERE, null, ex);
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
}
