<h1>Ajouter une recette</h1>
<form action="?c=Recettes&a=enregistrer" method="post">
    <div class="mb3">
        <label for="titre" class="form-label">Nom de la recette</label>
        <input type="text" class="form-control" id="titre" name="titre" required>
    </div>
    <div class="mb3">
        <label for="description" class="form-label">Description de la recette</label>
        <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
    </div>
    <div class="mb3">
        <label for="auteur" class="form-label">Auteur</label>
        <input type="email" class="form-control" name="auteur" id="auteur" required>
    </div>
    <div class="mb3">
        <button type="submit" class="btn btn-primary" id="enregistrer">Enregistrer</button>
    </div>
</form>
