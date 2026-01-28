use clap::Parser;

fn valider_nom_candidat(nom: &str) -> Result<String, String> {
    let nom_normalise = nom.trim().to_lowercase();
    
    if nom_normalise == "blanc" || nom_normalise == "nul" {
        return Err(format!("{} est un nom non-disponible.", nom));
    }
    
    Ok(nom.to_string())
}

#[derive(Debug, Parser)]
pub struct Configuration {
    #[arg(short = 'c', long, required = true, num_args = 1.., value_parser = valider_nom_candidat)]
    candidates: Vec<String>,
}

impl Configuration {
    pub fn get_candidates(&self) -> Vec<String> {
        let mut liste_finale = self.candidates.clone();
        
        liste_finale.push("Blanc".to_string());
        liste_finale.push("Nul".to_string());
        
        liste_finale
    }
}