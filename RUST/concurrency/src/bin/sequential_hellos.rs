use clap::Parser;

fn main() {
    let args: Args = Args::parse();


    if args.n >0 {
        for i in 0..args.n {
            println!("Bonjour {}", i);
            println!("Au revoir {}", i);
        }
    }
}

#[derive(Parser)]
struct Args {
    n : usize
}