<?php
namespace App\Controller;

use App\Model\User;
use App\View\View;
use Exception;

class AuthController {
    
    public function login() {
        $error = null;
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $userModel = new User();
            $user = $userModel->login($_POST['email'], $_POST['password']);
            
            if ($user) {
                $_SESSION['user'] = $user;
                View::redirect('index.php');
            } else {
                $error = "Email ou mot de passe incorrect.";
            }
        }
        View::render('login', ['error' => $error]);
    }

    public function register() {
        $error = null;
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $userModel = new User();
            try {
                $userModel->register($_POST['nom'], $_POST['email'], $_POST['password']);
                View::redirect('index.php?action=login');
            } catch (Exception $e) {
                $error = "Erreur : Cet email est peut-être déjà utilisé.";
            }
        }
        View::render('register', ['error' => $error]);
    }

    public function logout() {
        session_destroy();
        View::redirect('index.php?action=login');
    }
}