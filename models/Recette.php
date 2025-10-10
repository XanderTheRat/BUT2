<?php
require_once("Database.php");
class Recette
{
    private $conn;

    function _construct() {
        $database = new Database();
        $this->conn = $database->getConnection();
    }

    public function afficherToutesLesRecettes() {
        $query = "SELECT * FROM recette";
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function afficherRecette($id) {
        $query = "SELECT * FROM recette WHERE id = `$id`";
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt->fetch(PDO::FETCH_ASSOC);
    }

    public function chercherPar(array $params) {
        $query = "SELECT * FROM recette WHERE ". implode(' AND ', array_map(function($key) {
            return "`$key` = :$key";
            }, array_keys($params)));

        $stmt = $this->conn->prepare($query);
        $stmt->execute($params);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function ajouterRecette($titre, $description, $auteur) {
        $query = "INSERT INTO recettes (titre, description, auteur, date_creation) VALUES (:titre, :description, :auteur, NOW())";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':titre', $titre);
        $stmt->bindParam(':description', $description);
        $stmt->bindParam(':auteur', $auteur);
        $stmt->execute();
        return $this->conn->lastInsertId();
    }

    public function modifierRecette($id, $titre, $description, $auteur, $image) {
        $query = "UPDATE recette SET titre = :titre, description = :description, auteur = :auteur, image = :image WHERE id = :id";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':id', $id);
        $stmt->bindParam(':titre', $titre);
        $stmt->bindParam(':description', $description);
        $stmt->bindParam(':auteur', $auteur);
        $stmt->bindParam(':image', $image);
        return $stmt->execute();
    }

    public function supprimerRecette($id) {
        $query = "DELETE FROM recette WHERE id = :id";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':id', $id);
        $stmt->execute();
        return $stmt->rowCount() > 0;
    }
}