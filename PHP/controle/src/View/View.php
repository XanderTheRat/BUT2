<?php
namespace App\View;

class View {
    public static function render($template, $data = []) {
        extract($data);
        ob_start();
        require __DIR__ . '/' . $template . '.php';
        $content = ob_get_clean();
        require __DIR__ . '/layout.php'; 
    }

    public static function redirect($url) {
        header("Location: $url");
        exit;
    }
}