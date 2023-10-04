
extern crate reqwest;
extern crate dotenv;

use deadpool_postgres::{Config, PoolConfig, Runtime, Timeouts};
use tokio_postgres::NoTls;
use deadpool_redis::{ConnectionAddr, ConnectionInfo, RedisConnectionInfo};
use dotenv::dotenv;
use reqwest::header;
use tokio;
use std::env;
use std::{sync::Arc, time::Duration};

mod auth_service;
mod db;

use db::*;
use crate::auth_service::get_api_key;

#[tokio::main]
async fn main() -> AsyncVoidResult {
    dotenv().ok(); // Carrega as variáveis de ambiente do arquivo .env
    let api_key =  auth_service::get_api_key().unwrap();
    let url = format!(
        "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=100&q=Python%20for%20beginners&key={}",
        api_key
    );

    //Configurando conexão com o banco
    let mut config = Config::new();
    config.host = Some(
        env::var("POSTGRES_HOST").unwrap_or("localhost".into())
        .to_string(),
    );
    config.port = Some(5432);
    config.dbname = Some(env::var("POSTGRES_NAME").unwrap());
    config.user = Some(env::var("POSTGRES_USER").unwrap());
    config.password = Some(env::var("POSTGRES_PASSWORD").unwrap());

    let pool_size: usize = env::var("POOL_SIZE")
        .unwrap_or("125".to_string())
        .parse::<usize>()
        .unwrap();

    config.pool = PoolConfig::new(pool_size).into();
    println!("Criando conxão com o PostgreSQL...");
    let pool = config.create_pool(Some(Runtime::Tokio1), NoTls)?;
    println!("Conexão com o PostgreSQL: Criada com sucesso...");


    // Configurando conexão com o redis
    let mut cfg = deadpool_redis::Config::default();
    let redis_host = env::var("REDIS_HOST").unwrap_or("0.0.0.0".into());
    cfg.connection = Some(ConnectionInfo {
        addr: ConnectionAddr::Tcp(redis_host, 6379),
        redis: RedisConnectionInfo {
            db: 0,
            username: None,
            password: None,
        },
    });
    cfg.pool = Some(PoolConfig {
        max_size: 9995,
        timeouts: Timeouts {
            wait: Some(Duration::from_secs(60)),
            create: Some(Duration::from_secs(60)),
            recycle: Some(Duration::from_secs(60)),
        },
    });
    println!("creating redis pool...");
    let redis_pool = cfg.create_pool(Some(Runtime::Tokio1))?;
    println!("redis pool succesfully created");

    // Chamada na API --------------------------------------------------------
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
    // println!("{}", body);

    Ok(())
}