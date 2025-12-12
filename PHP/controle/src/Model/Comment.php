<?php
namespace App\Model;

use PDO;

class Comment {
    private $pdo;

    public function __construct() {
        $this->pdo = Database::getConnection();
    }

    public function getByPost($postId) {
        $stmt = $this->pdo->prepare("
            SELECT c.*, u.nom as auteur, u.id as id_auteur_comment,
            COALESCE((SELECT SUM(vote_value) FROM comment_votes WHERE comment_id = c.id), 0) as score
            FROM comments c 
            JOIN users u ON c.utilisateur_id = u.id 
            WHERE c.post_id = ? 
            ORDER BY c.date_commentaire ASC
        ");
        $stmt->execute([$postId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function add($content, $userId, $postId) {
        $stmt = $this->pdo->prepare("INSERT INTO comments (contenu, utilisateur_id, post_id) VALUES (?, ?, ?)");
        return $stmt->execute([$content, $userId, $postId]);
    }

    public function getAuthorId($commentId) {
        $stmt = $this->pdo->prepare("SELECT utilisateur_id FROM comments WHERE id = ?");
        $stmt->execute([$commentId]);
        return $stmt->fetchColumn();
    }

    public function update($id, $content) {
        $stmt = $this->pdo->prepare("UPDATE comments SET contenu = ? WHERE id = ?");
        return $stmt->execute([$content, $id]);
    }

    public function vote($userId, $commentId, $value) {
        $stmt = $this->pdo->prepare("SELECT user_id, vote_value FROM comment_votes WHERE user_id = ? AND comment_id = ?");
        $stmt->execute([$userId, $commentId]);
        $existing = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($existing) {
            if ($existing['vote_value'] == $value) {
                $del = $this->pdo->prepare("DELETE FROM comment_votes WHERE user_id = ? AND comment_id = ?");
                $del->execute([$userId, $commentId]);
            } else {
                $upd = $this->pdo->prepare("UPDATE comment_votes SET vote_value = ? WHERE user_id = ? AND comment_id = ?");
                $upd->execute([$value, $userId, $commentId]);
            }
        } else {
            $ins = $this->pdo->prepare("INSERT INTO comment_votes (user_id, comment_id, vote_value) VALUES (?, ?, ?)");
            $ins->execute([$userId, $commentId, $value]);
        }
    }

    public function getScore($commentId) {
        $stmt = $this->pdo->prepare("SELECT COALESCE(SUM(vote_value), 0) FROM comment_votes WHERE comment_id = ?");
        $stmt->execute([$commentId]);
        return $stmt->fetchColumn();
    }
}