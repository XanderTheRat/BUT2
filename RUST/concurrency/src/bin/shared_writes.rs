use clap::Parser;
use std::sync::Arc;
use std::sync::RwLock;

#[tokio::main]
async fn main() {
    let args: Args = Args::parse();

    let mut handles= Vec::new();
    let counter = Arc::new(RwLock::new(0));
    if args.n>0 {

        for _ in 0..args.n {
        	let c = counter.clone();
            handles.push(tokio::spawn(async move {
                print_args_async(c).await;
            }));
        }
        for handle in handles {
            let _ = handle.await;
        }

    }
}

#[derive(Parser)]
struct Args {
    n : usize
}

async fn print_args_async(counter: Arc<RwLock<usize>>) {
	let mut n = counter.write().unwrap();
	*n += 1;	
	println!("Bonjour {}", *n);
	println!("Au revoir {}", *n);    
}
