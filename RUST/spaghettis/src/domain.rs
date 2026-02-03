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