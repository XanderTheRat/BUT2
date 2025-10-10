<?php
require_once('models/Database.php');
require_once('controllers/RecetteController.php');
require_once('views/home/header.php');


$homeController='controllers/homeController.php';
$listerController='controllers/listerController.php';
$contactController='controllers/contactController.php';
$ajoutController='controllers/ajoutController.php';
$enregistrerController='controllers/enregistrerController.php';

$controller=isset($_GET['c']) ? $_GET['c'] : 'home';
$action=isset($_GET['a']) ? $_GET['a'] : 'index';

var_dump($controller);
var_dump($action);
switch ($controller) {
    case 'Recettes':
        $recetteController = new RecetteController();
        switch ($action) {
            case 'index' :
                echo "toto";
                $recetteController->index();
                break;
            case 'ajout' :
                $recetteController->ajouter();
                break;
            case 'enregistrer' :
                $recetteController->enregistrer();
                break;
            default :
                echo "Erreur 404. Le fichier est introuvable";
        }
        break;
    case 'Contact' :
        echo "Erreur 404. Le fichier est introuvable";
        break;
    case 'home':
        require_once($homeController);
        break;
    default:
        echo "Erreur 404. Le fichier est introuvable";
}

require_once('views/home/footer.php');
