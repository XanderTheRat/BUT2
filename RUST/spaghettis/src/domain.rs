use std::collections::BTreeMap as Map;
use std::collections::BTreeSet as Set;

#[derive(Clone, Debug, Ord, PartialOrd, Eq, PartialEq)]
pub struct Voter(pub String);
#[derive(Clone, Debug, Ord, PartialOrd, Eq, PartialEq)]
pub struct Candidate(pub String);
#[derive(Debug)]
pub struct Score(pub usize);
pub struct AttendanceSheet(pub Set<Voter>);

pub struct ScoreBoard{
	pub scores: Map<Candidate, Score>,
	pub blank_score: Score,
	pub invalid_score: Score,
}

pub struct BallotPaper{
	pub voter: Voter,
	pub candidate: Option<Candidate>,
}

pub enum VoteOutcome{
	AcceptedVote(Voter, Candidate),
	BlankVote(Voter),
	InvalidVoter(Voter),
	HasAlreadyVoted(Voter),
}

pub struct VotingMachine{
	voters: AttendanceSheet,
	score_board: ScoreBoard,
}

impl ScoreBoard{
	pub fn new(candidates: Vec<Candidate>) -> Self{
		let score_null = 0usize;
		let mut scores: Map<Candidate, Score> = Map::new();  
		let blank_score = Score(score_null);
		let invalid_score = Score(score_null);

		for cand in candidates {
			scores.insert(cand, Score(score_null));
		}

		Self { scores, blank_score, invalid_score }
	}
}

impl VotingMachine {
	pub fn new(voters: AttendanceSheet, score_board: ScoreBoard) -> Self {
		Self {voters, score_board}
	}	

	pub fn vote(&mut self, ballot_paper: BallotPaper) -> VoteOutcome{
		let voter = ballot_paper.voter;
		let candidate_option = ballot_paper.candidate;

		if !self.voters.0.contains(&voter) {
			return VoteOutcome::InvalidVoter(voter);
		}

		match candidate_option {
			Some(candidate) => {
				if let Some(score) = self.score_board.scores.get_mut(&candidate) {
					score.0 += 1;
					VoteOutcome::AcceptedVote(voter, candidate)
				}else {
					self.score_board.invalid_score.0 += 1;
					VoteOutcome::BlankVote(voter)
				}
			},
			None => {
				self.score_board.blank_score.0 +=1;
				VoteOutcome::BlankVote(voter)
			}
		}
	}

	pub fn get_scoreboard(&self) -> &ScoreBoard {&self.score_board}
	pub fn get_voters(&self) -> &AttendanceSheet {&self.voters}
}



#[cfg(test)]
mod tests {
	use crate::domain::AttendanceSheet;
	use crate::domain::BallotPaper;
	use crate::domain::VoteOutcome;
	use crate::domain::VotingMachine;
	use crate::domain::Candidate;
	use crate::domain::ScoreBoard;
	use std::collections::BTreeSet;
	use crate::domain::Voter;

#[test]
	fn it_works() {
		assert_eq!(1 + 1, 2);
	}

	fn create_test_machine() -> VotingMachine {
        let candidates = vec![
            Candidate("Tux".to_string()),
            Candidate("Arch".to_string()),
        ];
        
        let scoreboard = ScoreBoard::new(candidates);

        let mut voters_set = BTreeSet::new();
        voters_set.insert(Voter("Alice".to_string()));
        voters_set.insert(Voter("Bob".to_string()));
        voters_set.insert(Voter("Charlie".to_string()));
        
        let attendance = AttendanceSheet(voters_set);

        VotingMachine::new(attendance, scoreboard)
    }

    #[test]
    fn test_accepted_vote_increment_score() {
        let mut machine = create_test_machine();
        
        let ballot = BallotPaper {
            voter: Voter("Alice".to_string()),
            candidate: Some(Candidate("Tux".to_string())),
        };

        let outcome = machine.vote(ballot);

        match outcome {
            VoteOutcome::AcceptedVote(v, c) => {
                assert_eq!(v.0, "Alice");
                assert_eq!(c.0, "Tux");
            },
            _ => panic!("Devrait être un vote accepté"),
        }
        let sb = machine.get_scoreboard();
        let tux_score = sb.scores.get(&Candidate("Tux".to_string())).unwrap();
        assert_eq!(tux_score.0, 1);
    }

    #[test]
    fn test_blank_vote_explicit_none() {
        let mut machine = create_test_machine();

        let ballot = BallotPaper {
            voter: Voter("Bob".to_string()),
            candidate: None, 
        };

        let outcome = machine.vote(ballot);
        match outcome {
            VoteOutcome::BlankVote(v) => assert_eq!(v.0, "Bob"),
            _ => panic!("Devrait être un vote blanc"),
        }

        assert_eq!(machine.get_scoreboard().blank_score.0, 1);
    }

    #[test]
    fn test_invalid_candidate_counts_as_invalid_score_but_returns_blank() {
    	let mut machine = create_test_machine();

        let ballot = BallotPaper {
            voter: Voter("Charlie".to_string()),
            candidate: Some(Candidate("Windows".to_string())),
        };

        let outcome = machine.vote(ballot);
        if let VoteOutcome::BlankVote(v) = outcome {
            assert_eq!(v.0, "Charlie");
        } else {
            panic!("Un candidat invalide devrait retourner BlankVote selon votre logique actuelle");
        }
        let sb = machine.get_scoreboard();
        assert_eq!(sb.invalid_score.0, 1);
        assert_eq!(sb.blank_score.0, 0);
    }

    #[test]
    fn test_invalid_voter_rejected() {
        let mut machine = create_test_machine();

        let ballot = BallotPaper {
            voter: Voter("Hacker".to_string()), 
            candidate: Some(Candidate("Tux".to_string())),
        };

        let outcome = machine.vote(ballot);

        match outcome {
            VoteOutcome::InvalidVoter(v) => assert_eq!(v.0, "Hacker"),
            _ => panic!("Devrait rejeter un votant non inscrit"),
        }

        let sb = machine.get_scoreboard();
        assert_eq!(sb.scores.get(&Candidate("Tux".to_string())).unwrap().0, 0);
        assert_eq!(sb.blank_score.0, 0);
        assert_eq!(sb.invalid_score.0, 0);
    }
}
