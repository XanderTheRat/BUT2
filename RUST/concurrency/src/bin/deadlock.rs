use std::sync::{Arc};
use tokio::sync::RwLock;
use tokio::time::{sleep, Duration};

#[tokio::main]
async fn main() {
	let valeur = 0;
	let verrou_a = Arc::new(RwLock::new(valeur)); 
 	let verrou_b = Arc::new(RwLock::new(valeur));

	let clone_a = verrou_a.clone();
	let clone_b = verrou_b.clone();
 	let tache1 = tokio::spawn(async move {
 		let _var0 = clone_a.write().await;
 		println!("Bonjour 1");
 		sleep(Duration::from_secs(1)).await;
 		let _var1 = clone_b.write().await;
 		println!("Au revoir 1");
 		sleep(Duration::from_secs(1)).await;
 	});

 	let tache2 = tokio::spawn(async move {
 		let _var0 = verrou_b.write().await;
 		sleep(Duration::from_secs(1)).await;
 		let _var1 = verrou_a.write().await;
 		sleep(Duration::from_secs(1)).await;
 	}); 

 	let _ = tokio::join!(tache1, tache2);
}
