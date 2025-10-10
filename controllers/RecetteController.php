<?php

class RecetteController
{
    private $recetteModel;

    function _construct() {
        $recetteModel = new Recette();
        $this->recetteModel = $recetteModel;
    }
    function ajouter() {
        require_once("./views/recettes/ajout.php");
    }

    function enregistrer() {
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            $titre = $_POST['titre'];
            $description = $_POST['description'];
            $auteur = $_POST['auteur'];

            if ($titre && $description && $auteur) {
                $ajoutOk = $this->recetteModel->ajouterRecette($titre, $description, $auteur);

                if ($ajoutOk) {
                    require_once("./views/recettes/enregistrement.php");
                } else {
                    echo '<div class="alert alert-danger">Erreur lors de l\'ajout de la recette.</div>';
                }
            } else {
                echo '<div class="alert alert-warning">Tous les champs sont obligatoires.</div>';
            }
        }

    }

    function index() {
        $requete = $this->recetteModel->afficherToutesLesRecettes();

        $recettes = $this->recetteModel->afficherRecette($_GET['id']);

        require_once('./views/recettes/recettesView.php');
    }
}