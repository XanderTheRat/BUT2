use clap::Parser;
use std::sync::Arc;

#[tokio::main]
async fn main() {
    let args: Args = Args::parse();
    let bjr:String = String::from("Bonjour");

    let mut handles= Vec::new();
    if args.n>0 {

        for i in 0..args.n {
            let valeur = Arc::new(bjr.clone());

            handles.push(tokio::spawn(async move {
                print_args_async(valeur.clone(), i);
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

fn print_args_async(bjr:Arc<String>, n: usize) {
    println!("{} {}", bjr, n);
    println!("Au revoir {}", n);
}