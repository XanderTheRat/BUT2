use tokio::io::AsyncBufReadExt;
use tokio::io::BufReader;
use tokio::io::AsyncWriteExt;
use tokio::net::TcpListener;
use clap::Parser;

#[derive(Parser)]
struct Args {
	port : usize,
}

#[tokio::main]
async fn main() -> anyhow::Result<()> {
	const LOCALHOST: &str = "127.0.0.1";
	let args = Args::parse();
	let listener = TcpListener::bind(format!("{}:{}", LOCALHOST, args.port)).await?;
	loop {
		let (mut stream, _) = listener.accept().await?;
		tokio::spawn(async move {
			let (reader, mut writer) = stream.into_split();
			let mut lines = BufReader::new(reader).lines();
			if let Some(line) = lines.next_line().await? {
				writer.write_all(line.as_bytes()).await?;
				writer.flush().await?;
				println!("{}", line);
			}
			anyhow::Ok(())
		});
	}

}