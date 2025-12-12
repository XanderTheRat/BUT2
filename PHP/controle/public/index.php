<?php
session_start();

require_once __DIR__ . '/../vendor/autoload.php';

use App\Controller\AuthController;
use App\Controller\SocialController;

$action = $_GET['action'] ?? 'home';

switch($action) {
    case 'register': 
        (new AuthController())->register(); 
        break;
        
    case 'login':    
        (new AuthController())->login(); 
        break;
        
    case 'logout':   
        (new AuthController())->logout(); 
        break;

    case 'add_post': 
        (new SocialController())->addPost(); 
        break;
    
    case 'api_comment': 
        (new SocialController())->apiAddComment(); 
        break;

    case 'api_like':    
        (new SocialController())->apiLikePost(); 
        break;

    case 'api_vote':    
        (new SocialController())->apiVoteComment(); 
        break;

    case 'api_search':  
        (new SocialController())->apiSearch(); 
        break;

    case 'api_edit':    
        (new SocialController())->apiEdit(); 
        break;

    case 'api_notifications': 
        (new SocialController())->apiNotifications(); 
        break;

    case 'api_mark_read':     
        (new SocialController())->apiMarkRead(); 
        break;

    case 'api_notif_list': 
        (new SocialController())->apiGetNotificationsList(); 
        break;

    case 'home':
    default: 
        (new SocialController())->index(); 
        break;
}