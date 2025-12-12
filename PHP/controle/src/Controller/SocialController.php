<?php
namespace App\Controller;

use App\Model\Post;
use App\Model\Comment;
use App\Model\Notification;
use App\View\View;

class SocialController {
    
    public function index() {
        if (!isset($_SESSION['user'])) View::redirect('index.php?action=login');

        $postModel = new Post();
        $commentModel = new Comment();
        
        $posts = $postModel->getAll();
        if ($posts) {
            foreach ($posts as &$post) {
                $post['comments'] = $commentModel->getByPost($post['id']);
            }
        } else {
            $posts = [];
        }
        
        View::render('social', ['posts' => $posts]);
    }

    public function addPost() {
        if (!isset($_SESSION['user'])) View::redirect('index.php?action=login');

        if ($_SERVER['REQUEST_METHOD'] === 'POST' && !empty($_POST['titre']) && !empty($_POST['contenu'])) {
            $postModel = new Post();
            $postModel->add($_POST['titre'], $_POST['contenu'], $_SESSION['user']['id']);
        }
        View::redirect('index.php');
    }

    public function apiAddComment() {
        $this->checkAjaxAuth();
        $data = json_decode(file_get_contents('php://input'), true);

        if (!empty($data['postId']) && !empty($data['content'])) {
            $commentModel = new Comment();
            
            if ($commentModel->add($data['content'], $_SESSION['user']['id'], $data['postId'])) {
                
                $postModel = new Post();
                $authorId = $postModel->getAuthorId($data['postId']);

                if ($authorId && $authorId != $_SESSION['user']['id']) {
                    $notifModel = new Notification();
                    $msg = $_SESSION['user']['nom'] . " a commenté votre publication.";
                    $notifModel->create($authorId, $msg);
                }

                echo json_encode([
                    'status' => 'success',
                    'content' => htmlspecialchars($data['content']),
                    'author' => htmlspecialchars($_SESSION['user']['nom']),
                    'score' => 0
                ]);
            } else { echo json_encode(['error' => 'Erreur SQL']); }
        }
        exit;
    }

    public function apiLikePost() {
        $this->checkAjaxAuth();
        $data = json_decode(file_get_contents('php://input'), true);

        if (!empty($data['postId'])) {
            $postModel = new Post();
            $isLike = $postModel->toggleLike($_SESSION['user']['id'], $data['postId']);
            $newCount = $postModel->getLikeCount($data['postId']);

            if ($isLike) {
                $authorId = $postModel->getAuthorId($data['postId']);
                
                if ($authorId && $authorId != $_SESSION['user']['id']) {
                    $notifModel = new Notification();
                    $msg = $_SESSION['user']['nom'] . " a aimé votre publication.";
                    $notifModel->create($authorId, $msg);
                }
            }

            ob_clean();
            echo json_encode(['status' => 'success', 'likes' => $newCount]);
        }
        exit;
    }


    public function apiVoteComment() {
        $this->checkAjaxAuth();
        $data = json_decode(file_get_contents('php://input'), true);

        if (!empty($data['commentId']) && isset($data['value'])) {
            $commentModel = new Comment();
            $commentModel->vote($_SESSION['user']['id'], $data['commentId'], (int)$data['value']);
            $newScore = $commentModel->getScore($data['commentId']);
            echo json_encode(['status' => 'success', 'score' => $newScore]);
        }
        exit;
    }

    public function apiSearch() {
        if (!isset($_SESSION['user'])) exit;
        
        $query = $_GET['q'] ?? '';
        if (strlen($query) > 1) {
            $postModel = new Post();
            $results = $postModel->search($query);
            
            foreach ($results as $res) {
                echo '<div class="search-result-item" style="padding:10px; cursor:pointer; border-bottom:1px solid #eee;">';
                echo '<strong>' . htmlspecialchars($res['titre']) . '</strong> par ' . htmlspecialchars($res['auteur']);
                echo '<span style="display:none;">' . htmlspecialchars($res['contenu']) . '</span>';
                echo '</div>';
            }
        }
        exit;
    }

    private function checkAjaxAuth() {
        header('Content-Type: application/json');
        if (!isset($_SESSION['user'])) {
            echo json_encode(['error' => 'Non connecté']);
            exit;
        }
    }

    public function apiEdit() {
        header('Content-Type: application/json');

        if (!isset($_SESSION['user'])) {
            echo json_encode(['error' => 'Non connecté']);
            exit;
        }

        $json = file_get_contents('php://input');
        $data = json_decode($json, true);

        if (!empty($data['id']) && !empty($data['content']) && !empty($data['type'])) {
            
            if ($data['type'] === 'comment') {
                $commentModel = new Comment();
                
                $authorId = $commentModel->getAuthorId($data['id']);

                if ($authorId == $_SESSION['user']['id']) {
                    $success = $commentModel->update($data['id'], $data['content']);
                    
                    if ($success) {
                        echo json_encode(['status' => 'success']);
                    } else {
                        echo json_encode(['error' => 'Erreur SQL']);
                    }
                } else {
                    echo json_encode(['error' => 'Action non autorisée : Vous n\'êtes pas l\'auteur.']);
                }
            }
        } else {
            echo json_encode(['error' => 'Données incomplètes']);
        }
        exit;
    }

    public function apiNotifications() {
        $this->checkAjaxAuth();
        $notifModel = new Notification();
        $count = $notifModel->getUnreadCount($_SESSION['user']['id']);

        ob_clean();

        echo json_encode(['count' => $count]);
        exit;
    }

    public function apiMarkRead() {
        $this->checkAjaxAuth();
        $notifModel = new Notification();
        $notifModel->markAllAsRead($_SESSION['user']['id']);
        echo json_encode(['status' => 'success']);
        exit;
    }

    public function apiGetNotificationsList() {
        $this->checkAjaxAuth();
        $notifModel = new Notification();
        $notifs = $notifModel->getAllUnread($_SESSION['user']['id']);
        
        $html = '';
        if (empty($notifs)) {
            $html = '<div style="padding:10px; color:#888;">Aucune nouvelle notification</div>';
        } else {
            foreach ($notifs as $n) {
                $html .= '<div style="padding:10px; border-bottom:1px solid #eee; font-size:0.9em;">' . htmlspecialchars($n['message']) . '</div>';
            }
        }

        ob_clean();
        echo json_encode(['html' => $html, 'count' => count($notifs)]);
        exit;
    }
}