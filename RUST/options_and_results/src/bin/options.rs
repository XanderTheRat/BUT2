fn main() {
    let sentence1 ="Bonjour Limoges";
    let sentence2 ="";

    // print_first_word(sentence1);
    // print_first_word(sentence2);
    // print_first_word2(sentence1);
    // print_first_word2(sentence2);

    iterate_over_words(sentence1);
    iterate_over_words(sentence2);
}

fn print_first_word(s : &str) -> () {
    let first_word = s.split_whitespace().next();
    match first_word {
        Some(word) => println!("Premier mot : {}", word),
        None => println!("Chaine vide"),
    }
}

fn print_first_word2(s : &str) -> () {
    let first_word = s.split_whitespace().next();
    first_word.expect("Bonjour");
}

fn iterate_over_words(message : &str) -> () {
    for word in message.split_whitespace() {
        println!("{}", word);
    }
}