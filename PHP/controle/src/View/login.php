<h2>Connexion</h2>
<?php if(isset($error)): ?>
    <div class="alert error"><?= $error ?></div>
<?php endif; ?>

<form method="post" class="auth-form">
    <div class="form-group">
        <label>Email</label>
        <input type="email" name="email" required>
    </div>
    <div class="form-group">
        <label>Mot de passe</label>
        <input type="password" name="password" required>
    </div>
    <button type="submit">Se connecter</button>
</form>