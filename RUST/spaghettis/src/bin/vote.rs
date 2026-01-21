use std::io::{stdin, stdout, Write};
use std::collections::BTreeMap as Map;

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let votants = ["Tux", "Fedora", "Tix", "Nixos", "Gludul", "Arch"];
    let mut a_voter: Vec<String> = Vec::new(); 
    
    let mut scores: Map<String, i32> = Map::new();
    scores.insert("Fedora".to_string(), 30);
    scores.insert("Tux".to_string(), 12);
    scores.insert("Arch".to_string(), 0);

    println!("Bienvenue dans le système de vote ! Tapez ':q' pour quitter.");

    loop {
        let input = match get_input() {
            Ok(val) => val,
            Err(_) => {
                println!("Erreur de lecture.");
                "".to_string()
           }
        };

        if input.is_empty() {
            print_help();
        }

        if input == ":q" {
            println!("Au revoir !");
            break;
        }

        let parts: Vec<&str> = input.split_whitespace().collect();
        let command = parts[0]; 

        match command {
            "votants" => {
                println!("Les votants inscrits sont :");
                for votant in votants.iter() {
                    println!("- {}", votant);
                }
            },
            "scores" => {
                println!("Scores actuels :");
                for (candidat, score) in &scores {
                    println!("{}: {} voix", candidat, score);
                }
            },
            "voter" => {
                if parts.len() < 3 {
                    println!("Erreur : Utilisation correcte -> voter <NOM_VOTANT> <NOM_CANDIDAT>");
                }else {
                	let nom_votant = parts[1];
                	let nom_candidat = parts[2];

                	if !votants.contains(&nom_votant) {
                    	println!("Erreur : '{}' n'est pas sur la liste des inscrits.", nom_votant);
                	}

                	if a_voter.contains(&nom_votant.to_string()) {
                    	println!("Erreur : {} a déjà voté !", nom_votant);
                	}

                	if let Some(score) = scores.get_mut(nom_candidat) {
                    	*score += 1;    
                	} 
                	a_voter.push(nom_votant.to_string());
                	println!("A voté !");
                }
            },
            _ => println!("Commande inconnue : '{}'", command),
        }
        println!(); 
    }

    Ok(())
}

fn print_help() {
    println!("Les commandes disponibles sont :");
    println!("- voter <nom> <candidat> : Permet de voter");
    println!("- votants              : Affiche la liste des votants");
    println!("- scores               : Affiche les scores des candidats");
    println!("- :q                   : Quitte le programme");
}

fn get_input() -> Result<String, std::io::Error> {
    let mut buffer = String::new();
    print!("> ");

    stdout().flush()?;
    stdin().read_line(&mut buffer)?;
    
    Ok(buffer.trim().to_string())
}