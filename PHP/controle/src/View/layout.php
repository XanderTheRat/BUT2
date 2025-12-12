<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Social Network</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <header>
        <nav style="display: flex; align-items: center; justify-content: space-between; padding: 10px;">
            <?php if(isset($_SESSION['user'])): ?>
                
                <div>
                    <span class="accueil-span">Bonjour <strong><?= htmlspecialchars($_SESSION['user']['nom']) ?></strong></span>
                </div>
                
                <div style="position: relative;">
                    <input type="text" id="live-search" placeholder="Rechercher..." autocomplete="off">
                    <div id="search-results" style="position: absolute; background: white; border: 1px solid #ccc; width: 100%; display: none; z-index: 1000;"></div> 
                </div>

                <div style="display: flex; align-items: center;">

                    <div class="notif-container" style="position: relative; margin-right: 15px;">
                        
                        <button id="notif-bell" style="background: #e4e6eb; border:none; cursor:pointer; font-size:1.2em;">
                            🔔 
                            <span id="notif-badge" style="background:red; color:white; padding:2px 5px; border-radius:50%; font-size:10px; position:absolute; top:-5px; right:-5px; display:none;">0</span>
                        </button>

                        <div id="notif-dropdown" style="
                            display: none;
                            position: absolute;
                            right: 0;
                            top: 30px;
                            background: white;
                            border: 1px solid #ccc;
                            width: 250px;
                            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                            z-index: 1000;
                            border-radius: 5px;
                            color: black;
                        ">
                            <div style="padding:10px; text-align:center;">Chargement...</div>
                        </div>
                    </div>

                    <button id="theme-toggle" style="background: #e4e6eb; border:none; cursor:pointer; font-size:1.2em; margin-right: 15px;">🌙</button>

                    <a href="index.php?action=logout">Déconnexion</a>
                </div>

            <?php else: ?>
                <div>
                    <a href="index.php?action=login" style="margin-right: 10px;">Connexion</a>
                </div>
            <?php endif; ?>
        </nav>
    </header>

    <main class="container">
        <?= $content ?>
    </main>

    <script src="script.js"></script>
</body>
</html>