use dotenv::dotenv;
use std::env;

pub fn get_api_key() -> Result<String, String>{
    dotenv().ok(); // Carrega as variáveis de ambiente do arquivo .env

    match env::var("API_KEY") {
        Ok(api_key) => Ok(api_key), // Retorna a chave da API se estiver definida
        Err(_) => Err("API_KEY não definida no arquivo .env".to_string()), // Retorna um erro caso não esteja definida
    }
}