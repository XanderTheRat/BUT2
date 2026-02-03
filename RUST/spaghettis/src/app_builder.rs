use crate::configurations::Configuration;
use crate::domain::{VotingMachine, ScoreBoard, AttendanceSheet, Voter, Candidate, BallotPaper, VoteOutcome};
use std::collections::BTreeSet as Set;
use std::io::{stdin, stdout, Write};
use anyhow::Result;

pub async fn run_app(configuration : Configuration) -> Result<()> {
	

    let mut machine = create_voting_machine(&configuration);

    let mut has_already_voted: Set<Voter> = Set::new();

    println!("Bienvenue dans le système de vote ! Tapez ':q' pour quitter.");
    println!("Candidats disponibles : {:?}", configuration.get_candidates());

    loop {
        let input = match get_input() {
            Ok(val) => val,
            Err(_) => {
                println!("Erreur de lecture.");
                continue;
            }
        };

        if input.is_empty() {
            print_help();
            continue;
        }

        if input == ":q" {
            println!("---");
            break;
        }

        let parts: Vec<&str> = input.split_whitespace().collect();
        let command = parts[0];

        match command {
            "votants" => {
                println!("- Les votants inscrits sont :");
                for votant in &machine.get_voters().0 {
                    println!("- {}", votant.0);
                }
            },
            "scores" => {
                let sb = machine.get_scoreboard();
                println!("- Scores actuels ");
                for (candidat, score) in &sb.scores {
                    println!("{}: {} voix", candidat.0, score.0);
                }
                println!("Votes Blancs : {}", sb.blank_score.0);
                println!("Votes Invalides : {}", sb.invalid_score.0);
            },
            "voter" => {
                if parts.len() < 2 {
                    println!("- Usage : voter <NOM_VOTANT> [NOM_CANDIDAT]");
                } else {
                    let voter = Voter(parts[1].to_string());

                    if has_already_voted.contains(&voter) {
                        println!("- Erreur : {} a déjà voté !", voter.0);
                        continue;
                    }

                    let candidate_opt = if parts.len() >= 3 {
                        Some(Candidate(parts[2].to_string()))
                    } else {
                        None 
                    };
                    
                    let ballot = BallotPaper {
                        voter: voter.clone(),
                        candidate: candidate_opt,
                    };

                    let outcome = machine.vote(ballot);

                    match outcome {
                        VoteOutcome::AcceptedVote(v, c) => {
                            println!("A voté pour {} !", c.0);
                            has_already_voted.insert(v);
                        },
                        VoteOutcome::BlankVote(v) => {
                            println!("Vote Blanc ou Nul enregistré pour {}.", v.0);
                            has_already_voted.insert(v);
                        },
                        VoteOutcome::InvalidVoter(v) => {
                            println!("Erreur : '{}' n'est pas sur la liste des inscrits.", v.0);
                        },
                        VoteOutcome::HasAlreadyVoted(_) => {
                            println!("Erreur : A déjà voté.");
                        }
                    }
                }
            },
            _ => println!("- Commande inconnue : '{}'", command),
        }
        println!();
    }
	Ok(())
}

fn create_voting_machine(configuration: &Configuration) -> VotingMachine {
    let candidates_domain: Vec<Candidate> = configuration.get_candidates()
        .into_iter()
        .map(|name| Candidate(name))
        .collect();

    let scoreboard = ScoreBoard::new(candidates_domain);

    let votants_str = ["Tux", "Fedora", "Tix", "Nixos", "Gludul", "Arch"];
    let mut votants_set = Set::new();
    for v in votants_str {
        votants_set.insert(Voter(v.to_string()));
    }
    let attendance_sheet = AttendanceSheet(votants_set);

    VotingMachine::new(attendance_sheet, scoreboard)
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