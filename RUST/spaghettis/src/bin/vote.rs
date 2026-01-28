use clap::Parser;

use spaghettis::app_builder;
use spaghettis::configurations::Configuration;

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let config = Configuration::parse();
    app_builder::run_app(config).await?;

    Ok(())
}
