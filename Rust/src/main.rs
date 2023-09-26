
extern crate reqwest;
extern crate dotenv;

use dotenv::dotenv;
use reqwest::header;
use tokio;

mod auth_service;

use crate::auth_service::get_api_key;

#[tokio::main]
async fn main() -> Result<(), reqwest::Error> {
    dotenv().ok(); // Carrega as variáveis de ambiente do arquivo .env
    let api_key =  auth_service::get_api_key().unwrap();
    let url = format!(
        "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=100&q=Python%20for%20beginners&key={}",
        api_key
    );

    let client = reqwest::Client::new();
    let mut headers = header::HeaderMap::new();
    headers.insert(
        header::ACCEPT,
        header::HeaderValue::from_static("application/json"),
    );

    let response = client
        .get(&url)
        .headers(headers)
        .send()
        .await?; // Use .await para aguardar a conclusão da futura

    let body = response.text().await?;
    println!("{}", body);

    Ok(())
}