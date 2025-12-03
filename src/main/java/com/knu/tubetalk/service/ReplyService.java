package com.knu.tubetalk.service;

import com.knu.tubetalk.dao.ReplyDao;
import com.knu.tubetalk.domain.Reply;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReplyService {

    private final ReplyDao replyDao;

    public ReplyService(ReplyDao replyDao) {
        this.replyDao = replyDao;
    }

    public List<Reply> getRepliesByCommentId(String commentId) throws SQLException {
        return replyDao.findByCommentId(commentId);
    }

    @Transactional
    public void addReply(String commentId, Reply reply) throws SQLException {
        reply.setCommentId(commentId);
        reply.setReplyId(generateUniqueId());
        reply.setCreatedAt(LocalDateTime.now());
        reply.setLikeCount(0);
        reply.setDislikeCount(0);
        
        replyDao.save(reply);
    }

    public Reply getReply(String replyId) throws SQLException {
        Optional<Reply> opt = replyDao.findById(replyId);
        return opt.orElse(null);
    }

    @Transactional
    public void updateReply(String replyId, String content, LocalDateTime updatedAt) throws SQLException {
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        Timestamp ts = Timestamp.valueOf(updatedAt);
        replyDao.updateContent(replyId, content, ts);
    }

    @Transactional
    public void deleteReply(String replyId) throws SQLException {
        replyDao.deleteById(replyId);
    }

    private String generateUniqueId() {
        // UUID는 하이픈 제거 후 32자이므로, 26자로 제한 (다른 서비스와 동일하게)
        return UUID.randomUUID().toString().replace("-", "").substring(0, 26);
    }
}

