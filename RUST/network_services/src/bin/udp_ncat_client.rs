use clap::Parser;
use tokio::net::UdpSocket;
use std::io;


#[tokio::main]
async fn main() -> anyhow::Result<()>{
	let args = Args::parse(); 

	let host = args.ip;
	let port = args.port;

	loop {
		let mut buffer = String::new();
		io::stdin().read_line(&mut buffer)?;

		udp_call(host.clone(), port.clone(), buffer).await?;
	}
}

#[derive(Parser)]
struct Args {
	ip : String,
	port : String,
}

async fn udp_call(address : String, port:String, message:String) -> anyhow::Result<()> {

	let socket = UdpSocket::bind("127.0.0.1:0").await?;

	socket.send_to(message.as_bytes(), format!("{}:{}", address, port)).await?;

	Ok(())
}