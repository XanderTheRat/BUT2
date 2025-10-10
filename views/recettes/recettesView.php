<body>
    <h1>Recettes</h1>

    <div class="row">
        <?php foreach($recettes as $recette): ?>
        <div class="col-4 p-2">
            <div class="card">
                <div class="card-body">
                    <h2 class="card-title"><?php echo $recette['titre']; ?></h2>
                    <p class="card-text"><?php echo $recette['description']; ?></p>
                    Auteur : <a href="mailto: <?php echo $recette['email']; ?>"><?php echo $recette['email']; ?></a>
                </div>
            </div>
        </div>
        <?php endforeach; ?>
    </div>
    <a href="?c=home" class="btn btn-primary">Retour à l'accueil</a>
</body>
