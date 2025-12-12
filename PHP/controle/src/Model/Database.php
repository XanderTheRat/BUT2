<?php
namespace App\Model;

use PDO;
use Exception;

class Database {
    public static function getConnection() {
        try {
            // Remplace par tes accès réels
            $pdo = new PDO('mysql:host=localhost;dbname=social_network;charset=utf8mb4', 'user', 'motDePasse');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            return $pdo;
        } catch (Exception $e) {
            die('Erreur SQL : ' . $e->getMessage());
        }
    }
}