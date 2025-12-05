package com.knu.tubetalk.dao;

import com.knu.tubetalk.domain.ThreadEntity;
import com.knu.tubetalk.dto.TrendingThread;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ThreadDao {

    private final DataSource dataSource;

    public ThreadDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(ThreadEntity thread) throws SQLException {
        String sql = "INSERT INTO THREAD (Thread_id, Created_at, Participant_count) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, thread.getThreadId());
            ps.setTimestamp(2, Timestamp.valueOf(thread.getCreatedAt()));
            ps.setLong(3, thread.getParticipantCount());

            ps.executeUpdate();
        }
    }

    public Optional<ThreadEntity> findById(String threadId) throws SQLException {
        String sql = "SELECT Thread_id, Created_at, Participant_count FROM THREAD WHERE Thread_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, threadId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ThreadEntity thread = new ThreadEntity(
                            rs.getString("Thread_id"),
                            rs.getTimestamp("Created_at").toLocalDateTime(),
                            rs.getLong("Participant_count")
                    );
                    return Optional.of(thread);
                }
                return Optional.empty();
            }
        }
    }

    public void updateParticipantCount(String threadId, long count) throws SQLException {
        String sql = "UPDATE THREAD SET Participant_count = ? WHERE Thread_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, count);
            ps.setString(2, threadId);
            ps.executeUpdate();
        }
    }

    public void deleteById(String threadId) throws SQLException {
        String sql = "DELETE FROM THREAD WHERE Thread_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, threadId);
            ps.executeUpdate();
        }
    }
    
    public List<TrendingThread> findTrending(LocalDateTime since) throws SQLException {
        // 1. USER_COMMENT와 VIDEO를 조인
        // 2. 입력받은 시간(since) 이후에 작성된 댓글만 필터링
        // 3. 비디오별로 그룹화하여 개수 세기 (COUNT)
        // 4. 개수 내림차순 정렬 후 상위 5개 자르기
        String sql = "SELECT v.Video_id, v.Title, COUNT(c.Comment_id) as cnt " +
                     "FROM USER_COMMENT c " +
                     "JOIN VIDEO v ON c.Thread_id = v.Video_id " +
                     "WHERE c.Created_at >= ? " +
                     "GROUP BY v.Video_id, v.Title " +
                     "ORDER BY cnt DESC " +
                     "FETCH FIRST 5 ROWS ONLY";

        List<TrendingThread> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(since));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new TrendingThread(
                            rs.getString("Video_id"),
                            rs.getString("Title"),
                            rs.getLong("cnt")
                    ));
                }
            }
        }
        return result;
    }
}
