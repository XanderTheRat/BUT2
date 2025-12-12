<?php
namespace App\Model;

use PDO;

class Post {
    private $pdo;

    public function __construct() {
        $this->pdo = Database::getConnection();
    }

    public function getAll() {
        $stmt = $this->pdo->query("
            SELECT p.*, u.nom as auteur, 
            (SELECT COUNT(*) FROM post_likes WHERE post_id = p.id) as like_count 
            FROM posts p 
            JOIN users u ON p.utilisateur_id = u.id 
            ORDER BY p.date_publication DESC
        ");
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function add($titre, $contenu, $userId) {
        $stmt = $this->pdo->prepare("INSERT INTO posts (titre, contenu, utilisateur_id) VALUES (?, ?, ?)");
        return $stmt->execute([$titre, $contenu, $userId]);
    }

    public function toggleLike($userId, $postId) {
        $stmt = $this->pdo->prepare("SELECT post_id FROM post_likes WHERE user_id = ? AND post_id = ?");
        $stmt->execute([$userId, $postId]);
        
        if ($stmt->fetch()) {
            $del = $this->pdo->prepare("DELETE FROM post_likes WHERE user_id = ? AND post_id = ?");
            $del->execute([$userId, $postId]);
            return false; 
        } else {
            $add = $this->pdo->prepare("INSERT INTO post_likes (user_id, post_id) VALUES (?, ?)");
            $add->execute([$userId, $postId]);
            return true;
        }
    }

    public function getLikeCount($postId) {
        $stmt = $this->pdo->prepare("SELECT COUNT(*) FROM post_likes WHERE post_id = ?");
        $stmt->execute([$postId]);
        return $stmt->fetchColumn();
    }

    public function getAuthorId($postId) {
        $stmt = $this->pdo->prepare("SELECT utilisateur_id FROM posts WHERE id = ?");
        $stmt->execute([$postId]);
        return $stmt->fetchColumn();
    }

    public function search($query) {
        $stmt = $this->pdo->prepare("
            SELECT p.*, u.nom as auteur 
            FROM posts p 
            JOIN users u ON p.utilisateur_id = u.id 
            WHERE p.titre LIKE ? OR p.contenu LIKE ? OR u.nom LIKE ?
            ORDER BY p.date_publication DESC
        ");
        $term = "%$query%";
        $stmt->execute([$term, $term, $term]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}