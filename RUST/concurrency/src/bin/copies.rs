use clap::Parser;
use std::sync::Arc;
use std::sync::RwLock;

#[tokio::main]
async fn main() {
    let args: Args = Args::parse();

    let mut handles= Vec::new();
    let mut total = 0;
    if args.n>0 {

        for _ in 0..args.n {
            handles.push(tokio::spawn(async move {
                print_args_async(total).await;
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

async fn print_args_async(mut n:i32) -> i32 {
	n += 1;	
	
	println!("Bonjour {}", n);
	println!("Au revoir {}", n);
	n    
}
