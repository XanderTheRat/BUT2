use clap::Parser;
use tokio::net::UdpSocket;


#[tokio::main]
async fn main() -> anyhow::Result<()>{
	const PING:&str = "PING\n";

	let args = Args::parse(); 

	let host = args.ip;
	let port = args.port;

	let socket = UdpSocket::bind("127.0.0.1:0").await?;

	socket.send_to(PING.as_bytes(), format!("{}:{}", host, port)).await?;

	Ok(())
}

#[derive(Parser)]
struct Args {
	ip : String,
	port : String,
}