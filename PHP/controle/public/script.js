document.addEventListener('DOMContentLoaded', () => {
    
   const themeBtn = document.getElementById('theme-toggle');
    const body = document.body;

    if (localStorage.getItem('theme') === 'dark') {
        body.classList.add('dark-mode');
        if (themeBtn) themeBtn.textContent = '☀️';
    }

    if (themeBtn) {
        const newBtn = themeBtn.cloneNode(true);
        themeBtn.parentNode.replaceChild(newBtn, themeBtn);

        newBtn.addEventListener('click', (e) => {
            e.preventDefault();
            e.stopPropagation();
            e.stopImmediatePropagation(); 

            body.classList.toggle('dark-mode');

            const isDark = body.classList.contains('dark-mode');
            localStorage.setItem('theme', isDark ? 'dark' : 'light');
            
            newBtn.textContent = isDark ? '☀️' : '🌙';
        });
    }

    const notifBell = document.getElementById('notif-bell');
    const notifBadge = document.getElementById('notif-badge');
    const notifDropdown = document.getElementById('notif-dropdown');

    const checkNotifications = async () => {
        if (notifDropdown && notifDropdown.style.display === 'block') {
            return; 
        }

        try {
            const res = await fetch('index.php?action=api_notifications');
            const data = await res.json();
            if (data.count > 0) {
                if(notifBadge) {
                    notifBadge.innerText = data.count;
                    notifBadge.style.display = 'inline-block'; 
                }
            } else {
                if(notifBadge) notifBadge.style.display = 'none';
            }
        } catch (err) {
            console.error("Erreur check notif", err);
        }
    };
    setInterval(checkNotifications, 10000);
    checkNotifications();

    if (notifBell) {
        notifBell.addEventListener('click', async (e) => {
            e.stopPropagation();
            e.preventDefault();

            if (notifDropdown.style.display === 'block') {
                notifDropdown.style.display = 'none';
                return;
            }

            notifDropdown.style.display = 'block';
            notifDropdown.innerHTML = '<div style="padding:10px; text-align:center;">Chargement...</div>';

            if (notifBadge) {
                notifBadge.style.display = 'none'; 
                notifBadge.innerText = '0';        
            }

            try {
                fetch('index.php?action=api_mark_read');

                const res = await fetch('index.php?action=api_notif_list');
                const data = await res.json();
                
                notifDropdown.innerHTML = data.html;

            } catch (err) {
                console.error("Erreur chargement notifs :", err);
                notifDropdown.innerHTML = '<div style="padding:10px; color:red;">Erreur de chargement</div>';
            }
        });
    }

    document.addEventListener('click', (e) => {
        if (notifDropdown && notifDropdown.style.display === 'block') {
            if (!notifDropdown.contains(e.target) && e.target !== notifBell) {
                notifDropdown.style.display = 'none';
            }
        }
    });


    document.body.addEventListener('click', async (e) => {
        const btn = e.target.closest('.btn-vote');

        if (btn) {
            e.preventDefault();      
            e.stopPropagation();     
            e.stopImmediatePropagation(); 

            if (btn.dataset.loading === "true") {
                return; 
            }
            btn.dataset.loading = "true"; 

            const commentId = btn.dataset.id;
            const value = btn.dataset.val;

            if (commentId === 'new') { 
                alert("Rechargez la page pour voter sur ce nouveau commentaire."); 
                btn.dataset.loading = "false"; 
                return; 
            }

            try {
                const res = await fetch('index.php?action=api_vote', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ commentId, value })
                });

                if (!res.ok) throw new Error('Erreur réseau');

                const data = await res.json();

                if (data.status === 'success') {
                    const scoreSpan = document.getElementById(`score-${commentId}`);
                    if (scoreSpan) {
                        scoreSpan.textContent = data.score;
                        
                        scoreSpan.style.fontWeight = "bold";
                        setTimeout(() => scoreSpan.style.fontWeight = "normal", 300);
                    }
                } else {
                    console.error("Erreur vote:", data);
                }
            } catch (err) {
                console.error("Erreur JS Vote :", err);
            } finally {
                btn.dataset.loading = "false";
            }
        }
    });

    document.body.addEventListener('click', async (e) => {
        const btn = e.target.closest('.btn-like');

        if (btn) {
            e.preventDefault();
            e.stopPropagation(); 

            if (btn.dataset.loading === "true") {
                console.log("Requete déjà en cours, clic ignoré.");
                return; 
            }


            btn.dataset.loading = "true";
            btn.style.opacity = "0.5"; 

            const postId = btn.dataset.postId || btn.dataset.id; 

            try {
                const res = await fetch('index.php?action=api_like', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ postId })
                });

                if (!res.ok) throw new Error('Erreur serveur');

                const data = await res.json();

                if (data.status === 'success') {
                    const countSpan = document.getElementById(`like-count-${postId}`);
                    
                    if (countSpan) {
                        countSpan.textContent = data.likes;
                        
                        countSpan.style.color = "#007bff";
                        setTimeout(() => countSpan.style.color = "", 500);
                    }
                } else {
                    console.error("Erreur API:", data);
                }

            } catch (err) {
                console.error("Erreur Like :", err);
            } finally {
                btn.dataset.loading = "false";
                btn.style.opacity = "1";
            }
        }
    });

    document.body.addEventListener('click', async (e) => {
        if (e.target.classList.contains('btn-vote')) {
            const btn = e.target;
            const commentId = btn.dataset.id;
            const value = btn.dataset.val;

            if (commentId === 'new') { alert("Rechargez la page pour voter sur ce nouveau commentaire."); return; }

            const res = await fetch('index.php?action=api_vote', {
                method: 'POST',
                body: JSON.stringify({ commentId, value })
            });
            const data = await res.json();

            if (data.status === 'success') {
                document.getElementById(`score-${commentId}`).textContent = data.score;
            }
        }
    });

    const searchInput = document.getElementById('live-search');
    const searchResults = document.getElementById('search-results');

    if (searchInput) {
        searchInput.addEventListener('input', async (e) => {
            const query = e.target.value.toLowerCase();
            
            if (query === "") {
                searchResults.style.display = 'none'; 
                document.querySelectorAll('.post').forEach(post => {
                    post.style.display = 'block';
                });
                return; 
            }

            if (query.length < 2) {
                searchResults.style.display = 'none';
                return;
            }

            const res = await fetch(`index.php?action=api_search&q=${encodeURIComponent(query)}`);
            const html = await res.text();

            searchResults.innerHTML = html;
            searchResults.style.display = 'block';
            const posts = document.querySelectorAll('.post');

            posts.forEach(post => {
                const textContent = post.textContent.toLowerCase();
                if (textContent.includes(query)) {
                    post.style.display = 'block';
                } else {
                    post.style.display = 'none';
                }
            });

            document.addEventListener('click', (e) => {
                if (e.target !== searchInput) searchResults.style.display = 'none';
            });
        });
    }
});