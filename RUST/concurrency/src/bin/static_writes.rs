use clap::Parser;

#[tokio::main]
async fn main() {
    let args: Args = Args::parse();

    let mut handles= Vec::new();
    let mut bjr = "Bonjour";
    
    if args.n>0 {
    	bjr = "bonjour";
        for i in 0..args.n {handles.push(tokio::spawn(async move {
                printArgsAsync(bjr, i);
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

fn printArgsAsync(bjr:&str ,n: usize) {
    println!("{} {}", bjr, n);
    println!("Au revoir {}", n);
}
