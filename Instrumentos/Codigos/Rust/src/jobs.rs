

pub async fn db_warmup() {
    println!("Database warming up...");

    tokio::time::sleep(Duration::from_secs(3)).await;
    let http_client = reqwest::Client::new();
}