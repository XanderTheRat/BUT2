use clap::Parser;
use tokio::net::UdpSocket;


#[derive(Parser)]
struct Args {
	port : usize,
}

#[tokio::main]
async fn main() -> anyhow::Result<()>{
	const PONG : &str = "PONG\n";
	const LOCALHOST : &str = "127.0.0.1";

	let args = Args::parse();
	let port = args.port;

	let listener = UdpSocket::bind(format!("{}:{}", LOCALHOST, port)).await?;
	
	let mut buf = [0u8; 1024];
	let (_, sender_endpoint) = listener.recv_from(&mut buf).await?;
	println!("{} from {}", PONG, sender_endpoint);

	Ok(())
}