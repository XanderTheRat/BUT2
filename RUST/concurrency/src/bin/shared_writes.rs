use clap::Parser;
#[tokio::main]
async fn main() {
    let args: Args = Args::parse();
    // printArgs(args.n);

    let mut handles= Vec::new();
    if args.n>0 {

        for i in 0..args.n {
            handles.push(tokio::spawn(async move {
                printArgsAsync(i);
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

fn printArgsAsync(n: usize) {
    println!("Bonjour {}", n);
    println!("Au revoir {}", n);
}