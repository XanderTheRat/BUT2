<h2>Fil d'actualité</h2>

<section class="publish-box">
    <h3>Quoi de neuf ?</h3>
    <form method="post" action="index.php?action=add_post">
        <input type="text" name="titre" placeholder="Titre" required>
        <textarea name="contenu" placeholder="Contenu..." required></textarea>
        <button type="submit">Publier</button>
    </form>
</section>
<hr>

<section class="feed">
    <?php foreach($posts as $post): ?>
        <article class="post">
            <div class="post-header">
                <h3><?= htmlspecialchars($post['titre']) ?></h3>
                <small>Par <?= htmlspecialchars($post['auteur']) ?> le <?= $post['date_publication'] ?></small>
            </div>
            <div class="post-body"><?= nl2br(htmlspecialchars($post['contenu'])) ?></div>
            
            <div class="post-actions" style="margin: 10px 0;">
                <button class="btn-like" data-post-id="<?= $post['id'] ?>">
                    ❤️ (<span id="like-count-<?= $post['id'] ?>"><?= $post['like_count'] ?></span>)
                </button>
            </div>

            <div class="post-footer">
                <h4>Commentaires</h4>
                <div id="comments-list-<?= $post['id'] ?>">
                <?php if (!empty($post['comments'])): ?>
                    <?php foreach($post['comments'] as $comment): ?>
                        
                        <div class="comment-item">
                            <div class="vote-box">
                                <button class="btn-vote" data-id="<?= $comment['id'] ?>" data-val="1">▲</button>
                                <span id="score-<?= $comment['id'] ?>"><?= $comment['score'] ?></span>
                                <button class="btn-vote" data-id="<?= $comment['id'] ?>" data-val="-1">▼</button>
                            </div>

                            <?php 
                                $isAuthor = isset($_SESSION['user']) && ($_SESSION['user']['id'] == $comment['utilisateur_id']); 
                            ?>

                            <div class="comment-content <?= $isAuthor ? 'editable' : '' ?>" 
                                 data-type="comment" 
                                 data-id="<?= $comment['id'] ?>">
                                
                                <strong><?= htmlspecialchars($comment['auteur']) ?></strong>: 
                                <?= htmlspecialchars($comment['contenu']) ?>
                                
                                <?php if($isAuthor): ?>
                                    <span style="font-size:0.7em; color:#aaa; margin-left:5px;">(cliquez pour éditer - Remplacez toute la ligne)</span>
                                <?php endif; ?>
                            </div>
                        </div>

                    <?php endforeach; ?>
                <?php endif; ?>
            </div>

                <form class="js-comment-form" data-post-id="<?= $post['id'] ?>">
                    <input type="text" name="content" placeholder="Commenter..." required>
                    <button type="submit">Envoyer</button>
                </form>
            </div>
        </article>
    <?php endforeach; ?>
</section>
<script src="script.js"></script>